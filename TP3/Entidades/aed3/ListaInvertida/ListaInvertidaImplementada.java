package Entidades.aed3.ListaInvertida;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
  
  
}
