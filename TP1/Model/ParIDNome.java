package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import Entidades.aed3.RegistroHashExtensivel;

public class ParIDNome implements RegistroHashExtensivel<ParIDNome> {

    private int id;
    private String nome;
    private final short TAMANHO = 44; 

    public ParIDNome() {
        this.id = -1;
        this.nome = "";
    }

    public ParIDNome(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.id + ";\"" + this.nome + "\")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);

        byte[] nomeBytes = new byte[40];
        byte[] nomeOriginal = this.nome.getBytes(StandardCharsets.UTF_8);
        int length = Math.min(nomeOriginal.length, 40);
        System.arraycopy(nomeOriginal, 0, nomeBytes, 0, length);
        dos.write(nomeBytes);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();

        byte[] nomeBytes = new byte[40];
        dis.readFully(nomeBytes);
        this.nome = new String(nomeBytes, StandardCharsets.UTF_8).trim();
    }

}
