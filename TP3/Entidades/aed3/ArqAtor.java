package Entidades.aed3;

import Model.Ator;
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


public class ArqAtor extends Arquivo<Ator> {

    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceAtor;

    public ListaInvertida listaInvertida;

    public ArqAtor() throws Exception {
        super("Atores", Ator.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(),
            5,
            "./Dados/Atores/indiceNome.db"
        );

        indiceAtor = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./Dados/Atuacao/indiceAtorAtuacao.db"
        );

        listaInvertida = new ListaInvertida(
            10,
            "./Dados/ListaInvertida/dicionarioAtor.db",
            "./Dados/ListaInvertida/blocosAtor.db"
        );
    }

    @Override
    public int create(Ator a) throws Exception {
        int id = super.create(a);

        indiceNome.create(new ParNomeId(a.getNome(), id));

        // Indexa na lista invertida
        indexarNaListaInvertida(a.getNome(), id);
        listaInvertida.incrementaEntidades();

        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        if (ptis.size() > 0) {
            Ator[] atores = new Ator[ptis.size()];
            int i = 0;
            for (ParNomeId pti : ptis)
                atores[i++] = read(pti.getId());
            return atores;
        } else
            return null;
    }

    public Ator readId(int pid) throws Exception {
        return read(pid);
    }

    @Override
    public boolean delete(int id) throws Exception {
        Ator a = read(id);
        if (a != null) {
            ArrayList<ParIdId> ptis = indiceAtor.read(new ParIdId(id, -1));
            if (ptis.size() == 0) {
                if (super.delete(id)) {
                    indiceNome.delete(new ParNomeId(a.getNome(), id));

                    // Remove da lista invertida
                    removerDaListaInvertida(a.getNome(), id);
                    listaInvertida.decrementaEntidades();

                    return true;
                }
            } else {
                System.out.println("Erro! Exclua as atuações antes de excluir o ator!");
            }
        }
        return false;
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator a = read(novoAtor.getId());
        if (a != null) {
            if (super.update(novoAtor)) {
                if (!a.getNome().equals(novoAtor.getNome())) {
                    indiceNome.delete(new ParNomeId(a.getNome(), a.getId()));
                    indiceNome.create(new ParNomeId(novoAtor.getNome(), novoAtor.getId()));

                    // Atualiza na lista invertida
                    removerDaListaInvertida(a.getNome(), a.getId());
                    indexarNaListaInvertida(novoAtor.getNome(), novoAtor.getId());
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
