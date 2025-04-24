package Entidades.aed3;
import Model.Ator;
import Model.ParNomeId;
import Model.ParIdId;

import java.util.ArrayList;

import Entidades.aed3.*;

public class ArqAtor extends Arquivo<Ator> {
    Arquivo<Ator> arquivo;
    ArvoreBMais<ParNomeId> indiceNome;
    
    public ArqAtor() throws Exception {
        super("Atores", Ator.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(), 
            5, 
            "./Dados/Atores/indiceNome.db");
    }

    @Override
    public int create(Ator s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        if(ptis.size()>0) {
            Ator[] series = new Ator[ptis.size()];
            int i=0;
            for(ParNomeId pti: ptis) 
                series[i++] = read(pti.getId());
            return series;
        }
        else 
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Ator a = read(id);   // na superclasse
        if(a!=null) {
            if(super.delete(id))
                return indiceNome.delete(new ParNomeId(a.getNome(), id));
        }
        return false;
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator s = read(novoAtor.getId());    // na superclasse
        if(s!=null) {
            if(super.update(novoAtor)) {
                if(!s.getNome().equals(novoAtor.getNome())) {
                    indiceNome.delete(new ParNomeId(s.getNome(), s.getId()));
                    indiceNome.create(new ParNomeId(novoAtor.getNome(), novoAtor.getId()));
                }
                return true;
            }
        }
        return false;
    }

}
