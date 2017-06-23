package com.projeto.dicionario.dicionario;

import java.io.Serializable;

/**
 * Created by Nads on 29/05/2017.
 */

public class Palavra implements Serializable {

    private int idPalavra;
    private String palavra;
    public String grupoGramatical;
    public String  texto;

    public Palavra(int idPalavra, String palavra, String grupoGramatical, String texto){
        super();

        this.idPalavra = idPalavra;
        this.palavra = palavra;
        this.grupoGramatical = grupoGramatical;
        this.texto = texto;
    }

    public int getIdPalavra(){
        return idPalavra;
    }

    public void setIdPalavra(int idPalavra){
        this.idPalavra = idPalavra;
    }

    public String getPalavra(){
        return palavra;
    }

    public void setPalavra(String palavra){
        this.palavra = palavra;
    }

    public String getGrupoGramatical(){
        return grupoGramatical;
    }

    public void setGrupoGramatical(String grupoGramatical){ this.grupoGramatical = grupoGramatical; }

    public String getTexto(){
        return texto;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    @Override
    public String toString(){
        return palavra;
    }
}

