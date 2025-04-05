package TP1.View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TP1.Model.Episodio;

public class MenuEpisodio {
    private static Scanner console = new Scanner(System.in);

    public void menu(int serieId) {
        
        int opcao;
        do {
            System.out.println("\n\nPUCFlix 1.0");
            System.out.println("-------");
            System.out.println("> Início > Serie > Buscar > Episódios");
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
                    incluirEpisodio(serieId);
                    break;
                case 2:
                    buscarEpisodio(serieId);
                    break;
                case 3:
                    alterarEpisodio(serieId);
                    break;
                case 4:
                    excluirEpisodio(serieId);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
    
        } while (opcao != 0);
      }

    public void incluirEpisodio(int serieId) {
        System.out.println("\nInclusão de episodio");
        String nome = "";
        short temporada;
        LocalDate lancamento;
        short duracao;
        boolean dadosCorretos;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
            if(nome.length()<4)
                System.err.println("O nome do episodio deve ter no mínimo 4 caracteres.");
        } while(nome.length()<4);

        do {
            System.out.print("\nTemporada (-1 para cancelar): ");
            try {
                temporada = Short.parseShort(console.nextLine());
            } catch (Exception e) {
                temporada = 0;
            }
            
            if(temporada==-1)
                return;
            if(temporada==0)
                System.err.println("Erro, tente novamente");
        } while(temporada==0);

        do {
            System.out.print("Data de nascimento (DD/MM/AAAA): ");
            String dataStr = console.nextLine();
            dadosCorretos = false;
            try {
                lancamento = LocalDate.parse(dataStr, formatter);
                dadosCorretos = true;
            } catch (Exception e) {
                System.err.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        } while(!dadosCorretos);

        do {
            System.out.print("\nTemporada (-1 para cancelar): ");
            try {
                duracao = Short.parseShort(console.nextLine());
            } catch (Exception e) {
                duracao = 0;
            }
            
            if(duracao==-1)
                return;
            if(duracao==0)
                System.err.println("Erro, tente novamente");
        } while(duracao==0);

        System.out.print("\nConfirma a inclusão de episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') {
            try {
                Episodio c = new Episodio(serieId, nome, temporada, lancamento, duracao);
                arqEpisodio.create(c);
                System.out.println("Cliente incluído com sucesso.");
            } catch(Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o cliente!");
            }
        }
    }
}
