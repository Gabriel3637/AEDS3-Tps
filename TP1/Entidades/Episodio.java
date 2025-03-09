import java.time.LocalDate
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.DataOutputStream

public class Episodio{
  private int id;
  private String nome;
  private short temporada;
  private LocalDate lancamento;
  private short duracao;
  
  //Construtores
  
  public Episodio(int pid, String pnome, short ptemporada, LocalDate plancamento, short pduracao){
    this.id = pid;
    this.nome = pnome;
    this.temporada = ptemporada;
    this.lancamento = plancamento;
    this.duracao = pduracao;
  }
  
  public Episodio(){
    this("", -1, LocalDate.now(), -1);
  }
  
  public Episodio(String pnome, short ptemporada, LocalDate plancamento, short pduracao){
    this(-1, pnome, ptemporada, plancamento, pduracao);
  }
  
  //Métodos Gets
  
  public int getId(){
    return id;
  }
  public String getNome(){
    return nome;
  }
  public short getTemporada(){
    return temporada;
  }
  public LocalDate getLancamento(){
    return lancamento;
  }
  public short getDuracao(){
    return duracao;
  }
  
  //Métodos Sets
  
  public void setId(int pid){
    id = pid;
  }
  public void setNome(String pnome){
    nome = pnome;
  }
  public void setTemporada(short ptemporada){
    temporada = ptemporada;
  }
  public void setLancamento(LocalDate plancamento){
    lancamento = plancamento;
  }
  public void setDuracao(short pduracao){
    duracao = pduracao;
  }
  
  /*
  Identidade: toByteArray()
  Objetivo: Transformar todos os dados da instancia do objeto em um vetor de bytes. 
  Parametros: Não há parâmentros
  */
  public byte[] toByteArray(){
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    
    dos.writeInt(this.id);
    dos.writeUTF(this.nome);
    dos.writeShort(this.temporada);
    dos.writeInt((int)lancamento.toEpochDay());
    dos.writeShort(this.duracao);
    
    return baos.toByteArray();
  }
  
  /*
  Identidade: fromByteArray()
  Objetivo: Transformar um vetor de bytes em um objeto da classe
  Parametros:
  @vetor = Vetor de bytes para ser transformado
  */
  public void fromByteArray(byte[] vetor){
    ByteArrayInputStream bais = new ByteArrayInputStream(vetor);
    DataInputStream dis = new DataInputStream(bais);
    
    this.id = dos.readInt();
    this.nome = dos.readUTF();
    this.temporada = dos.readShort();
    this.lancamento = LocalDate.ofEpochDay(dos.readInt());
    this.duracao = dos.readShort();
  }
  
  
}
