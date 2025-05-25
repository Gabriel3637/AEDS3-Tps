package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Model.Serie;
import Entidades.aed3.ArqSerie;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MenuSeries {
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
            System.out.println("2) Buscar por termos (Lista Invertida)");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("5) Episódios");
            System.out.println("6) Atuação");
            System.out.println("0) Retomar ao menu anterior");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirSerie();
                    break;
                case 2:
                    buscarSeriePorTermo();
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
        boolean dadosCorretos;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.print("\nNome (min. de 4 letras ou vazio para cancelar): ");
            nome = console.nextLine();
            if (nome.length() == 0)
                return;
            if (nome.length() < 4)
                System.err.println("O nome da série deve ter no mínimo 4 caracteres.");
        } while (nome.length() < 4);

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
        } while (!dadosCorretos);

        do {
            System.out.print("Sinopse (min. de 20 letras ou vazio para cancelar): ");
            sinopse = console.nextLine();
            if (sinopse.length() == 0)
                return;
            if (sinopse.length() <= 20)
                System.err.println("A sinopse da série deve ter no mínimo 20 caracteres.");
        } while (sinopse.length() <= 20);

        do {
            System.out.print("\nPlataforma de streaming (min. de 2 letras ou vazio para cancelar): ");
            streaming = console.nextLine();
            if (streaming.length() == 0)
                return;
            if (streaming.length() < 2)
                System.err.println("O nome da plataforma de streaming deve ter no mínimo 2 caracteres.");
        } while (streaming.length() < 2);

        System.out.print("\nConfirma a inclusão da serie? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Serie c = new Serie(nome, lancamento, sinopse, streaming);
                arquivoSerie.create(c);
                System.out.println("Serie incluída com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível incluir a série!");
            }
        }
    }

    public void buscarSeriePorTermo() {
        System.out.println("Busca de série por termos");
        System.out.print("Digite os termos de busca: ");
        String consulta = console.nextLine();

        if (consulta.isEmpty()) return;

        try {
            List<String> termos = Entidades.aed3.ListaInvertida.TextoUtil.processarTexto(consulta);
            if (termos.isEmpty()) {
                System.out.println("Nenhum termo válido na busca.");
                return;
            }

            int N = arquivoSerie.listaInvertida.numeroEntidades();
            Map<Integer, Float> scores = new HashMap<>();

            for (String termo : termos) {
                var lista = arquivoSerie.listaInvertida.read(termo);
                if (lista.length == 0) continue;

                float idf = (float) (Math.log((double) N / lista.length) + 1);

                for (var elemento : lista) {
                    float score = elemento.getFrequencia() * idf;
                    scores.put(elemento.getId(), scores.getOrDefault(elemento.getId(), 0f) + score);
                }
            }

            if (scores.isEmpty()) {
                System.out.println("Nenhuma série encontrada com esses termos.");
                return;
            }

            List<Map.Entry<Integer, Float>> resultados = new ArrayList<>(scores.entrySet());
            resultados.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));

            int n = 1;
            for (var entry : resultados) {
                Serie serie = arquivoSerie.readId(entry.getKey());
                System.out.printf("%d) %s (Score: %.3f)\n", n++, serie.getNome(), entry.getValue());
            }

            System.out.print("Escolha a série para ver detalhes (ou 0 para sair): ");
            int escolha;
            try {
                escolha = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                escolha = 0;
            }

            if (escolha > 0 && escolha <= resultados.size()) {
                Serie s = arquivoSerie.readId(resultados.get(escolha - 1).getKey());
                mostrarSerie(s);
            }

        } catch (Exception e) {
            System.out.println("Erro na busca: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void EpisodiosSerie() {
        System.out.println("Busca da série para gerenciar episódios:");
        System.out.print("Digite os termos de busca: ");
        String consulta = console.nextLine();
    
        if (consulta.isEmpty()) return;
    
        try {
            List<String> termos = Entidades.aed3.ListaInvertida.TextoUtil.processarTexto(consulta);
            if (termos.isEmpty()) {
                System.out.println("Nenhum termo válido na busca.");
                return;
            }
    
            int N = arquivoSerie.listaInvertida.numeroEntidades();
            Map<Integer, Float> scores = new HashMap<>();
    
            for (String termo : termos) {
                var lista = arquivoSerie.listaInvertida.read(termo);
                if (lista.length == 0) continue;
    
                float idf = (float) (Math.log((double) N / lista.length) + 1);
    
                for (var elemento : lista) {
                    float score = elemento.getFrequencia() * idf;
                    scores.put(elemento.getId(), scores.getOrDefault(elemento.getId(), 0f) + score);
                }
            }
    
            if (scores.isEmpty()) {
                System.out.println("Nenhuma série encontrada com esses termos.");
                return;
            }
    
            List<Map.Entry<Integer, Float>> resultados = new ArrayList<>(scores.entrySet());
            resultados.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));
    
            int n = 1;
            for (var entry : resultados) {
                Serie serie = arquivoSerie.readId(entry.getKey());
                System.out.printf("%d) %s (Score: %.3f)\n", n++, serie.getNome(), entry.getValue());
            }
    
            System.out.print("Escolha a série para gerenciar episódios (ou 0 para sair): ");
            int escolha;
            try {
                escolha = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                escolha = 0;
            }
    
            if (escolha > 0 && escolha <= resultados.size()) {
                int serieId = resultados.get(escolha - 1).getKey();
                (new MenuEpisodio(serieId)).menu();
            }
    
        } catch (Exception e) {
            System.out.println("Erro na busca ou ao abrir menu de episódios: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void AtuacaoSerie() {
        System.out.println("Busca da série para gerenciar atuações:");
        System.out.print("Digite os termos de busca: ");
        String consulta = console.nextLine();
    
        if (consulta.isEmpty()) return;
    
        try {
            List<String> termos = Entidades.aed3.ListaInvertida.TextoUtil.processarTexto(consulta);
            if (termos.isEmpty()) {
                System.out.println("Nenhum termo válido na busca.");
                return;
            }
    
            int N = arquivoSerie.listaInvertida.numeroEntidades();
            Map<Integer, Float> scores = new HashMap<>();
    
            for (String termo : termos) {
                var lista = arquivoSerie.listaInvertida.read(termo);
                if (lista.length == 0) continue;
    
                float idf = (float) (Math.log((double) N / lista.length) + 1);
    
                for (var elemento : lista) {
                    float score = elemento.getFrequencia() * idf;
                    scores.put(elemento.getId(), scores.getOrDefault(elemento.getId(), 0f) + score);
                }
            }
    
            if (scores.isEmpty()) {
                System.out.println("Nenhuma série encontrada com esses termos.");
                return;
            }
    
            List<Map.Entry<Integer, Float>> resultados = new ArrayList<>(scores.entrySet());
            resultados.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));
    
            int n = 1;
            for (var entry : resultados) {
                Serie serie = arquivoSerie.readId(entry.getKey());
                System.out.printf("%d) %s (Score: %.3f)\n", n++, serie.getNome(), entry.getValue());
            }
    
            System.out.print("Escolha a série para gerenciar atuações (ou 0 para sair): ");
            int escolha;
            try {
                escolha = Integer.parseInt(console.nextLine());
            } catch (NumberFormatException e) {
                escolha = 0;
            }
    
            if (escolha > 0 && escolha <= resultados.size()) {
                int serieId = resultados.get(escolha - 1).getKey();
                (new MenuAtuacao(serieId)).menu();
            }
    
        } catch (Exception e) {
            System.out.println("Erro na busca ou ao abrir menu de atuações: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public void alterarSerie() {
        System.out.println("Alteração de série");
        System.out.print("Digite os termos da série para buscar: ");
        String termos = console.nextLine();
        if (termos.isEmpty()) return;
    
        try {
            List<String> listaTermos = Entidades.aed3.ListaInvertida.TextoUtil.processarTexto(termos);
            Map<Integer, Float> scores = new HashMap<>();
            int N = arquivoSerie.listaInvertida.numeroEntidades();
    
            for (String termo : listaTermos) {
                var lista = arquivoSerie.listaInvertida.read(termo);
                if (lista.length == 0) continue;
                float idf = (float) (Math.log((double) N / lista.length) + 1);
                for (var elemento : lista) {
                    float score = elemento.getFrequencia() * idf;
                    scores.put(elemento.getId(), scores.getOrDefault(elemento.getId(), 0f) + score);
                }
            }
    
            if (scores.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
    
            List<Map.Entry<Integer, Float>> resultados = new ArrayList<>(scores.entrySet());
            resultados.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));
    
            int n = 1;
            for (var entry : resultados) {
                Serie serie = arquivoSerie.readId(entry.getKey());
                System.out.printf("%d) %s (Score: %.3f)\n", n++, serie.getNome(), entry.getValue());
            }
    
            System.out.print("Escolha a série para alterar (ou 0 para sair): ");
            int escolha = Integer.parseInt(console.nextLine());
    
            if (escolha <= 0 || escolha > resultados.size()) return;
    
            Serie s = arquivoSerie.readId(resultados.get(escolha - 1).getKey());
            mostrarSerie(s);
    
            System.out.print("Novo nome (ou ENTER para manter): ");
            String nome = console.nextLine();
            if (!nome.isEmpty()) s.setNome(nome);
    
            System.out.print("Nova sinopse (ou ENTER para manter): ");
            String sinopse = console.nextLine();
            if (!sinopse.isEmpty()) s.setSinopse(sinopse);
    
            System.out.print("Nova plataforma de streaming (ou ENTER para manter): ");
            String streaming = console.nextLine();
            if (!streaming.isEmpty()) s.setStreaming(streaming);
    
            System.out.print("Nova data de lançamento (DD/MM/AAAA) ou ENTER para manter: ");
            String dataStr = console.nextLine();
            if (!dataStr.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate lancamento = LocalDate.parse(dataStr, formatter);
                    s.setLancamento(lancamento);
                } catch (Exception e) {
                    System.err.println("Data inválida. Mantida a anterior.");
                }
            }
    
            arquivoSerie.update(s);
            System.out.println("Série alterada com sucesso.");
    
        } catch (Exception e) {
            System.out.println("Erro na alteração.");
            e.printStackTrace();
        }
    }

    public void excluirSerie() {
        System.out.println("Exclusão de série");
        System.out.print("Digite os termos da série para buscar: ");
        String termos = console.nextLine();
        if (termos.isEmpty()) return;
    
        try {
            List<String> listaTermos = Entidades.aed3.ListaInvertida.TextoUtil.processarTexto(termos);
            Map<Integer, Float> scores = new HashMap<>();
            int N = arquivoSerie.listaInvertida.numeroEntidades();
    
            for (String termo : listaTermos) {
                var lista = arquivoSerie.listaInvertida.read(termo);
                if (lista.length == 0) continue;
                float idf = (float) (Math.log((double) N / lista.length) + 1);
                for (var elemento : lista) {
                    float score = elemento.getFrequencia() * idf;
                    scores.put(elemento.getId(), scores.getOrDefault(elemento.getId(), 0f) + score);
                }
            }
    
            if (scores.isEmpty()) {
                System.out.println("Nenhuma série encontrada.");
                return;
            }
    
            List<Map.Entry<Integer, Float>> resultados = new ArrayList<>(scores.entrySet());
            resultados.sort((a, b) -> Float.compare(b.getValue(), a.getValue()));
    
            int n = 1;
            for (var entry : resultados) {
                Serie serie = arquivoSerie.readId(entry.getKey());
                System.out.printf("%d) %s (Score: %.3f)\n", n++, serie.getNome(), entry.getValue());
            }
    
            System.out.print("Escolha a série para excluir (ou 0 para sair): ");
            int escolha = Integer.parseInt(console.nextLine());
    
            if (escolha <= 0 || escolha > resultados.size()) return;
    
            Serie s = arquivoSerie.readId(resultados.get(escolha - 1).getKey());
            mostrarSerie(s);
    
            System.out.print("\nConfirma a exclusão? (S/N) ");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                arquivoSerie.delete(s.getId());
                System.out.println("Série excluída com sucesso.");
            } else {
                System.out.println("Exclusão cancelada.");
            }
    
        } catch (Exception e) {
            System.out.println("Erro na exclusão.");
            e.printStackTrace();
        }
    }
    
}
