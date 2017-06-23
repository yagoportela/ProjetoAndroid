/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.Serializable;

/**
 * Created by Nads on 26/05/2017.
 */


public class Palavra implements Serializable {
    
    
    private String palavra;
    public String grupoGramatical;
    public String  texto;

    public Palavra(String palavra, String grupoGramatical, String texto){
        super();
        this.palavra = palavra;
        this.grupoGramatical = grupoGramatical;
        this.texto = texto;
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