import java.io.*;
import aed3.HashExtensivel;

public class ArquivoSerie {

    private RandomAccessFile arquivo;
    private HashExtensivel<ParNomeID> indiceNome;
    private final String caminhoArquivo = ".\\dados\\registros\\dados.db";
    private final String caminhoIndice = ".\\dados\\registros\\indiceNome.d.db";
    private final String caminhoCestos = ".\\dados\\registros\\indiceNome.c.db";

    public ArquivoSerie() throws Exception {
        File dir = new File(".\\dados\\registros");
        if (!dir.exists()) dir.mkdirs();

        arquivo = new RandomAccessFile(caminhoArquivo, "rw");
        indiceNome = new HashExtensivel<>(ParNomeID.class.getConstructor(),4,caminhoIndice,caminhoCestos);
    }
    
    public interface RegistroComNome extends Registro {
    public String getNome();
    public int getId();
    public void setId(int id);
}

    public int create(RegistroComNome obj) throws Exception {
        arquivo.seek(arquivo.length());
        long pos = arquivo.getFilePointer();

        byte[] b = obj.toByteArray();
        arquivo.writeInt(b.length);
        arquivo.write(b);

        int id = (int)(pos); // usando posição como ID
        obj.setId(id);
        indiceNome.create(new ParNomeID(obj.getNome(), id));
        return id;
    }

    public RegistroComNome read(String nome) throws Exception {
        ParNomeID pni = indiceNome.read(ParNomeID.hash(nome));
        if (pni == null) return null;
        return read(pni.getId());
    }

    public RegistroComNome read(int id) throws Exception {
        arquivo.seek(id);
        int tamanho = arquivo.readInt();
        byte[] b = new byte[tamanho];
        arquivo.readFully(b);

        RegistroComNome obj = new Cliente(); // ou outra classe que implemente RegistroComNome
        obj.fromByteArray(b);
        obj.setId(id);
        return obj;
    }

    public boolean delete(String nome) throws Exception {
        ParNomeID pni = indiceNome.read(ParNomeID.hash(nome));
        if (pni != null) {
            arquivo.seek(pni.getId());
            arquivo.writeInt(-1); // marca o registro como deletado
            return indiceNome.delete(ParNomeID.hash(nome));
        }
        return false;
    }

    public boolean update(RegistroComNome novo) throws Exception {
        RegistroComNome antigo = read(novo.getNome());
        if (antigo == null) return false;

        delete(antigo.getNome());
        int novoId = create(novo);
        novo.setId(novoId);
        return true;
    }

    public void close() throws IOException {
        arquivo.close();
    }
}


