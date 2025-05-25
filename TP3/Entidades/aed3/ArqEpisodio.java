package Entidades.aed3;

import Model.Episodio;
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


public class ArqEpisodio extends Arquivo<Episodio> {

    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceSerie;

    public ListaInvertida listaInvertida;

    public ArqEpisodio() throws Exception {
        super("Episodio", Episodio.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(),
            5,
            "./Dados/Episodio/indiceNome.db"
        );

        indiceSerie = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./Dados/Episodio/indiceId.db"
        );

        listaInvertida = new ListaInvertida(
            10,
            "./Dados/ListaInvertida/dicionarioEpisodio.db",
            "./Dados/ListaInvertida/blocosEpisodio.db"
        );
    }

    @Override
    public int create(Episodio ep) throws Exception {
        int id = super.create(ep);

        indiceNome.create(new ParNomeId(ep.getNome(), id));
        indiceSerie.create(new ParIdId(ep.getSerieId(), id));

        // Indexar na lista invertida
        indexarNaListaInvertida(ep.getNome(), id);
        listaInvertida.incrementaEntidades();

        return id;
    }

    public Episodio[] readNomeSerieId(String nome, int serieId) throws Exception {
        if (nome.length() == 0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        ArrayList<ParIdId> ptis2 = indiceSerie.read(new ParIdId(serieId, -1));

        if (ptis.size() > 0 && ptis2.size() > 0) {
            ArrayList<Episodio> resultado = new ArrayList<>();
            for (ParNomeId pti : ptis) {
                for (ParIdId pti2 : ptis2) {
                    if (pti.getId() == pti2.getEpId()) {
                        resultado.add(read(pti2.getEpId()));
                    }
                }
            }
            return resultado.toArray(new Episodio[resultado.size()]);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio ep = read(id);
        if (ep != null) {
            if (super.delete(id)) {
                indiceNome.delete(new ParNomeId(ep.getNome(), id));
                indiceSerie.delete(new ParIdId(ep.getSerieId(), id));

                // Remover da lista invertida
                removerDaListaInvertida(ep.getNome(), id);
                listaInvertida.decrementaEntidades();

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEp) throws Exception {
        Episodio ep = read(novoEp.getId());
        if (ep != null) {
            if (super.update(novoEp)) {
                if (!ep.getNome().equals(novoEp.getNome())) {
                    indiceNome.delete(new ParNomeId(ep.getNome(), ep.getId()));
                    indiceNome.create(new ParNomeId(novoEp.getNome(), novoEp.getId()));

                    // Atualiza na lista invertida
                    removerDaListaInvertida(ep.getNome(), ep.getId());
                    indexarNaListaInvertida(novoEp.getNome(), novoEp.getId());
                }
                if (ep.getSerieId() != novoEp.getSerieId()) {
                    indiceSerie.delete(new ParIdId(ep.getSerieId(), ep.getId()));
                    indiceSerie.create(new ParIdId(novoEp.getSerieId(), novoEp.getId()));
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

    public Episodio readId(int pid) throws Exception {
        return read(pid);
    }
    
}
