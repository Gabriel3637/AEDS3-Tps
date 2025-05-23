package Entidades.aed3.ListaInvertida;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.text.Normalizer;
import java.util.regex.Pattern;

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
    try{
      ArrayList<String> palavras = normalizar(s);
      int tam = palavras.size(), contpalavras = 0, contconj = 0;
      String palavraselecionada = "";
      
      palavras.sort((a, b) -> {return a.compareTo(b);});
      float frequencia = 0;
      do{
        palavraselecionada = palavras.get(contpalavras);
        contpalavras++;
        contconj = 1;
        while(palavraselecionada.compareTo(palavras.get(contpalavras + 1)) == 0){
          contconj++;
        }
        frequencia = (float)contconj/(float)tam;
        listainv.create(palavraselecionada , new ElementoLista(id, frequencia));
      }while(contpalavras < tam);
    }catch(Exception e){
      e.printStackTrace();
    }
    
    return true;
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
  public int buscar(String s){ // busca a palavra toda, tem que retornar o id com a maior soma

    int id = -1;
      try {
        //coloca os termos nas classes
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
        //somar de todos os termos
        ElementoLista[] elementos = new ElementoLista[listainv.numeroEntidades()];
        for(int i=0;i<palavras.size();i++){ //percorro os termos
          for(int j=0;j<elementos.length;j++){
            for(int k=0;k<(t[i].getElementos()).length;k++){
              if(elementos[j].getId() == t[i].elementos[k].getId()){
                  elementos[j].setFrequencia( elementos[j].getFrequencia()+t[i].elementos[k].getFrequencia());
              }
            }
          }
        }

        //retornar o maior
        Arrays.sort(elementos, Comparator.comparingDouble(ElementoLista::getFrequencia));
        id = elementos[0].getId();
      } catch (Exception e) {
        e.printStackTrace();
      }

    return id;
  }
}
