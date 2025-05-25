package Entidades.aed3;

import Model.Serie;
import Model.ParNomeId;
import Model.ParIdId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entidades.aed3.ListaInvertida.ElementoLista;
import Entidades.aed3.ListaInvertida.ListaInvertida;
import Entidades.aed3.ListaInvertida.TextoUtil;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ArqSerie extends Arquivo<Serie> {

    ArqAtuacao arquivoAtuacao;
    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceEpisodio;

    public ListaInvertida listaInvertida;

    public ArqSerie() throws Exception {
        super("Series", Serie.class.getConstructor());

        this.arquivoAtuacao = new ArqAtuacao();

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(),
            5,
            "./Dados/Series/indiceNome.db"
        );

        indiceEpisodio = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./Dados/Episodio/indiceId.db"
        );

        listaInvertida = new ListaInvertida(
            10,
            "./Dados/ListaInvertida/dicionarioSerie.db",
            "./Dados/ListaInvertida/blocosSerie.db"
        );
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);

        // Índice Nome tradicional
        indiceNome.create(new ParNomeId(s.getNome(), id));

        // Índice Invertido
        indexarNaListaInvertida(s.getNome(), id);

        listaInvertida.incrementaEntidades();

        return id;
    }

    public Serie readId(int pid) throws Exception {
        return read(pid);
    }

    public Serie[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        if (ptis.size() > 0) {
            Serie[] series = new Serie[ptis.size()];
            int i = 0;
            for (ParNomeId pti : ptis)
                series[i++] = read(pti.getId());
            return series;
        } else
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie s = read(id);

        if (s != null) {
            ArrayList<ParIdId> ptis = indiceEpisodio.read(new ParIdId(id, -1));
            if (ptis.size() == 0) {
                if (super.delete(id)) {
                    indiceNome.delete(new ParNomeId(s.getNome(), id));
                    arquivoAtuacao.deleteSerie(id);

                    // Remove da lista invertida
                    removerDaListaInvertida(s.getNome(), id);
                    listaInvertida.decrementaEntidades();

                    return true;
                }
            } else {
                System.out.println("Erro! Exclua os episódios antes de excluir a série!");
            }
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getId());
        if (s != null) {
            if (super.update(novaSerie)) {

                if (!s.getNome().equals(novaSerie.getNome())) {
                    indiceNome.delete(new ParNomeId(s.getNome(), s.getId()));
                    indiceNome.create(new ParNomeId(novaSerie.getNome(), novaSerie.getId()));

                    // Atualiza na lista invertida
                    removerDaListaInvertida(s.getNome(), s.getId());
                    indexarNaListaInvertida(novaSerie.getNome(), novaSerie.getId());
                }
                return true;
            }
        }
        return false;
    }

    // 👉 Métodos auxiliares para Lista Invertida

    private void indexarNaListaInvertida(String texto, int id) throws Exception {
        List<String> termos = TextoUtil.processarTexto(texto);
        int total = termos.size();
        if (total == 0)
            return;

        Map<String, Integer> contagem = new HashMap<>();
        for (String termo : termos) {
            contagem.put(termo, contagem.getOrDefault(termo, 0) + 1);
        }

        for (String termo : contagem.keySet()) {
            float tf = (float) contagem.get(termo) / total;
            listaInvertida.create(termo, new ElementoLista(id, tf));
        }
    }

    private void removerDaListaInvertida(String texto, int id) throws Exception {
        List<String> termos = TextoUtil.processarTexto(texto);
        for (String termo : termos) {
            listaInvertida.delete(termo, id);
        }
    }
}
