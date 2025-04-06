package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Episodio;

import Entidades.aed3.ArqEpisodio;


public class MenuEpisodio{

    
  ArqEpisodio arquivoEpisodio;
  private static Scanner console = new Scanner(System.in);
  int serieId

  public MenuEpisodio(int pserieid) throws Exception {
      arquivoEpisodio = new ArqEpisodio();
      serieId = pserieid;
  }
    
  public void mostrarEpisodio(Episodio episodio) {
    if (episodio != null) {
        System.out.println("\nDetalhes do Episódio:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", episodio.getNome());
        System.out.printf("Temporada.: %d%n", episodio.getTemporada());
        System.out.printf("Lançamento: %s%n", episodio.getLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("Duracao...: %d%n", episodio.getDuracao());
        System.out.println("----------------------");
    }
  }
  
  public void menu() {

    int opcao;
    do {
        System.out.println("\n\nPUCFlix 1.0");
        System.out.println("-------");
        System.out.println("> Início > Series > Episodios");
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
                incluirEpisodio();
                break;
            case 2:
                buscarEpisodio();
                break;
            case 3:
                alterarEpisodio();
                break;
            case 4:
                excluirEpisodio();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

    } while (opcao != 0);
  }
  
    public void incluirEpisodio() {
      System.out.println("Inclusão de episódio");
      String nome = "";
      LocalDate lancamento = null;
      int temporada  = -1;
      int duracao = -1;
      String streaming = "";
      boolean dadosCorretos = false;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      do {
          System.out.print("\nNome (min. de 1 letras ou vazio para cancelar): ");
          nome = console.nextLine();
          if(nome.length()==0)
              return;
          if(nome.length()<1)
              System.err.println("O nome do episódio deve ter no mínimo 1 caracter.");
      } while(nome.length()<1);
      
      do {
            dadosCorretos = false;
            System.out.print("Temporada: ");
            if (console.hasNextInt()) {
                temporada = console.nextInt();
                dadosCorretos = true;
            } else {
                System.err.println("Temporada inválida! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);
      
      do {
          System.out.print("Data de lancamento (DD/MM/AAAA): ");
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
            dadosCorretos = false;
            System.out.print("Duração: ");
            if (console.hasNextInt()) {
                duracao = console.nextInt();
                dadosCorretos = true;
            } else {
                System.err.println("Duração inválida! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

      

      System.out.print("\nConfirma a inclusão do episódio? (S/N) ");
      char resp = console.nextLine().charAt(0);
      if(resp=='S' || resp=='s') {
          try {
              Episodio c = new Episodio(serieId, nome, temporada, lancamento, duracao);
              arquivoEpisodio.create(c);
              System.out.println("Episodio incluída com sucesso.");
          } catch(Exception e) {
              System.out.println("Erro do sistema. Não foi possível incluir o episódio!");
          }
      }
  }
  
  
  
  public void buscarEpisodio() {
        System.out.println("Busca de episódio");
        System.out.print("Nome: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
            return; 

        try {
            Episodio[] episodio = arquivoEpisodio.readNomeSerieId(nome, serieId);  // Chama o método de leitura da classe Arquivo
            if (episodio.length>0) {
                int n=1;
                for(Episodio s : episodio) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o episódio: ");
                int o;
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarEpisodio(episodio[o-1]);  // Exibe os detalhes do episódio encontrada
            } else {
                System.out.println("Nenhum episódio encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar os episodios!");
            e.printStackTrace();
        }
    }
    
    public void alterarEpisodio() {
        System.out.println("\nAlteração de episódio");
        boolean dadosCorretos;

        dadosCorretos = false;
        
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Episodio[] episodio = arquivoEpisodio.readNomeSerieId(nome, serieId);  // Chama o método de leitura da classe Arquivo
            if (episodio.length>0) {
                int n=1;
                for(Episodio s : episodio) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o episódio: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
            } else {
                System.out.println("Nenhum episódio encontrado.");
            }
      
            // Tenta ler o episódio com o ID fornecido
            Episodio episodioSelecionada = episodio[o-1];
            if (episodioSelecionada != null) {
                mostrarEpisodio(episodioSelecionada);  // Exibe os dados do episodio para confirmação

                // Alteração de nome
                String novoNome;
                dadosCorretos = false;
                do {
                    System.out.print("Novo nome (deixe em branco para manter o anterior): ");
                    novoNome = console.nextLine();
                    if(!novoNome.isEmpty()) {
                        if(novoNome.length()>=4) {
                            episodioSelecionada.setNome(novoNome);  // Atualiza o nome se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome do episodio deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);
                
                // Alteração de temporada
                System.out.print("Nova temporada (deixe em branco para manter o anterior): ");
                String novaTemporada = console.nextLine();
                if (!novoSalarioStr.isEmpty()) {
                    try {
                        episodioSelecionado.setTemporada(Integer.parseInt(novaTemporada));  // Atualiza a temporada se fornecida
                    } catch (NumberFormatException e) {
                        System.err.println("Temporada inválida. Valor mantido.");
                    }
                }

                // Alteração de data de lançamento
                String novaData;
                dadosCorretos = false;
                do {
                    System.out.print("Nova data de lançamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                    novaData = console.nextLine();
                    if (!novaData.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            episodioSelecionada.setLancamento(LocalDate.parse(novaData, formatter));  // Atualiza a data de lançamento se fornecida
                        } catch (Exception e) {
                            System.err.println("Data inválida. Valor mantido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de duração
                System.out.print("Nova duração (deixe em branco para manter o anterior): ");
                String novaDuracao = console.nextLine();
                if (!novoSalarioStr.isEmpty()) {
                    try {
                        episodioSelecionado.setTemporada(Integer.parseInt(novaDuracao));  // Atualiza a duração se fornecida
                    } catch (NumberFormatException e) {
                        System.err.println("Duracao inválida. Valor mantido.");
                    }
                }
                

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arquivoEpisodio.update(episodioSelecionada);
                    if (alterado) {
                        System.out.println("Episodio alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o episodio.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o episodio!");
            e.printStackTrace();
        }
        
    }
    
    public void excluirEpisodio() {
        System.out.println("\nExclusão de episodio");
        
        
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Episodio[] episodio = arquivoEpisodio.readNomeSerieId(nome, serieId);  // Chama o método de leitura da classe Arquivo
            if (episodio.length>0) {
                int n=1;
                for(Episodio s : episodio) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o episódio: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
            } else {
                System.out.println("Nenhum episódio encontrado.");
            }

            // Tenta selecionar episodio do array
            Episodio episodioSelecionada = episodio[o-1];
            if (episodioSelecionada != null) {
                System.out.println("Episodio encontrado:");
                mostrarEpisodio(episodioSelecionada);  // Exibe os dados da episodio para confirmação

                System.out.print("\nConfirma a exclusão do episodio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arquivoEpisodio.delete(episodioSelecionada.getId());  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episodio excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir a episodio.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir a episodio!");
            e.printStackTrace();
        }
    }
    
    
    
    
  
}
