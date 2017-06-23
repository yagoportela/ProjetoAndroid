package servidor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nads on 26/05/2017.
 */

public class Atualizacao implements Serializable {

    private double versao;
    private String objetivo;
    public ArrayList<Palavra> palavra;

    public Atualizacao(double versao, String objetivo){

        this.versao = versao;
        this.objetivo = objetivo;
        palavra = new ArrayList();

    }

    public double getVersao() {
        return versao;
    }

    public void setVersao(double versao) {
        this.versao = versao;
    }
    
    public String getObjetivo(){
        return this.objetivo;
    }
    
    public void setObjetivo(String objetivo){
       this.objetivo = objetivo; 
    }

}
