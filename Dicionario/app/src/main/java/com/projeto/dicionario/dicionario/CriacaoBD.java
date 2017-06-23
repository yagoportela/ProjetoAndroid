package com.projeto.dicionario.dicionario;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nads on 10/05/2017.
 */

public class CriacaoBD extends SQLiteOpenHelper{

    Context context;

    public static class Informacoes {

        //Nome banco de dados
        public static final String BD = "Dicionario";

        //Dicionario
        public static final String idPalavra = "idPalavra";
        public static final String tabelaPalavra = "palavras";
        public static final String palavra = "palavra";
        public static final String grupoGramatical = "grupoGramatical";
        public static final String texto = "texto";
        public static final String[] colunasDicionario = {idPalavra, palavra, grupoGramatical, texto};

        //Informacoes
        public static final String tabelaInformacoes = "informacoes";
        public static final String idInformacao= "idInformacao";
        public static final String versao = "versao";
        public static final String[] colunasInformacao = {versao};

        //Historico
        public static final String idHistorico = "idHistorico";
        public static final String tabelaHistorico = "historico";
        public static final String fk_idPalavra = "fk_idPalavra";
        public static final String[] colunasHistorico = {fk_idPalavra};

    }

    public CriacaoBD(Context context, int versao) {

        super(context, Informacoes.BD, null, versao);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Criando tabelaPalavra no banco de dados
        String query = "create table "+ Informacoes.tabelaPalavra+"(" +
                Informacoes.idPalavra+" integer primary key autoincrement,"+
                Informacoes.palavra +" text not null,"+
                Informacoes.grupoGramatical +" text,"+
                Informacoes.texto +" text not null);";

        db.execSQL(query);

        //Criando tabelaHistorico no banco de dados
        query = "create table "+ Informacoes.tabelaHistorico+"("+
                Informacoes.idHistorico+" integer primary key autoincrement,"+
                Informacoes.fk_idPalavra +" int not null," +
                "FOREIGN KEY("+Informacoes.fk_idPalavra+") REFERENCES "+Informacoes.tabelaPalavra+"("+Informacoes.idPalavra+"));";

        db.execSQL(query);

        //Criando tabelaInformacoes no banco de dados
        query = "create table "+ Informacoes.tabelaInformacoes +"("+
                Informacoes.idInformacao+" integer primary key autoincrement,"+
                Informacoes.versao +" float not null);";

        db.execSQL(query);

        //Populando banco de dados
        InserindoBD(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InserindoBD(SQLiteDatabase db){

        //Pegando os arquivos para salvar no banco
        AssetManager asset = context.getResources().getAssets();
        String[] listaArquivos = null;

        //Verificando arquivos salvos na pasta assets
        try {
            listaArquivos = asset.list("BancoXML");
        }catch (IOException e){
            Toast.makeText(context, "Erro ao verificar arquivos", Toast.LENGTH_LONG);
        }

        //Verificando quatidade de arquivos para popular no banco
        for(int i = 0; i < listaArquivos.length;i++) {

            ArrayList<Palavra> arrayPalavras = new LeituraXML().AbrirXML(context, "BancoXML/"+listaArquivos[i]);

            //Inserindo Palavras na tabelaPalavra
            for (int j = 0; j < arrayPalavras.size(); j++) {

                ContentValues valores = new ContentValues();

                valores.put(Informacoes.palavra, arrayPalavras.get(j).getPalavra());
                valores.put(Informacoes.grupoGramatical, arrayPalavras.get(j).getGrupoGramatical());
                valores.put(Informacoes.texto, arrayPalavras.get(j).getTexto());

                db.insert(Informacoes.tabelaPalavra, null, valores);
            }
        }

        ContentValues valores = new ContentValues();

        valores.put("versao", "1.0");

        db.insert(Informacoes.tabelaInformacoes, null, valores);

    }
}
