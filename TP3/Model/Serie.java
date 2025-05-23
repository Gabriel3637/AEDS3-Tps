package Model;

import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Model.Registro;

public class Serie implements Registro{
  private int id;
  private String nome;
  private LocalDate lancamento;
  private String sinopse;
  private String streaming;
  
  //Contrutores
  
  public Serie(int pid, String pnome, LocalDate plancamento, String psinopse, String pstreaming){
    this.id = pid;
    this.nome = pnome;
    this.lancamento = plancamento;
    this.sinopse = psinopse;
    this.streaming = pstreaming;
  }
  
  public Serie(){
    this(-1, "", LocalDate.now(), "", "");
  }
  
  public Serie(String pnome, LocalDate plancamento, String psinopse, String pstreaming){
    this(-1, pnome, plancamento, psinopse, pstreaming);
  }
  
  //Métodos Get
  
  public int getId(){
    return this.id;
  }
  
  public String getNome(){
    return this.nome;
  }
  
  public LocalDate getLancamento(){
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
  
  public void setLancamento(LocalDate plancamento){
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
  
  public byte[] toByteArray() throws IOException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    
    dos.writeInt(this.id);
    dos.writeUTF(this.nome);
    dos.writeInt((int)this.lancamento.toEpochDay());
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
  
  public void fromByteArray(byte[] vetor) throws IOException{
    ByteArrayInputStream bais = new ByteArrayInputStream(vetor);
    DataInputStream dis = new DataInputStream(bais);
    
    this.id = dis.readInt();
    this.nome = dis.readUTF();
    this.lancamento = LocalDate.ofEpochDay(dis.readInt());
    this.sinopse = dis.readUTF();
    this.streaming = dis.readUTF();
    
  }
  
}
