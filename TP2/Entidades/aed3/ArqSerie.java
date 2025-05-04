package Entidades.aed3;
import Model.Serie;
import Model.ParNomeId;
import Model.ParIdId;

import java.util.ArrayList;

import Entidades.aed3.*;

public class ArqSerie extends Arquivo<Serie> {
    Arquivo<Serie> arquivo;
    ArqAtuacao arquivoAtuacao;
    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceEpisodio;
    
    public ArqSerie() throws Exception {
        super("Series", Serie.class.getConstructor());

        this.arquivoAtuacao = new ArqAtuacao();
        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(), 
            5, 
            "./Dados/Series/indiceNome.db");
        indiceEpisodio = new ArvoreBMais<>(
            ParIdId.class.getConstructor(),
            5,
            "./Dados/Episodio/indiceId.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        return id;
    }
    
    public Serie readId(int pid) throws Exception{
      return read(pid);
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
          ArrayList<ParIdId> ptis = indiceEpisodio.read(new ParIdId(id, -1));
          if(ptis.size() == 0){
            if(super.delete(id))
                return indiceNome.delete(new ParNomeId(s.getNome(), id)) && arquivoAtuacao.deleteSerie(id);
          }else{
            System.out.println("Erro! Exclua os episódios antes de excluir a série!");
          }
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
