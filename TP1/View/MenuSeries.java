package TP1.Views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuSeries{

  private static Scanner console = new Scanner(System.in);
  
  
  public void mostraSerie(Serie serie) {
    if (serie != null) {
        System.out.println("\nDetalhes da Série:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", serie.nome);
        System.out.printf("Lançamento: %s%n", serie.lancamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("Sinopse...: %s%n", serie.sinopse);
        System.out.printf("Streaming.: %s%n", serie.streaming);
        System.out.println("----------------------");
    }
  }
  
  public void menu() {

    int opcao;
    do {
        System.out.println("\n\nPUCFlix 1.0");
        System.out.println("-------");
        System.out.println("> Início > Series");
        System.out.println("1) Incluir");
        System.out.println("2) Buscar");
        System.out.println("3) Alterar");
        System.out.println("4) Excluir");
        System.out.println("0) Retomar ao menu anterior");

        System.out.print("\nOpção: ");
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch(NumberFormatException e) {
            opcao = -1;
        }

        switch (opcao) {
            case 1:
                incluirSerie();
                break;
            case 2:
                buscarSerie();
                break;
            case 3:
                alterarSerie();
                break;
            case 4:
                excluirSerie();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

    } while (opcao != 0);
  }
  
  
  
  
  public void buscarSerie() {
    System.out.println("\nBusca de serie");
    String nome;
    boolean nomeValido = false;

    do {
        System.out.print("\nNome: ");
        nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
            return; 
    } while (!nomeValido);

    try {
        Cliente serie = arqClientes.read(nome);  // Chama o método de leitura da classe Arquivo
        if (serie != null) {
            mostraSerie(serie);  // Exibe os detalhes da serie encontrada
        } else {
            System.out.println("Série não encontrada.");
        }
    } catch(Exception e) {
        System.out.println("Erro do sistema. Não foi possível buscar a série!");
        e.printStackTrace();
    }
  }
    
    
  
}
