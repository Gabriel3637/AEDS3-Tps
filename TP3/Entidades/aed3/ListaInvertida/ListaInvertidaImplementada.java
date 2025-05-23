package Entidades.aed3.ListaInvertida;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.text.Normalizer;
import java.util.regex.Pattern;

import Entidades.aed3.ListaInvertida.ElementoLista;

public class ListaInvertidaImplementada{
  public ArrayList<String> stopwords;
  private ListaInvertida listainv;
  
  /*
  Identidade: ListaInvertidaImplementada()
  Objetivo: Construtor da classe ListaInvertidaImplementada, receber como parametros String c com o local do arquivo das stopwords, inteiro tb com tamanho do bloco da listainvertida, String ad com local do arquivo dicionário, String ab com o local do arquivo de bloco, criar uma entidade listainv com o tb, ad e ab, criar um Scanner para ler o arquivo de stopwords adiciona-las a um arraylist 
  Parametros:
  -String c: Local do arquivo das stopwords
  -int tb: Tamanho do bloco da lista invertida
  -String ad: Local do arquivo de dicionario
  -String ab: Local do arquivo de blocos
  Retorno: Não há retorno
  */
  public ListaInvertidaImplementada(String c, int tb, String ad, String ab) throws Exception {
    //Definir variáveis
    int cont = 0;
    String palavra = "";
    ArrayList<String> tmp = new ArrayList<>();
    
    //Criar classe da lista invertida
    listainv = new ListaInvertida(tb, ad, ab);
    
    //Criar scanner para leitura do arquivo das stopwords
    Scanner console = new Scanner(new File(c));
    
    //Adicionar todas as stopwords a um arraylist
    while(console.hasNext()){
      palavra = console.nextLine();
      tmp.add(palavra);
    }
    
    //Remover espaços excedentes do arraylist
    tmp.trimToSize();
    this.stopwords = tmp;
  }
  
  /*
  Identidade: printlista()
  Objetivo: Printa todos os indices invertidos e a quantidade de elementos
  Parametros:Não há parâmetros
  Retorno: Não há retorno
  */
  public void printlista(){
    try{
      listainv.print();
      System.out.println("Quantidade de entidades: " + listainv.numeroEntidades());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  /*
  Identidade: normalizar()
  Objetivo: Recebe uma string s e o id do elemento como parâmetro, transforma todos os seu caracteres em minusculos, decompõe os caracteres acentuados (ã = a~), remove os caracteres de acento, remove as pontuações e caracteres especiais, remove espaços excedentes, gera um arraylist com todas as palavras restantes, gera uma arraylist com todas as palavras que não são stopwords e à retorna 
  Parametros:
  -String s: String a ser normalizada
  Retorno: ArrayList<String>
  -Contendo todas as palavras normalizadas sem stopwords
  */
  public ArrayList<String> normalizar(String s){
    //Definindo variaveis
    ArrayList<String> resp, tmp = new ArrayList<>();
    String normalizada;
    String palavaraatual = "";
    boolean verificarstop = false;
    int cont = 0;
    
    //Transforma todos os caracteres em minúsculos
    normalizada = s.toLowerCase();
    
    //Decompõe os caracteres acentuados (ã = a~), remove os caracteres de acento, remove as pontuações e caracteres especiais e remove espaços excedentes
    normalizada = Normalizer.normalize(normalizada, Normalizer.Form.NFKD).replaceAll("\\p{M}", "").replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", " ");
    
    //Gera array lista com todas as palavras restantes
    resp = new ArrayList<>(Arrays.asList(normalizada.split(" ")));
    
    //Para todas as palavras geradas verifica se não há uma correspondente nas stopwords para adiciona-la a um novo arraylist
    for(String palavrafrase : resp){
      verificarstop = false;
      
      //Buscar se há correspondente nas stopword
      while(!verificarstop && cont < this.stopwords.size()){
        verificarstop = palavrafrase.compareTo(this.stopwords.get(cont)) == 0;
        cont += 1;
      }
      
      //Se não houver correspondente adicionar no arraylist final
      if(!verificarstop){
        tmp.add(palavrafrase);
      }
      cont = 0;
    }
    
    //Retirar espaços excedentes no arraylist
    tmp.trimToSize();
    resp = tmp;
    
    //Retorno
    return resp;
  }
  
  /*
  Identidade: inserir()
  Objetivo: Recebe uma string s e o id do elemento como parametro, normaliza suas palavras, calcula a frequencia, adiciona na lista invertida e incrementa no contador de entidade
  Parametros:
  -String s: String contendo o nome da entidade a ser adicionada
  -int id: Id da entidade a ser adicionada
  Retorno: Boolean
  -true: Se adicionado com sucesso
  -false: Se não for adicionado com sucesso
  */
  public boolean inserir(String s, int id){
    boolean resp = false;
    try{
      //Definir variaveis
      ArrayList<String> palavras = normalizar(s);
      int tam = palavras.size(), contpalavras = 0, contconj = 0;
      String palavraselecionada = "";
      float frequencia = 0;
      
      //Ordenar arraylist das palavras normalizadas
      palavras.sort((a, b) -> {return a.compareTo(b);});
      
      //Para cada palavra diferente contar quantas vezes aparece e calcular frequencia
      do{
        //Selecionar uma palavra
        palavraselecionada = palavras.get(contpalavras);
        contconj = 0;
        
        //Contar quantas vezes a palavra apareceu
        while( contpalavras < tam && palavraselecionada.compareTo(palavras.get(contpalavras)) == 0){
          contconj++;
          contpalavras++;
        }
        
        //Calcular a frequencia da palavra
        frequencia = (float)contconj /(float)tam;
        
        //Adicionar a palavra à lista invertida
        resp = listainv.create(palavraselecionada , new ElementoLista(id, frequencia));
      }while(resp && contpalavras < tam);
      
      //Se adicionado com sucesso incrementar o contador de entidades
      if(resp){
        listainv.incrementaEntidades();
      }
    }catch(Exception e){
      System.out.println("[inserir]: Erro ao inserir entidade!");
      e.printStackTrace();
    }
    
    return resp;
  }
  
  /*
  Identidade: atualizar()
  Objetivo: Recebe uma string ant com o string antigo do elemento, uma string s como o novo string do elemento e o id do elemento como parametro, normaliza as palavras das strings, exclui todos as palavras antigas, calcula a frequencia das palavras novas, adiciona na lista invertida as palvras novas
  Parametros:
  -String ant: String contendo o nome antigo da entidade a ser atualizada
  -String s: String contendo o novo nome da entidade a ser atualizada
  -int id: Id da entidade a ser atualizada
  Retorno: Boolean
  -true: Se atualizdo com sucesso
  -false: Se não for atualizado com sucesso
  */
  public boolean atualizar(String ant, String s, int id){
    boolean resp = false;
    try{
      //Definir variáveis
      ArrayList<String> palavras = normalizar(s);
      ArrayList<String> palavrasAntigas = normalizar(ant);
      int tamnov = palavras.size(), tamant = palavrasAntigas.size(), contpalavras = 0, contconj = 0;
      String palavraselecionada = "";
      
      //Ordenar arraylist das palavras normalizadas antigas e novas
      palavrasAntigas.sort((a, b) -> {return a.compareTo(b);});
      palavras.sort((a, b) -> {return a.compareTo(b);});
      
      //Para cada palavra diferente exclui-la
      do{
        //Selecionar palavra
        palavraselecionada = palavrasAntigas.get(contpalavras);
        
        //Se a palavra for a primeira ou se for diferente da anterior exclui-la
        if(contpalavras == 0 || palavraselecionada.compareTo(palavrasAntigas.get(contpalavras-1)) != 0){
          
          resp = listainv.delete(palavraselecionada , id);
          
        }
        contpalavras++;
      }while(resp && contpalavras < tamant);
      
      //Reiniciar o contador
      contpalavras = 0;
      if(resp){
        float frequencia = 0;
        
        //Para cada palavra diferente contar quantas vezes aparece e calcular frequencia
        do{
          //Selecionar uma palavra
          palavraselecionada = palavras.get(contpalavras);
          contconj = 0;
          
          //Contar quantas vezes a palavra apareceu
          while(contpalavras < tamnov && palavraselecionada.compareTo(palavras.get(contpalavras)) == 0){
            contpalavras++;
            contconj++;
          }
          
          //Calcular a frequencia da palavra
          frequencia = (float)contconj/(float)tamnov;
          
          //Adicionar a palavra à lista invertida
          resp = listainv.create(palavraselecionada , new ElementoLista(id, frequencia));
        }while(resp && contpalavras < tamnov);
      }
    }catch(Exception e){
      System.out.println("[atualizar]: Erro ao atualizar entidade!");
      e.printStackTrace();
    }
    
    return resp;
  }
  
  /*
  Identidade: excluir()
  Objetivo: Recebe uma string s e o id do elemento como parametro, normaliza suas palavras, remove da lista invertida e decrementa no contador de entidade
  Parametros:
  -String s: String contendo o nome da entidade a ser excluida
  -int id: Id da entidade a ser excluida
  Retorno: Boolean
  -true: Se excluida com sucesso
  -false: Se não for excluida com sucesso
  */
  public boolean excluir(String s, int id){
    boolean resp = false;
    try{
      //Definir variaveis
      ArrayList<String> palavras = normalizar(s);
      int tam = palavras.size(), contpalavras = 0;
      String palavraselecionada = "";
      
      //Ordenar arraylist das palavras normalizadas
      palavras.sort((a, b) -> {return a.compareTo(b);});
      
      //Para cada palavra diferente exclui-la
      do{
        //Selecionar palavra
        palavraselecionada = palavras.get(contpalavras);
        
        //Se a palavra for a primeira ou se for diferente da anterior exclui-la
        if(contpalavras == 0 || palavraselecionada.compareTo(palavras.get(contpalavras-1)) != 0){
          
          resp = listainv.delete(palavraselecionada , id);
          
        }
        contpalavras++;
      }while(resp && contpalavras < tam);
      //Se a entidade for excluida com sucesso decrementar a quantidade de entidades
      if(resp){
        listainv.decrementaEntidades();
      }
    }catch(Exception e){
      System.out.println("[excluir]: Erro ao excluir entidade!");
      e.printStackTrace();
    }
    
    return resp;
  }
  
  public class Termo{
    String termo;
    int idf;
    ElementoLista[] elementos;

    public Termo(String t){
      setTermo(t);
    }

    public String getTermo() {
      return termo;
    }
    public void setTermo(String termo) {
      this.termo = termo;
    }
    public int getIdf() {
      return idf;
    }
    public void setIdf(int idf) {
      this.idf = idf;
    }
    public ElementoLista[] getElementos() {
      return elementos;
    }
    public void setElementos(ElementoLista[] elementos) {
      this.elementos = elementos;
    }

    
  }
  public ArrayList<ElementoLista> buscar(String s){ // busca a palavra toda, tem que retornar o id com a maior soma
    ArrayList<ElementoLista> a = new ArrayList<ElementoLista>();
    int id = -2;
      try {
        //coloca os termos nas classes
        System.out.println("a");
        ArrayList<String> palavras = normalizar(s);
        Termo[] t = new Termo[palavras.size()];
        for(int i=0;i<palavras.size();i++){
          t[i] = new Termo(palavras.get(i));
          //procura a frequência do termo
          t[i].setElementos(listainv.read(t[i].getTermo()));
          //calcula o idf
          t[i].setIdf(listainv.numeroEntidades()/(t[i].getElementos()).length);
          //atualiza a frequência
          for(int j=0;j<(t[i].getElementos()).length;j++){
            t[i].elementos[j].setFrequencia(t[i].elementos[j].getFrequencia()*t[i].getIdf());
          }
        }
        System.out.println("aa");
        //somar de todos os termos
        //ElementoLista[] ele = new ElementoLista[listainv.numeroEntidades()];
        int[] ids = new int[1000]; // tamanho máximo arbitrário
        float[] somas = new float[listainv.numeroEntidades()];
        for(int i=0;i<listainv.numeroEntidades();i++){
          somas[i]=0;
        }
        for(int i=0;i<palavras.size();i++){
          for(int j=0;j<t[i].getElementos().length;j++){
            somas[t[i].elementos[j].getId()] = somas[t[i].elementos[j].getId()] + t[i].elementos[j].getFrequencia();
            System.out.println("Id: "+t[i].elementos[j].getId() + ", Soma: " + somas[t[i].elementos[j].getId()]);
          }
        }
        ArrayList<Integer> x = new ArrayList<Integer>();
        for(int i=0;i<palavras.size();i++){
          if(somas[i] > 0.001){
            x.add(i);
            //System.out.println("adcionou aqui");
          }
        }
        for(int i=0;i<x.size();i++){
          a.add(new ElementoLista(x.get(i),somas[x.get(i)]));
          System.out.println("Colocando aqui: "+a.get(i).getId());
        }
        //retornar o maior
        /*Arrays.sort(ele, Comparator.comparingDouble(ElementoLista::getFrequencia));
        for(int i=0;i<listainv.numeroEntidades();i++){
          System.out.print(ele[i] + " ");
        }
        id = ele[ele.length - 1].getId();*/
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("aaaa");
    return a;
  }
}
