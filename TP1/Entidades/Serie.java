public class Serie{
  private int id;
  private String nome;
  private short lancamento;
  private String sinopse;
  private String streaming;
  
  //Contrutores
  
  public Serie(int pid, pString nome, short plancamento, String psinopse, String pstreaming){
    this.id = pid;
    this.nome = pnome;
    this.lancamento = plancamento;
    this.sinopse = psinopse;
    this.streaming = pstreaming;
  }
  
  public Serie(){
    this(-1, "", -1, "", "");
  }
  
  public Serie(String pnome, short plancamento, String psinopse, String pstreaming){
    this(-1, pnome, plancamento, psinopse, pstreaming);
  }
  
  //Métodos Get
  
  public int getId(){
    return this.id;
  }
  
  public String getNome(){
    return this.nome;
  }
  
  public short getLancamento(){
    return this.lancamento;
  }
  
  public String getSinopse(){
    return this.sinopse;
  }
  
  public String getStreaming(){
    return this.streaming;
  }
  
  //Métodos Sets
  
  public void setId(int pid){
    this.id = pid;
  }
  
  public void setNome(String pnome){
    this.nome = pnome;
  }
  
  public void setLancamento(short plancamento){
    this.lancamento = plancamento;
  }
  
  public void setSinopse(String psinopse){
    this.sinopse = psinopse;
  }
  
  public void setStreaming(String pstreaming){
    this.streaming = pstreaming;
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
    dos.writeShort(this.lancamento);
    dos.writeUTF(this.sinopse);
    dos.writeUTF(this.streaming);
    
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
    this.lancamento = dos.readShort();
    this.sinopse = dos.readUTF();
    this.streaming = dos.readUTF();
    
  }
  
}
