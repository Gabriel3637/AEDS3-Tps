package Entidades.aed3.ListaInvertida;

import java.util.Scanner;
import Entidades.aed3.ListaInvertida.ListaInvertidaImplementada;
import java.util.ArrayList;
import java.util.Arrays;

public class Chamada{
   public static Scanner console;
   public static ListaInvertidaImplementada lista;
   
  public static void opnormalizar(){
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
  }
  
  public static void opincluir(){}
  
  public static void opbuscar(){}
  public static void opatualizar(){}
  public static void opdeletar(){}
  
  public static void main(String[] args){
    try{
      console = new Scanner(System.in);
      lista = new ListaInvertidaImplementada("Dados/ListaInvertida/stopwords.txt", 4,"dadostestec/dicionario.listainv.db", "dadostestec/blocos.listainv.db"); 
      
      int opcao = -1;
      do{
        System.out.println("1) Normalizar");
        System.out.println("2) Incluir");
        System.out.println("3) Buscar");
        System.out.println("4) Atualizar");
        System.out.println("5) Deletar");
        System.out.println("Escolha uma opcao:");
        
        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }
        switch(opcao){
          case 0: break;
          case 1: 
            opnormalizar();
            break;
          case 2: 
            opincluir();
            break;
          case 3: 
            opbuscar();
            break;
          case 4: 
            opatualizar();
            break;
          case 5: 
            opdeletar();
            break;
          default:
            System.out.println("Erro");
            opcao = -1;
        }
        
      }while(opcao != 0);
      
    }catch(Exception e){
      System.out.println("Erro");
      e.printStackTrace();
    }
    
  }
}
