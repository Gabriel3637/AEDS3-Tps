import View.*;

import java.util.Scanner;


public class Principal {

public static void main(String[] args) {

    Scanner console;

    try {
        console = new Scanner(System.in);
        int opcao;
        do {

            System.out.println("\n\nPUCFlix 1.0");
            System.out.println("-------");
            System.out.println("> Início");
            System.out.println("1) Séries");
            System.out.println("2) Atores");
            System.out.println("0) Sair");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    (new MenuSeries()).menu();
                    break;
                case 2:
                    (new MenuAtores()).menu();
                    break;   
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);



    } catch(Exception e) {
        e.printStackTrace();
    }

}

}
