package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Ator;
import Model.Atuacao;
import Entidades.aed3.ArqAtor;
import Entidades.aed3.ArqAtuacao;


public class MenuAtores{

    
  ArqAtor arquivoAtor;
  ArqAtuacao arquivoAtuacao;
  private static Scanner console = new Scanner(System.in);

  public MenuAtores() throws Exception {
      arquivoAtor = new ArqAtor();
      arquivoAtuacao = new ArqAtuacao();
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
        System.out.println("> Início > Atores");
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
                incluirAtor();
                break;
            case 2:
                buscarAtor();
                break;
            case 3:
                alterarAtor();
                break;
            case 4:
                excluirAtor();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

    } while (opcao != 0);
  }
  
    public void incluirAtor() {
      System.out.println("Inclusão de ator");
      String nome = "";
      int idade = -1;
      char sexo = 'M';
      String strsexo = "";
      boolean dadosCorretos = false;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      do {
          System.out.print("\nNome (vazio para cancelar): ");
          nome = console.nextLine();
          if(nome.length()==0)
              return;
          if(nome.length()<4)
              System.err.println("O nome do ator deve ter no mínimo 4 caracteres.");
      } while(nome.length()<4);

      do {
            dadosCorretos = false;
            System.out.print("Idade: ");
            if (console.hasNextInt()) {
                idade = console.nextInt();
                dadosCorretos = true;
            } else {
                System.err.println("Idade inválida! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);
      
      do {
          dadosCorretos = false;
          System.out.print("\nSexo (Vazio para cancelar): ");
          strsexo = console.nextLine();
          
          if(strsexo.length()==0)
              return;
          if(nome.length()>0){
            
            sexo = strsexo.charAt(0);
            dadosCorretos = true;
          }
          
      } while(!dadosCorretos);

      

      System.out.print("\nConfirma a inclusão do ator? (S/N) ");
      char resp = console.nextLine().charAt(0);
      if(resp=='S' || resp=='s') {
          try {
              Ator c = new Ator(nome, idade, sexo);
              arquivoAtor.create(c);
              System.out.println("Ator incluído com sucesso.");
          } catch(Exception e) {
              System.out.println("Erro do sistema. Não foi possível incluir o ator!");
          }
      }
  }
  
  
  
  public void buscarAtor() {
        System.out.println("Busca de ator");
        System.out.print("Nome: ");
        String nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
            return; 

        try {
            Ator[] ator = arquivoAtor.readNome(nome);  // Chama o método de leitura da classe Arquivo
            if (ator.length>0) {
                int n=1;
                for(Ator s : ator) {
                    System.out.println((n++)+": "+s.getNome());
                }
                System.out.print("Escolha o ator: ");
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
                mostrarAtor(ator[o-1]);  // Exibe os detalhes do ator encontrada
                System.out.println("Deseja exibir os papeis do autor? (S/N)");
                char resp = console.nextLine().charAt(0);
                if(resp=='S' || resp=='s') {
                    buscarAtuacao(ator[o-1]);
                }

            } else {
                System.out.println("Nenhum ator encontrado.");
            }
        } catch(Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar as series!");
            e.printStackTrace();
        }
    }

    public void buscarAtuacao(Ator a1) {
        System.out.println("Busca de atuação");
        System.out.print("Papel: ");
        String nome = a1.getNome();  // Lê o papel digitado pelo usuário

        if(nome.isEmpty())
            return; 

        try {
            int o = -1;
            Atuacao[] atuacao = arquivoAtuacao.readAtor(a1.getId());  // Chama o método de leitura da classe Arquivo
            if (atuacao.length>0) {
                int n=1;
                for(Atuacao s : atuacao) {
                    System.out.println((n++)+": "+s.getPapel());
                }
                System.out.print("Escolha a atuacao: ");
                
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
    
    public void mostrarAtuacao(Atuacao atuacao) {
        if (atuacao != null) {
            System.out.println("\nDetalhes da atuação:");
            System.out.println("----------------------");
            System.out.printf("Ator..: %s%n", atuacao.getAtorId());
            System.out.printf("Papel: %s%n", atuacao.getPapel());
            System.out.println("----------------------");
        }
      }

    public void alterarAtor() {
        System.out.println("\nAlteração de ator");
        boolean dadosCorretos;

        dadosCorretos = false;
        
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
      
            // Tenta ler o ator com o ID fornecido
            Ator atorSelecionado = ator[o-1];
            if (atorSelecionado != null) {
                mostrarAtor(atorSelecionado);  // Exibe os dados do ator para confirmação

                // Alteração de nome
                String novoNome;
                dadosCorretos = false;
                do {
                    System.out.print("Novo nome (Deixe em branco para manter o anterior): ");
                    novoNome = console.nextLine();
                    if(!novoNome.isEmpty()) {
                        if(novoNome.length()>=4) {
                            atorSelecionado.setNome(novoNome);  // Atualiza o nome se fornecido
                            dadosCorretos = true;
                        } else
                            System.err.println("O nome do ator deve ter no mínimo 4 caracteres.");
                    } else
                        dadosCorretos = true;
                } while(!dadosCorretos);

                // Alteração de idade
                int novaIdade;
                dadosCorretos = false;
                do {
                    dadosCorretos = false;
                    System.out.print("Idade: ");
                    if (console.hasNextInt()) {
                        novaIdade = console.nextInt();
                        atorSelecionado.setIdade(novaIdade);
                        dadosCorretos = true;
                    } else {
                        System.err.println("Idade inválida! Por favor, insira um número válido.");
                    }
                    console.nextLine(); // Limpar o buffer 
                } while(!dadosCorretos);
                
                // Alteração de sexo
                String strsexo;
                char novosexo;
                do {
                  dadosCorretos = false;
                  System.out.print("\nSexo (Deixe em branco para manter o anterior): ");
                  strsexo = console.nextLine();
                  
                  if(strsexo.length()==0)
                    dadosCorretos = true;
                  if(strsexo.length()>0){
                    
                    novosexo = strsexo.charAt(0);
                    dadosCorretos = true;
                  }
                } while(!dadosCorretos);
                

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arquivoAtor.update(atorSelecionado);
                    if (alterado) {
                        System.out.println("Ator alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o ator.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
                 console.nextLine(); // Limpar o buffer 
            } else {
                System.out.println("Ator não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o ator!");
            e.printStackTrace();
        }
        
    }
    
    public void excluirAtor() {
        System.out.println("\nExclusão de ator");
        
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

                System.out.print("\nConfirma a exclusão do ator? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arquivoAtor.delete(atorSelecionado.getId());  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Ator excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o ator.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Ator não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o ator!");
            e.printStackTrace();
        }
    }
}
