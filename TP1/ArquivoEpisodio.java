import Entidades.aed3.*;
import TP1.Model.Episodio;
import aed3.Arquivo;
import aed3.HashExtensivel;

public class ArquivoEpisodio extends Entidades.aed3.Arquivo<Episodio> {

    Arquivo<Episodio> arqEpisodio;
    HashExtensivel<ParSerieIdEpId> indiceIndiretoNome;

    public ArquivoEpisodio() throws Exception {
        super("episodio", Episodio.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParSerieIdEpId.class.getConstructor(), 
            4, 
            ".\\dados\\episodio\\indiceNome.d.db",   // diretório
            ".\\dados\\episodio\\indiceNome.c.db"    // cestos 
        );
    }

    @Override
    public int create(Episodio c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCPF.create(new ParCPFID(c.getCpf(), id));//aqui é encher tudo, isso coloca o id nos indices, quando tiver mais indices vamo lá
        return id;
    }

    public Cliente read(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoCPF.delete(ParCPFID.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Cliente c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoCPF.delete(ParCPFID.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Cliente novoCliente) throws Exception {
        Cliente clienteVelho = read(novoCliente.getCpf());
        if(super.update(novoCliente)) {
            if(novoCliente.getCpf().compareTo(clienteVelho.getCpf())!=0) {
                indiceIndiretoCPF.delete(ParCPFID.hash(clienteVelho.getCpf()));
                indiceIndiretoCPF.create(new ParCPFID(novoCliente.getCpf(), novoCliente.getId()));
            }
            return true;
        }
        return false;
    }
}
