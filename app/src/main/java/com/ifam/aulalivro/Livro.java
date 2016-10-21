package com.ifam.aulalivro;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fernando on 06/10/16.
 */

public class Livro implements Serializable {


    private int numComentario;


    private String categoria;
    private String capa;
    private String titulo;
    private ArrayList<String> usuario;
    private ArrayList<String> mensagem;
    private String autor;
    private int paginas;
    private int ano;

    public Livro() {

    }


    public int getNumComentario(){
        this.numComentario = mensagem.size();
        return numComentario;
    }




    public Livro(String cat, String capa, String titulo, String autor, int paginas, int ano, ArrayList<String> user, ArrayList<String> msg) {
        this.categoria = cat;
        this.capa = capa;
        this.usuario = user;
        this.mensagem = msg;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ano = ano;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUsuario(int index) {
        return usuario.get(index);
    }

    public void setUsuario(ArrayList<String> usuario) {
        this.usuario = usuario;
    }

    public String getMensagem(int index) {
        return mensagem.get(index);
    }

    public void setMensagem(ArrayList<String> mensagem) {
        this.mensagem = mensagem;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
