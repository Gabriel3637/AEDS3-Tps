package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Serie;

import Entidades.aed3.ArqSerie;


public class MenuSeries{

    
  ArqSerie arquivoSerie;
  private static Scanner console = new Scanner(System.in);

  public MenuSeries() throws Exception {
      arquivoSerie = new ArqSerie();
  }
    
  public void mostrarSerie(Serie serie) {
    if (serie != null) {
        System.out.println("\nDetalhes da Série:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", serie.getNome());
        System.out.printf("Lançamento: %s%n", serie.getLancamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("Sinopse...: %s%n", serie.getSinopse());
        System.out.printf("Streaming.: %s%n", serie.getStreaming());
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
        System.out.println("5) Episódios");
        System.out.println("6) Atuação");
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
            case 5:
                EpisodiosSerie();
                break;
            case 6:
                AtuacaoSerie();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

    } while (opcao != 0);
  }
  
    public void incluirSerie() {
      System.out.println("Inclusão de série");
      String nome = "";
      LocalDate lancamento = null;
      String sinopse = "";
      String streaming = "";
      boolean dadosCorretos = false;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      do {
          System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
          nome = console.nextLine();
          if(nome.length()==0)
              return;
          if(nome.length()<4)
              System.err.println("O nome da série deve ter no mínimo 4 caracteres.");
      } while(nome.length()<4);
      
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
          System.out.print("Sinopse (min. de 20 letras ou vazio para cancelar): ");
          sinopse = console.nextLine();
          if(sinopse.length()== 0)
            return;
          if(sinopse.length()<=20)
              System.err.println("A sinopse da série deve ter no mínimo 20 caracteres.");
      } while(sinopse.length()<=20);
      
      do {
          System.out.print("\nPlataforma de streaming (min. de 2 letras ou vazio para cancelar): ");
          streaming = console.nextLine();
          if(nome.length()==0)
              return;
          if(nome.length()<2)
              System.err.println("O nome da plataforma de streaming deve ter no mínimo 2 caracteres.");
      } while(nome.length()<2);

      

      System.out.print("\nConfirma a inclusão da serie? (S/N) ");
      char resp = console.nextLine().charAt(0);
      if(resp=='S' || resp=='s') {
          try {
              Serie c = new Serie(nome, lancamento, sinopse, streaming);
              arquivoSerie.create(c);
              System.out.println("Serie incluída com sucesso.");
          } catch(Exception e) {
              System.out.println("Erro do sistema. Não foi possível incluir a série!");
          }
      }
  }
  
  
  
  public void buscarSerie() {
        System.out.println("Busca de série");
        System.out.print("Nome: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
            return; 

        try {
            Serie[] serie = arquivoSerie.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a série: ");
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
                mostrarSerie(serie[o-1]);  // Exibe os detalhes da série encontrada
            } else {
                System.out.println("Nenhuma série encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar as series!");
            e.printStackTrace();
        }
    }
    
    public void alterarSerie() {
        System.out.println("\nAlteração de série");
        boolean dadosCorretos;

        dadosCorretos = false;
        
        System.out.print("Digite o nome da série: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoSerie.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a série: ");
                
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
                System.out.println("Nenhuma série encontrado.");
            }
      
            // Tenta ler a série com o ID fornecido
            Serie serieSelecionada = serie[o-1];
            if (serieSelecionada != null) {
                mostrarSerie(serieSelecionada);  // Exibe os dados da serie para confirmação

                // Alteração de nome
                String novoNome;
                dadosCorretos = false;
                do {
                    System.out.print("Novo nome (deixe em branco para manter o anterior): ");
                    novoNome = console.nextLine();
                    if(!novoNome.isEmpty()) {
                        if(novoNome.length()>=4) {
                            serieSelecionada.setNome(novoNome);  // Atualiza o nome se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome da serie deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de data de lançamento
                String novaData;
                dadosCorretos = false;
                do {
                    System.out.print("Nova data de lançamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                    novaData = console.nextLine();
                    if (!novaData.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            serieSelecionada.setLancamento(LocalDate.parse(novaData, formatter));  // Atualiza a data de lançamento se fornecida
                        } catch (Exception e) {
                            System.err.println("Data inválida. Valor mantido.");
                        }
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de sinopse
                String novaSinopse;
                dadosCorretos = false;
                do {
                    System.out.print("Nova sinopse (deixe em branco para manter o anterior): ");
                    novaSinopse = console.nextLine();
                    if(!novaSinopse.isEmpty()) {
                        if(novaSinopse.length()>=20) {
                            serieSelecionada.setSinopse(novaSinopse);  // Atualiza a sinopse se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("A sinopse da série deve ter no mínimo 20 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);
                
                // Alteração de streaming
                String novaStreaming;
                dadosCorretos = false;
                do {
                    System.out.print("Nova plataforma de streaming (deixe em branco para manter o anterior): ");
                    novaStreaming = console.nextLine();
                    if(!novaStreaming.isEmpty()) {
                        if(novaStreaming.length()>=2) {
                            serieSelecionada.setStreaming(novaStreaming);  // Atualiza o nome da plataforma de streaming se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome da plataforma de streaming da série deve ter no mínimo 2 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);
                

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arquivoSerie.update(serieSelecionada);
                    if (alterado) {
                        System.out.println("Serie alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o serie.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o serie!");
            e.printStackTrace();
        }
        
    }
    
    public void excluirSerie() {
        System.out.println("\nExclusão de serie");
        
        System.out.print("Digite o nome da série: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoSerie.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a série: ");
                
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
                System.out.println("Nenhuma série encontrado.");
            }

            // Tenta selecionar serie do array
            Serie serieSelecionada = serie[o-1];
            if (serieSelecionada != null) {
                System.out.println("Serie encontrada:");
                mostrarSerie(serieSelecionada);  // Exibe os dados da serie para confirmação

                System.out.print("\nConfirma a exclusão do serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arquivoSerie.delete(serieSelecionada.getId());  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Serie excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir a serie.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Serie não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir a serie!");
            e.printStackTrace();
        }
    }
    
    public void EpisodiosSerie(){
      System.out.print("Digite o nome da série: ");
      String nome = console.nextLine();  // Lê o nome digitado pelo usuário
      System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoSerie.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a série: ");
                
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
                System.out.println("Nenhuma série encontrado.");
            }
      
            // Tenta ler a série com o ID fornecido
            Serie serieSelecionada = serie[o-1];
            
            (new MenuEpisodio(serieSelecionada.getId())).menu();
            
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível encontrar a serie!");
            e.printStackTrace();
        }
        
            
    }
    
    public void AtuacaoSerie(){
      System.out.print("Digite o nome da série: ");
      String nome = console.nextLine();  // Lê o nome digitado pelo usuário
      System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoSerie.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a série: ");
                
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
                System.out.println("Nenhuma série encontrado.");
            }
      
            // Tenta ler a série com o ID fornecido
            Serie atuacaoSelecionada = serie[o-1];
            
            (new MenuAtuacao(atuacaoSelecionada.getId())).menu();
            
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível encontrar a serie!");
            e.printStackTrace();
        }
      }
    
  
}
