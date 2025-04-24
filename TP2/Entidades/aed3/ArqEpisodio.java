package Entidades.aed3;
import Model.Serie;
import Model.Episodio;
import Model.ParNomeId;
import Model.ParIdId;

import java.util.ArrayList;

import Entidades.aed3.*;

public class ArqEpisodio extends Arquivo<Episodio> {
    Arquivo<Episodio> arquivo;
    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceSerie;

    public ArqEpisodio() throws Exception {
        super("Episodio", Episodio.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(), 
            5, 
            "./Dados/Episodio/indiceNome.db");

        indiceSerie = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./Dados/Episodio/indiceId.db");
        
    }

    @Override
    public int create(Episodio s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        indiceSerie.create(new ParIdId(s.getSerieId(),id));
        return id;
    }

    public Episodio[] readNomeSerieId(String nome, int serieId) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ParNomeId> ptis = indiceNome.read(new ParNomeId(nome, -1));
        ArrayList<ParIdId> ptis2 = indiceSerie.read(new ParIdId(serieId, -1));
        if(ptis.size()>0 && ptis2.size()>0) {
            int maior;
            if(ptis.size() > ptis2.size()){
                maior = ptis.size();
            }else{
                maior = ptis2.size();
            }
            Episodio[] ep = new Episodio[maior];
            int i=0;
            for(ParNomeId pti: ptis)
                for(ParIdId pti2: ptis2){
                    if(pti.getId() == pti2.getEpId()){
                        ep[i++] = read(pti2.getEpId());
                    }
                } 
                
            return ep;
        }
        else 
            return null;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio s = read(id);   // na superclasse
        if(s!=null) {
            if(super.delete(id))
                return indiceNome.delete(new ParNomeId(s.getNome(), id)) && indiceSerie.delete(new ParIdId(s.getSerieId(), id));
        }
        return false;
    }

    @Override
    public boolean update(Episodio novoEp) throws Exception {
        Episodio s = read(novoEp.getId());    // na superclasse
        if(s!=null) {
            if(super.update(novoEp)) {
                if(!s.getNome().equals(novoEp.getNome())) {
                    indiceNome.delete(new ParNomeId(s.getNome(), s.getId()));
                    indiceNome.create(new ParNomeId(novoEp.getNome(), novoEp.getId()));
                }
                if(s.getSerieId() != (novoEp.getSerieId())) {
                    indiceSerie.delete(new ParIdId(s.getSerieId(), s.getId()));
                    indiceSerie.create(new ParIdId(novoEp.getSerieId(), novoEp.getId()));
                }
                return true;
            }
        }
        return false;
    }

}
