package com.projeto.dicionario.dicionario;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Nads on 21/05/2017.
 */

public class AtualizacaoWEB extends AsyncTask<String, Void, Boolean>{

    TextView text;
    Context context;
    AppCompatActivity layout;

    public AtualizacaoWEB(AppCompatActivity layout, Context context, TextView text){
        this.text = text;
        this.context = context;
        this.layout = layout;
    }

    @Override
    protected void onPreExecute(){

        text.setText("Instalando Atualizações do banco de dados......"+
                "\nPor favor espere mais alguns minutos...."+
                "\nIsto pode demorar um pouco");

    }


    @Override
    protected Boolean doInBackground(String... paramentro){

        try {

            //Conectando com o servidor
            Socket socket = new Socket("192.168.43.54", 1777);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            saida.writeUTF("atualizacao");
            saida.flush();

            if(entrada.readBoolean()){

                saida.writeDouble(new InformacaoCRUD(context).SelectInformacao().getVersao());
                saida.flush();

                ArrayList<Atualizacao> arrayAtualizacao = new ArrayList();

                int tamanhoVetorAtualizacao = entrada.readInt();

                for(int i = 0; i < tamanhoVetorAtualizacao; i++){
                    double versao = entrada.readDouble();
                    String objetivo = entrada.readUTF();

                    Atualizacao atualizacao = new Atualizacao(versao, objetivo);

                    int tamanhoVetorPalavra = entrada.readInt();

                    for(int j = 0; j < tamanhoVetorPalavra; j++){
                        String palavra = entrada.readUTF();
                        String grupoGramatical = entrada.readUTF();
                        String  texto = entrada.readUTF();

                        atualizacao.palavra.add(new Palavra(0,palavra, grupoGramatical, texto));
                    }

                    arrayAtualizacao.add(atualizacao);
                }

                if(entrada.readBoolean()){

                    for(int i = 0; i < arrayAtualizacao.size(); i++){

                        new InformacaoCRUD(context).Alterar(new Informacao(arrayAtualizacao.get(i).getVersao()));

                        //Se for para inserir novas palavras
                        if(arrayAtualizacao.get(i).getObjetivo().equalsIgnoreCase("Inserir")) {

                            for (int j = 0; j < arrayAtualizacao.get(i).palavra.size(); j++) {

                                new DicionarioCRUD(context).Inserir(new Palavra(0, arrayAtualizacao.get(i).palavra.get(j).getPalavra(),
                                        arrayAtualizacao.get(i).palavra.get(j).grupoGramatical,
                                        arrayAtualizacao.get(i).palavra.get(j).texto));
                            }
                        }

                        //Se for para alterar as palavras
                        if(arrayAtualizacao.get(i).getObjetivo().equalsIgnoreCase("Alterar")) {

                            for (int j = 0; j < arrayAtualizacao.get(i).palavra.size(); j++) {

                                new DicionarioCRUD(context).Alterar(new Palavra(0, arrayAtualizacao.get(i).palavra.get(j).getPalavra(),
                                        arrayAtualizacao.get(i).palavra.get(j).grupoGramatical,
                                        arrayAtualizacao.get(i).palavra.get(j).texto));
                            }
                        }

                        //Se for para excluir as palavras
                        if(arrayAtualizacao.get(i).getObjetivo().equalsIgnoreCase("Excluir")) {

                            for (int j = 0; j < arrayAtualizacao.get(i).palavra.size(); j++) {

                                new DicionarioCRUD(context).Excluir(new Palavra(0, arrayAtualizacao.get(i).palavra.get(j).getPalavra(),
                                        arrayAtualizacao.get(i).palavra.get(j).grupoGramatical,
                                        arrayAtualizacao.get(i).palavra.get(j).texto));
                            }
                        }
                    }

                    saida.close();
                    entrada.close();
                    socket.close();
                    return true;
                }
            }

            saida.close();
            entrada.close();
            socket.close();

            return false;

        }catch(Exception e){
            return false;
        }

    }

    @Override
    public void onPostExecute(Boolean valor){

        if(valor == true){
            text.setText("Banco Atualizado");
        }else{
            text.setText("Não foi possivel se conectar Com o servidor....");
        }

        Intent intent = new Intent(context, LayoutPrincipal.class);
        context.startActivity(intent);
        layout.finish();

    }

    public boolean VerificarConexao(){

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if(conexao.getActiveNetworkInfo() != null && conexao.getActiveNetworkInfo().isAvailable()
                && conexao.getActiveNetworkInfo().isConnected()){

            return true;

        }

        return false;
    }

}
