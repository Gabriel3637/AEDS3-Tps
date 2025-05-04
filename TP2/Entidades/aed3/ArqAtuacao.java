package Entidades.aed3;

import java.util.ArrayList;

import Model.Atuacao;
import Model.ParIdId;
import estruturas.*;

public class ArqAtuacao extends Arquivo<Atuacao> {

    ArvoreBMais<ParIdId> indiceSerie;
    ArvoreBMais<ParIdId> indiceAtor;

    public ArqAtuacao() throws Exception {
        super("Atuacao", Atuacao.class.getConstructor());

        indiceSerie = new ArvoreBMais<>(
            ParIdId.class.getConstructor(), 
            5, 
            "./Dados/indiceSerieAtuacao.db");

        indiceAtor = new ArvoreBMais<>(
            ParIdId.class.getConstructor(), 
            5, 
            "./Dados/indiceAtorAtuacao.db");
    }

    @Override
    public int create(Atuacao atuacao) throws Exception {
        int id = super.create(atuacao);
        atuacao.setId(id);

        indiceSerie.create(new ParIdId(atuacao.getSerieId(), id));
        indiceAtor.create(new ParIdId(atuacao.getAtorId(), id));

        return id;
    }

    public Atuacao[] readBySerieId(int serieId) throws Exception {
        ArrayList<ParIdId> lista = indiceSerie.read(new ParIdId(serieId, -1));
        if (lista == null || lista.size() == 0) return null;

        Atuacao[] atuacoes = new Atuacao[lista.size()];
        int i = 0;
        for (ParIdId p : lista) {
            atuacoes[i++] = read(p.getEpId());
        }

        return atuacoes;
    }

    public Atuacao[] readByAtorId(int atorId) throws Exception {
        ArrayList<ParIdId> lista = indiceAtor.read(new ParIdId(atorId, -1));
        if (lista == null || lista.size() == 0) return null;

        Atuacao[] atuacoes = new Atuacao[lista.size()];
        int i = 0;
        for (ParIdId p : lista) {
            atuacoes[i++] = read(p.getEpId());
        }

        return atuacoes;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Atuacao a = read(id);
        if (a != null) {
            if (super.delete(id)) {
                indiceSerie.delete(new ParIdId(a.getSerieId(), id));
                indiceAtor.delete(new ParIdId(a.getAtorId(), id));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(Atuacao novaAtuacao) throws Exception {
        Atuacao antiga = read(novaAtuacao.getId());
        if (antiga != null) {
            if (super.update(novaAtuacao)) {

                if (antiga.getSerieId() != novaAtuacao.getSerieId()) {
                    indiceSerie.delete(new ParIdId(antiga.getSerieId(), antiga.getId()));
                    indiceSerie.create(new ParIdId(novaAtuacao.getSerieId(), novaAtuacao.getId()));
                }

                if (antiga.getAtorId() != novaAtuacao.getAtorId()) {
                    indiceAtor.delete(new ParIdId(antiga.getAtorId(), antiga.getId()));
                    indiceAtor.create(new ParIdId(novaAtuacao.getAtorId(), novaAtuacao.getId()));
                }

                return true;
            }
        }
        return false;
    }

    public boolean deleteBySerie(int serieId) throws Exception {
        Atuacao[] atuacoes = readBySerieId(serieId);
        if (atuacoes != null) {
            for (Atuacao a : atuacoes) {
                delete(a.getId());
            }
        }
        return true;
    }

    public boolean existsForAtor(int atorId) throws Exception {
        Atuacao[] atuacoes = readByAtorId(atorId);
        return atuacoes != null && atuacoes.length > 0;
    }

}
