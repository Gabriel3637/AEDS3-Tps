package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Episodio;

public class MenuEpisodio {
    ArquivoEpisodio arqEpisodio;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodio() throws Exception {
        arqEpisodio = new ArquivoEpisodio();
    }

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
            } catch (NumberFormatException e) {
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
            if (nome.length() == 0)
                return;
            if (nome.length() < 4)
                System.err.println("O nome do episodio deve ter no mínimo 4 caracteres.");
        } while (nome.length() < 4);

        do {
            System.out.print("\nTemporada (-1 para cancelar): ");
            try {
                temporada = Short.parseShort(console.nextLine());
            } catch (Exception e) {
                temporada = 0;
            }

            if (temporada == -1)
                return;
            if (temporada == 0)
                System.err.println("Erro, tente novamente");
        } while (temporada == 0);

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
        } while (!dadosCorretos);

        do {
            System.out.print("\nTemporada (-1 para cancelar): ");
            try {
                duracao = Short.parseShort(console.nextLine());
            } catch (Exception e) {
                duracao = 0;
            }

            if (duracao == -1)
                return;
            if (duracao == 0)
                System.err.println("Erro, tente novamente");
        } while (duracao == 0);

        System.out.print("\nConfirma a inclusão de episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Episodio c = new Episodio(serieId, nome, temporada, lancamento, duracao);
                arqEpisodio.create(c);
                System.out.println("Episodio incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir o episodio!");
            }
        }
    }

    public void buscarEpisodio(int serieId) {
        System.out.println("\nBusca de episodio");
        String nome;
        boolean nomeValido = false;

        do {
            System.out.print("\nNome: ");
            nome = console.nextLine(); // Lê o CPF digitado pelo usuário

            if (nome.isEmpty())
                return;

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (nome.length() > 0) {
                nomeValido = true; // CPF válido
            } else {
                System.out.println("Nome inválido.");
            }
        } while (!nomeValido);

        try {
            Episodio ep = arqEpisodio.read(nome, serieId); // Chama o método de leitura da classe Arquivo
            if (ep != null) {
                mostraEpisodio(ep); // Exibe os detalhes do cliente encontrado
            } else {
                System.out.println("Episodio não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível buscar o episodio!");
            e.printStackTrace();
        }
    }

    public void alterarEpisodio(int serieId) {
        System.out.println("\nAlteração de episodio");
        String nome;
        boolean nomeValido = false;

        do {
            System.out.print("\nNome: ");
            nome = console.nextLine(); // Lê o CPF digitado pelo usuário

            if (nome.isEmpty())
                return;

            // Validação do CPF (11 dígitos e composto apenas por números)
            if (nome.length() > 0) {
                nomeValido = true; // CPF válido
            } else {
                System.out.println("Nome inválido.");
            }
        } while (!nomeValido);

        try {
            // Tenta ler o cliente com o ID fornecido

            Episodio ep = arqEpisodio.read(nome, serieId); // Chama o método de leitura da classe Arquivo
            if (ep != null) {
                mostraEpisodio(ep); // Exibe os detalhes do cliente encontrado
                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) {
                    ep.setNome(novoNome); // Atualiza o nome se fornecido
                }

                // Alteração de temp
                System.out.print("Nova temp (deixe em branco para manter o anterior): ");
                String novoTemp = console.nextLine();
                if (!novoTemp.isEmpty()) {
                    ep.setTemporada(Short.parseShort(console.nextLine()));
                }

                // Alteração de duraçao
                System.out.print("Nova duracao (deixe em branco para manter o anterior): ");
                String novoDur = console.nextLine();
                if (!novoDur.isEmpty()) {
                    ep.setDuracao(Short.parseShort(console.nextLine()));
                }

                // Alteração de data de lancamento
                System.out.print("Nova data de lancamento (DD/MM/AAAA) (deixe em branco para manter a anterior): ");
                String novaDataStr = console.nextLine();
                if (!novaDataStr.isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        ep.setLancamento(LocalDate.parse(novaDataStr, formatter)); // Atualiza a data de nascimento se
                                                                                   // fornecida
                    } catch (Exception e) {
                        System.err.println("Data inválida. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqEpisodio.update(ep);
                    if (alterado) {
                        System.out.println("Cliente alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o cliente.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Episodio não encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o ep!");
            e.printStackTrace();
        }

    }

    public void excluirEpisodio(int serieId) {
        System.out.println("\nExclusão de episodio");
        String nome;
        boolean nomeValido = false;

        do {
            System.out.print("\nNome: ");
            nome = console.nextLine(); 

            if (nome.isEmpty())
                return;

            if (nome.length() > 0) {
                nomeValido = true; 
            } else {
                System.out.println("Nome inválido.");
            }
        } while (!nomeValido);

        try {
            
            Episodio ep = arqEpisodio.read(nome, serieId);
            if (ep != null) {
                System.out.println("Ep encontrado:");
                mostraEpisodio(ep);  // Exibe os dados do ep para confirmação

                System.out.print("\nConfirma a exclusão do Ep? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodio.delete(nome, serieId);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Cliente excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o cliente.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o cliente!");
            e.printStackTrace();
        }
    }

}
