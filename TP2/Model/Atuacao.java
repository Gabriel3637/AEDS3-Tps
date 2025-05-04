package Model;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Atuacao implements Registro{
  private int id;
  private int serieId;
  private int atorId;
  private String papel;
  
  //Construtor
  
  public Atuacao(int pid, int pserieId, int patorId, String ppapel){
    this.id = pid;
    this.serieId = pserieId;
    this.atorId = patorId;
    this.papel = ppapel;
  }
  
  public Atuacao(){
    this(-1, -1, -1, "");
  }
  
  public Atuacao(int pserieId, int patorId, String ppapel){
    this(-1, pserieId, patorId, ppapel);
  }
  
  public String getNome(){
    return "-1";
  }
  //Métodos GETS
  
  public int getId(){
    return this.id;
  }
  
  public int getSerieId(){
    return this.serieId;
  }
  
  public int getAtorId(){
    return this.atorId;
  }
  
  public String getPapel(){
    return this.papel;
  }
  
  //Métodos SETS
  
  public void setId(int pid){
    this.id = pid;
  }
  
  public void setSerieId(int pserieId){
    this.serieId = pserieId;
  }
  
  public void setAtorId(int patorId){
    this.atorId = patorId;
  }
  
  public void setPapel(String ppapel){
    this.papel = ppapel;
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
    dos.writeInt(this.serieId);
    dos.writeInt(this.atorId);
    dos.writeUTF(this.papel);
    
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
    this.serieId = dis.readInt();
    this.atorId = dis.readInt();
    this.papel = dis.readUTF();
  }
}

