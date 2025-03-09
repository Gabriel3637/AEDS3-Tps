package TP1;


import java.time.LocalDate;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import TP1.EntidadeArquivo;

public class Episodio{
  private int serieId;
  private int id;
  private String nome;
  private short temporada;
  private LocalDate lancamento;
  private short duracao;
  
  //Construtores
  
  public Episodio(int pid, String pnome, int ptemporada, LocalDate plancamento, int pduracao){
    this.id = pid;
    this.nome = pnome;
    this.temporada = (short)ptemporada;
    this.lancamento = plancamento;
    this.duracao = (short)pduracao;
  }
  
  public Episodio(){
    this(-1, "", -1, LocalDate.now(), -1);
  }
  
  public Episodio(String pnome, short ptemporada, LocalDate plancamento, short pduracao){
    this(-1, pnome, ptemporada, plancamento, pduracao);
  }
  
  //Métodos Gets
  
  public int getId(){
    return this.id;
  }
  public String getNome(){
    return this.nome;
  }
  public short getTemporada(){
    return this.temporada;
  }
  public LocalDate getLancamento(){
    return this.lancamento;
  }
  public short getDuracao(){
    return this.duracao;
  }
  public int getSerieId(){
    return this.serieId;
  }
  
  //Métodos Sets
  
  public void setId(int pid){
    this.id = pid;
  }
  public void setNome(String pnome){
    this.nome = pnome;
  }
  public void setTemporada(short ptemporada){
    this.temporada = ptemporada;
  }
  public void setLancamento(LocalDate plancamento){
    this.lancamento = plancamento;
  }
  public void setDuracao(short pduracao){
    this.duracao = pduracao;
  }
  public void setSerieId(int pserieId){
    this.serieId = pserieId;
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
    
    this.id = dis.readInt();
    this.nome = dis.readUTF();
    this.temporada = dis.readShort();
    this.lancamento = LocalDate.ofEpochDay(dis.readInt());
    this.duracao = dis.readShort();
  }
  
  
}
