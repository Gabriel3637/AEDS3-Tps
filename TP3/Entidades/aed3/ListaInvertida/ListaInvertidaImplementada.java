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
  
  public ListaInvertidaImplementada(String c, int tb, String ad, String ab) throws Exception {
    listainv = new ListaInvertida(tb, ad, ab);
    Scanner console = new Scanner(new File(c));
    int cont = 0;
    String palavra = "";
    ArrayList<String> tmp = new ArrayList<>();
    while(console.hasNext()){
      palavra = console.nextLine();
      tmp.add(palavra);
    }
    tmp.trimToSize();
    this.stopwords = tmp;
  }
  
  public void printlista(){
    try{
      listainv.print();
      System.out.println(listainv.numeroEntidades());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public ArrayList<String> normalizar(String s){
    ArrayList<String> resp, tmp = new ArrayList<>();
    String normalizada;
    String palavaraatual = "";
    boolean verificarstop = false;
    int cont = 0;
    normalizada = s.toLowerCase();
    normalizada = Normalizer.normalize(normalizada, Normalizer.Form.NFKD).replaceAll("\\p{M}", "").replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", " ");
    
    resp = new ArrayList<>(Arrays.asList(normalizada.split(" ")));
    
    for(String palavrafrase : resp){
      verificarstop = false;
      while(!verificarstop && cont < this.stopwords.size()){
        verificarstop = palavrafrase.compareTo(this.stopwords.get(cont)) == 0;
        cont += 1;
      }
      if(!verificarstop){
        tmp.add(palavrafrase);
      }
      cont = 0;
    }
    tmp.trimToSize();
    resp = tmp;
    return resp;
  }
  
  public boolean inserir(String s, int id){
    boolean resp = false;
    try{
      ArrayList<String> palavras = normalizar(s);
      int tam = palavras.size(), contpalavras = 0, contconj = 0;
      String palavraselecionada = "";
      ElementoLista elementoteste;
      
      palavras.sort((a, b) -> {return a.compareTo(b);});
      float frequencia = 0;
      do{
        palavraselecionada = palavras.get(contpalavras);
        contpalavras++;
        contconj = 1;
        while(contpalavras < tam-1 && palavraselecionada.compareTo(palavras.get(contpalavras + 1)) == 0){
          contconj++;
        }
        frequencia = (float)contconj/(float)tam;
        resp = listainv.create(palavraselecionada , new ElementoLista(id, frequencia));
      }while(resp && contpalavras < tam);
      if(resp){
        listainv.incrementaEntidades();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    
    return resp;
  }
  public boolean excluir(String s, int id){
    boolean resp = false;
    try{
      ArrayList<String> palavras = normalizar(s);
      int tam = palavras.size(), contpalavras = 0;
      String palavraselecionada = "";
      
      palavras.sort((a, b) -> {return a.compareTo(b);});
      do{
        palavraselecionada = palavras.get(contpalavras);
        if(contpalavras == 0 || palavraselecionada.compareTo(palavras.get(contpalavras-1)) != 0){
          
          resp = listainv.delete(palavraselecionada , id);
          
        }
        contpalavras++;
      }while(resp && contpalavras < tam);
      
      if(resp){
        listainv.decrementaEntidades();
      }
    }catch(Exception e){
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
