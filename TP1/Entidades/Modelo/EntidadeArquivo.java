package TP1;
public interface EntidadeArquivo {
    public void setId(int id);
    public int getId();
    public byte[] toByteArray() throws Exception;
    public void fromByteArray(byte[] vetor) throws Exception;
}
