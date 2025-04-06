package Entidades.aed3;
import Model.Serie;
import Model.ParNomeId;

import java.util.ArrayList;

import Entidades.aed3.*;

public class ArqSerie extends Arquivo<Serie> {
    Arquivo<Serie> arquivo;
    ArvoreBMais<ParNomeId> indiceNome;

    public ArqSerie() throws Exception {
        super("Series", Serie.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(), 
            5, 
            "./Dados/Series/indiceNome.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        return id;
    }

    public Serie[] readNome(String nome) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        if(ptis.size()>0) {
            Serie[] series = new Serie[ptis.size()];
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
        Serie s = read(id);   // na superclasse
        if(s!=null) {
            if(super.delete(id))
                return indiceNome.delete(new ParNomeId(s.getNome(), id));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie s = read(novaSerie.getId());    // na superclasse
        if(s!=null) {
            if(super.update(novaSerie)) {
                if(!s.getNome().equals(novaSerie.getNome())) {
                    indiceNome.delete(new ParNomeId(s.getNome(), s.getId()));
                    indiceNome.create(new ParNomeId(novaSerie.getNome(), novaSerie.getId()));
                }
                return true;
            }
        }
        return false;
    }

}
