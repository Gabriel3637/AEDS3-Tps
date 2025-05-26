package Entidades.aed3;
import Model.Serie;
import Model.ParNomeId;
import Model.ParIdId;

import java.util.ArrayList;

import Entidades.aed3.*;
import Entidades.aed3.ListaInvertida.ElementoLista;
import Entidades.aed3.ListaInvertida.ListaInvertidaImplementada;

public class ArqSerie extends Arquivo<Serie> {
    Arquivo<Serie> arquivo;
    ArqAtuacao arquivoAtuacao;
    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceEpisodio;
    ListaInvertidaImplementada lista;
    
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
            lista = new ListaInvertidaImplementada("Dados/ListaInvertida/stopwords.txt", 4,"Dados/ListaInvertida/Atores/dicionario.listainv.db", "Dados/ListaInvertida/Atores/blocos.listainv.db");
    }

    @Override
    public int create(Serie s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        lista.inserir(s.getNome(), s.getId());
        return id;
    }
    
    public Serie readId(int pid) throws Exception{
      return read(pid);
    }

    public Serie[] readNome(String nome) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ElementoLista> ptis = lista.buscar(nome);
        if(ptis.size()>0) {
            Serie[] series = new Serie[ptis.size()];
            int i=0;
            for(ElementoLista pti: ptis) 
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
                return lista.excluir(s.getNome(), s.getId());
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
                    lista.atualizar(s.getNome(), novaSerie.getNome(), novaSerie.getId());
                }
                return true;
            }
        }
        return false;
    }

}
