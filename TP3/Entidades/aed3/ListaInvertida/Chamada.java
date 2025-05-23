package Entidades.aed3.ListaInvertida;

import java.util.Scanner;
import Entidades.aed3.ListaInvertida.ListaInvertidaImplementada;
import java.util.ArrayList;
import java.util.Arrays;

public class Chamada{

  
  public static void main(String[] args){
    try{
      Scanner console = new Scanner(System.in);
      ListaInvertidaImplementada lista = new ListaInvertidaImplementada("aed3/stopwords.txt", 4,"Dados/dicionario.listainv.db", "Dados/blocos.listainv.db"); 
      
      System.out.println("Digite: ");
      String teste = console.nextLine();
      if(teste.isEmpty()){
        teste = "A computação é a arte de dizer ao computador o que fazer";
      }
      System.out.println(teste);
      ArrayList<String> arrayteste = lista.normalizar(teste);
      for(String s : arrayteste){
        System.out.println("|" + s + "|");
      }
    }catch(Exception e){
      System.out.println("Erro");
      e.printStackTrace();
    }
    
  }
}
