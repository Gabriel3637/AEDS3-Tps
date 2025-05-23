package Model;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Ator implements Registro{
  private int id;
  private String nome;
  private short idade;
  private char sexo;
  
  //Construtores
  
  public Ator(int pid, String pnome, int pidade, char psexo){
    this.id = pid;
    this.nome = pnome;
    this.idade = (short)pidade;
    this.sexo = psexo;
  }
  
  public Ator(){
    this(-1, "", -1, 'M');
  }
  
  public Ator(String pnome, int pidade, char psexo){
    this(-1, pnome, pidade, psexo);
  }
  
  //Metodos GETS
  
  public int getId(){
    return this.id;
  }
  
  public String getNome(){
    return this.nome;
  }
  
  public short getIdade(){
    return this.idade;
  }

  public char getSexo(){
    return this.sexo;
  }
  
  //Metodos SETS
  
  public void setId(int pid){
    this.id = pid;
  }
  
  public void setNome(String pnome){
    this.nome = pnome;
  }
  
  public void setIdade(int pidade){
    this.idade = (short)pidade;
  }
  
  public void setSexo(char psexo){
    this.sexo = psexo;
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
    dos.writeShort(this.idade);
    dos.writeChar(this.sexo);
    
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
    this.idade = dis.readShort();
    this.sexo = dis.readChar();
  }
}
