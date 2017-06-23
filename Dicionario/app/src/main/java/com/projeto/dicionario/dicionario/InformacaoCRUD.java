package com.projeto.dicionario.dicionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Nads on 28/05/2017.
 */

public class InformacaoCRUD {

    private CriacaoBD banco;
    private SQLiteDatabase dataBase;

    public InformacaoCRUD(Context context){
        banco = new CriacaoBD(context, 1);
    }

    public SQLiteDatabase getDataBase(){

        if(dataBase==null){
            dataBase = banco.getWritableDatabase();
        }

        return dataBase;
    }

    public Informacao SelectInformacao(){

        Cursor cursor =getDataBase().query(CriacaoBD.Informacoes.tabelaInformacoes,
                CriacaoBD.Informacoes.colunasInformacao, null, null, null, null, null);

        Informacao informacao = null;

        while(cursor.moveToNext()){
            informacao = new Informacao(cursor.getDouble(0));
        }

        cursor.close();

        return informacao;
    }

    public long Inserir(Informacao informacao){

        ContentValues valores = new ContentValues();

        valores.put(CriacaoBD.Informacoes.versao, informacao.getVersao());

        return getDataBase().insert(CriacaoBD.Informacoes.tabelaInformacoes, null, valores);
    }

    public boolean Alterar(Informacao informacao){

        ContentValues valores = new ContentValues();

        valores.put(CriacaoBD.Informacoes.versao, informacao.getVersao());

        String whereCause = CriacaoBD.Informacoes.versao+" = ?";
        String whereArgs[] = {String.valueOf(SelectInformacao().getVersao())};

        return getDataBase().update(CriacaoBD.Informacoes.tabelaInformacoes,valores, whereCause, whereArgs) > 0;

    }

    public boolean Excluir(){

        String whereCause = "idInformacao = ?";
        String whereArgs[] = {"1"};

        return getDataBase().delete(CriacaoBD.Informacoes.tabelaInformacoes, whereCause, whereArgs) > 0;
    }

    public boolean ExistenciaBD(){

        try {
            Cursor cursor = getDataBase().query(CriacaoBD.Informacoes.tabelaInformacoes, null, null, null, null, null, null);
            return true;
        }catch(SQLException e){
            return false;
        }
    }


    public void Finalizacao(){
        banco.close();
        dataBase.close();
    }
}
