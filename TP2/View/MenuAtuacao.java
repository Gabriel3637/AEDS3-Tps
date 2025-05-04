package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Serie;

import Entidades.aed3.ArqAtuacao;


public class MenuAtuacao{

  public int serieId;
  ArqAtuacao arquivoAtuacao;
  ArqAtor arquivoAtor;
  private static Scanner console = new Scanner(System.in);

  public MenuAtuacao(int pserieId) throws Exception {
      arquivoAtuacao = new ArqAtuacao();
      this.serieId = pserieId;
  }
    
  public void mostrarAtuacao(Atuacao atuacao) {
    if (atuacao != null) {
        System.out.println("\nDetalhes da atuação:");
        System.out.println("----------------------");
        System.out.printf("Ator..: %s%n", atuacao.getAtorId());
        System.out.printf("Papel: %s%n", atuacao.getPapel());
        System.out.println("----------------------");
    }
  }
  
  public void mostrarAtor(Ator ator) {
    if (ator != null) {
        System.out.println("\nDetalhes da Ator:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", ator.getNome());
        System.out.printf("Idade.....: %s%n", ator.getIdade());
        System.out.printf("Sexo...: %s%n", ator.getSexo());
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
                incluirAtuacao();
                break;
            case 2:
                buscarAtuacao();
                break;
            case 3:
                alterarAtuacao();
                break;
            case 4:
                excluirAtuacao();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

    } while (opcao != 0);
  }
  
    public void incluirAtuacao() {
      System.out.println("Inclusão de atuação");
      String papel = "";
      int serieId
      boolean dadosCorretos = false;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      Ator atorSelecionado;
      
      System.out.print("Digite o nome do ator: ");
      String nome = console.nextLine();  // Lê o nome digitado pelo usuário
      System.out.println("");
      if(nome.isEmpty())
          return; 
      int o = -1;
      try {
          Ator[] ator = arquivoAtor.readNome(nome);  // Chama o método de leitura da classe Arquivo
          if (ator.length>0) {
              int n=1;
              for(Ator s : ator) {
                  System.out.println((n++)+": "+s.getNome());
              }
              System.out.print("Escolha o ator: ");
              
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
              System.out.println("Nenhum ator encontrado.");
          }

          // Tenta selecionar ator do array
          Ator atorSelecionado = ator[o-1];
          if (atorSelecionado != null) {
              System.out.println("Ator encontrado:");
              mostrarAtor(atorSelecionado);  // Exibe os dados do ator para confirmação
          } else {
              System.out.println("Ator não encontrado.");
          }
      }catch{
        System.out.println("Erro do sistema. Não foi possível encontrar o ator!");
            e.printStackTrace();
      }
      
      dadosCorretos = false;
      do {
          System.out.print("\nPapel (vazio para cancelar): ");
          nome = console.nextLine();
          if(nome.length()==0)
              return;
          if(nome.length()>0)
              dadosCorretos = true;
      } while(dadosCorretos);
      

      System.out.print("\nConfirma a inclusão da atuação? (S/N) ");
      char resp = console.nextLine().charAt(0);
      if(resp=='S' || resp=='s') {
          try {
              Atuacao c = new Atuacao(this.serieId, atorSelecionado.getId(), papel);
              arquivoAtuacao.create(c);
              System.out.println("Atuação incluída com sucesso.");
          } catch(Exception e) {
              System.out.println("Erro do sistema. Não foi possível incluir a atuação!");
          }
      }
  }
  
  
  
  public void buscarAtuacao() {
        System.out.println("Busca de atuação");
        System.out.print("Papel: ");
        String nome = console.nextLine();  // Lê o papel digitado pelo usuário

        if(papel.isEmpty())
            return; 

        try {
            int o = -1;
            Ator[] ator = arquivoAtor.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (ator.length>0) {
                int n=1;
                for(Ator s : ator) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o ator: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarAtor(ator[o-1]);  // Exibe os detalhes da atuação encontrada
            } else {
                System.out.println("Nenhum ator encontrado.");
            }
            
            o = -1;
            Atuacao[] atuacao = arquivoAtuacao.readAtor(ator[o-1].getId());  // Chama o método de leitura da classe Arquivo
            if (atuacao.length>0) {
                int n=1;
                for(Atuacao s : atuacao) {
                    System.out.println((n++)+": "+s.getPapel());
                }
                System.out.print("Escolha o atuacao: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarAtuacao(atuacao[o-1]);  // Exibe os detalhes da atuação encontrada
            } else {
                System.out.println("Nenhuma atuação encontrada.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar as series!");
            e.printStackTrace();
        }
        
        
    }
    
    public void alterarAtuacao() {
        System.out.println("\nAlteração de atuação");
        boolean dadosCorretos;

        dadosCorretos = false;
        
        System.out.print("Digite o nome do ator: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        System.out.println("");
        if(nome.isEmpty())
            return; 
            
        try {
            int o = -1;
            Ator[] ator = arquivoAtor.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (ator.length>0) {
                int n=1;
                for(Ator s : ator) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o ator: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarAtor(ator[o-1]);  // Exibe os detalhes da atuação encontrada
            } else {
                System.out.println("Nenhum ator encontrado.");
            }
            
            o = -1;
            Atuacao[] atuacao = arquivoAtuacao.readAtor(ator[o-1].getId());  // Chama o método de leitura da classe Arquivo
            if (atuacao.length>0) {
                int n=1;
                for(Atuacao s : atuacao) {
                    System.out.println((n++)+": "+s.getPapel());
                }
                System.out.print("Escolha o atuacao: ");
                
                do { 
                    try {
                        o = Integer.valueOf(console.nextLine());
                    } catch(NumberFormatException e) {
                        o = -1;
                    }
                    if(o<=0 || o>n-1)
                        System.out.println("Escolha um número entre 1 e "+(n-1));
                }while(o<=0 || o>n-1);
                mostrarAtuacao(atuacao[o-1]);  // Exibe os detalhes da atuação encontrada
            } else {
                System.out.println("Nenhuma atuação encontrada.");
            }
            Atuacao atuacaoSelecionada = atuacao[o-1];
            
        
            
            
        if (atuacaoSelecionada != null) {

            // Alteração do ator
            dadosCorretos = false;
            do{
              System.out.print("Novo ator (deixe em branco para manter o anterior): ");
              String nomeNovoAtor = console.console.nextLine();
              
              if(!nomeNovo.isEmpty()){
                int o = -1;
                Ator[] ator = arquivoAtor.readNome(nomeNovoAtor);  // Chama o método de leitura da classe Arquivo
                if (ator.length>0) {
                    int n=1;
                    for(Ator s : ator) {
                        System.out.println((n++)+": "+s.getNome());
                    }
                    System.out.print("Escolha o ator: ");
                    
                    do { 
                        try {
                            o = Integer.valueOf(console.nextLine());
                        } catch(NumberFormatException e) {
                            o = -1;
                        }
                        if(o<=0 || o>n-1)
                            System.out.println("Escolha um número entre 1 e "+(n-1));
                    }while(o<=0 || o>n-1);
                    mostrarAtor(ator[o-1]);  // Exibe os detalhes da atuação encontrada
                } else {
                    System.out.println("Nenhum ator encontrado.");
                }
                atorSelecionado = ator[o-1];
                
                if(atorSelecionado != null){
                  dadosCorretos = true;
                  atuacaoSelecionada.setAtorId(atorSelecionado.getId())
                }
              }else {
                dadosCorretos = true;
              }
            } while(!dadosCorretos);
            
            
            String novoPapel;
            dadosCorretos = false;
            do {
                System.out.print("Novo papel (deixe em branco para manter o anterior): ");
                novoPapel = console.nextLine();
                if(!novoPapel.isEmpty()) {
                  atuacaoSelecionada.setPapel(novoPapel);  // Atualiza o nome se fornecido
                  dadosCorretos = true;
                } else
                    dadosCorretos = true;
            } while(!dadosCorretos);
          }

               
                

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arquivoAtuacao.update(atuacaoSelecionada);
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
    
    public void excluirAtuacao() {
        System.out.println("\nExclusão de serie");
        
        System.out.print("Digite o nome da atuação: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário
        System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoAtuacao.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a atuação: ");
                
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
                System.out.println("Nenhuma atuação encontrado.");
            }

            // Tenta selecionar serie do array
            Serie serieSelecionada = serie[o-1];
            if (serieSelecionada != null) {
                System.out.println("Serie encontrada:");
                mostrarAtuacao(serieSelecionada);  // Exibe os dados da serie para confirmação

                System.out.print("\nConfirma a exclusão do serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arquivoAtuacao.delete(serieSelecionada.getId());  // Chama o método de exclusão no arquivo
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
      System.out.print("Digite o nome da atuação: ");
      String nome = console.nextLine();  // Lê o nome digitado pelo usuário
      System.out.println("");
        if(nome.isEmpty())
            return; 
        int o = -1;
        try {
            Serie[] serie = arquivoAtuacao.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (serie.length>0) {
                int n=1;
                for(Serie s : serie) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha a atuação: ");
                
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
                System.out.println("Nenhuma atuação encontrado.");
            }
      
            // Tenta ler a atuação com o ID fornecido
            Serie serieSelecionada = serie[o-1];
            
            (new MenuEpisodio(serieSelecionada.getId())).menu();
            
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível encontrar a serie!");
            e.printStackTrace();
        }
            
    }
    
    
    
  
}
