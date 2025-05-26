package Entidades.aed3;
import Model.Ator;
import Model.ParNomeId;
import Model.ParIdId;
import Entidades.aed3.ListaInvertida.ListaInvertidaImplementada;
import Entidades.aed3.ListaInvertida.ElementoLista;

import java.util.ArrayList;

import Entidades.aed3.*;

public class ArqAtor extends Arquivo<Ator> {
    Arquivo<Ator> arquivo;
    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceAtor;
    ListaInvertidaImplementada lista;
    
    public ArqAtor() throws Exception {
        super("Atores", Ator.class.getConstructor());

        indiceNome = new ArvoreBMais<>(
            ParNomeId.class.getConstructor(), 
            5, 
            "./Dados/Atores/indiceNome.db");
            indiceAtor = new ArvoreBMais<>(
            ParIdId.class.getConstructor(), 
            5, 
            "./Dados/Atuacao/indiceAtorAtuacao.db");
            lista = new ListaInvertidaImplementada("Dados/ListaInvertida/stopwords.txt", 4,"Dados/ListaInvertida/Atores/dicionario.listainv.db", "Dados/ListaInvertida/Atores/blocos.listainv.db");
    }

    @Override
    public int create(Ator s) throws Exception {
        int id = super.create(s);
        indiceNome.create(new ParNomeId(s.getNome(), id));
        lista.inserir(s.getNome(), s.getId());
        return id;
    }

    public Ator[] readNome(String nome) throws Exception {
        if(nome.length()==0)
            return null;
        ArrayList<ElementoLista> ptis = lista.buscar(nome);
        if(ptis.size()>0) {
            Ator[] series = new Ator[ptis.size()];
            int i=0;
            for(ElementoLista pti: ptis) 
                series[i++] = read(pti.getId());
            return series;
        }
        else 
            return null;
    }
    
    public Ator readId(int pid) throws Exception{
      return read(pid);
    }

    @Override
    public boolean delete(int id) throws Exception {
        Ator a = read(id);   // na superclasse
        if(a!=null) {
          ArrayList<ParIdId> ptis = indiceAtor.read(new ParIdId(id, -1));
          if(ptis.size() == 0){
            if(super.delete(id))
                return lista.excluir(a.getNome(), a.getId());
          } else {
            System.out.println("Erro! Exclua as atuações antes de excluir o ator!");
          }
        }
        return false;
    }

    @Override
    public boolean update(Ator novoAtor) throws Exception {
        Ator s = read(novoAtor.getId());    // na superclasse
        if(s!=null) {
            if(super.update(novoAtor)) {
                if(!s.getNome().equals(novoAtor.getNome())) {
                    lista.atualizar(s.getNome(), novoAtor.getNome(), novoAtor.getId());
                }
                return true;
            }
        }
        return false;
    }

}
