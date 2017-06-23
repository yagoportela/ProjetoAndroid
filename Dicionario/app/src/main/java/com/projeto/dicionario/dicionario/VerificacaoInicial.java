package com.projeto.dicionario.dicionario;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class VerificacaoInicial extends AsyncTask <String, Void, Boolean> {

    TextView text;
    Context context;
    AppCompatActivity layout;

    public VerificacaoInicial(AppCompatActivity layout, Context context, TextView text){
        this.text = text;
        this.context = context;
        this.layout = layout;
    }

    @Override
    protected void onPreExecute(){

        text.setText("Instalando Informações do banco de dados......"+
                "\nIsto pode demorar um pouco");

    }


    @Override
    protected Boolean doInBackground(String... params) {

                if(new DicionarioCRUD(context).ExistenciaBD() == true){

                    return true;

                }else{

                    return false;
                }
    }

    @Override
    public void onPostExecute(Boolean valor){

        if(valor == true){

            text.setText("Dicionario Instalado");

            AtualizacaoWEB atualizacaoWEB = new AtualizacaoWEB(layout, context, text);
            atualizacaoWEB.execute("");

        }else{
            text.setText("Erro 404.....Por favor contate o suporte");
        }

    }

}
