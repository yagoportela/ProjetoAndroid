package com.projeto.dicionario.dicionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DicionarioCRUD {

    private CriacaoBD banco;
    private SQLiteDatabase dataBase;

    public DicionarioCRUD(Context context){
        banco = new CriacaoBD(context, 1);
    }

    public SQLiteDatabase getDataBase(){

        if(dataBase==null){
            dataBase = banco.getWritableDatabase();
        }

        return dataBase;
    }

    public ArrayList<Palavra> SelectPalavras(String pesquisa){

        ArrayList<Palavra> todasPalavras = new ArrayList<>();
        String[] where = {pesquisa+"%"};

        Cursor cursor = getDataBase().query(CriacaoBD.Informacoes.tabelaPalavra,
                CriacaoBD.Informacoes.colunasDicionario, CriacaoBD.Informacoes.palavra+" like ? ", where,
                null, null, CriacaoBD.Informacoes.palavra);

        while(cursor.moveToNext()){
            Palavra palavra = new Palavra(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            todasPalavras.add(palavra);
        }

        cursor.close();
        return todasPalavras;

    }

    public Palavra SelectPalavra(String pesquisa){

        Cursor cursor =getDataBase().query(CriacaoBD.Informacoes.tabelaPalavra,
                                            CriacaoBD.Informacoes.colunasDicionario, pesquisa, null, null, null, null);
        Palavra palavra = null;
        while(cursor.moveToNext()){
            palavra = new Palavra(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        cursor.close();

        return palavra;
    }

    public boolean Inserir(Palavra palavra){
        ContentValues valores = new ContentValues();

        valores.put("palavra", palavra.getPalavra());
        valores.put("grupoGramatical", palavra.getGrupoGramatical());
        valores.put("texto", palavra.getTexto());

        return getDataBase().insert("palavras", null, valores) > 0;
    }

    public boolean Alterar(Palavra palavra){

        ContentValues valores = new ContentValues();

        valores.put("palavra", palavra.getPalavra());
        valores.put("grupoGramatical", palavra.getGrupoGramatical());
        valores.put("texto", palavra.getTexto());

        String whereCause = CriacaoBD.Informacoes.palavra + " = ?";
        String whereArgs[] = {palavra.getPalavra()};

        return getDataBase().update(CriacaoBD.Informacoes.tabelaPalavra,valores, whereCause, whereArgs) > 0;

    }

    public boolean Excluir(Palavra palavra){

        ContentValues valores = new ContentValues();

        String whereCause = CriacaoBD.Informacoes.palavra + " = ?";
        String whereArgs[] = {palavra.getPalavra()};

        return getDataBase().delete(CriacaoBD.Informacoes.tabelaPalavra, whereCause, whereArgs) > 0;
    }

    public boolean ExistenciaBD(){

        try {
            Cursor cursor = getDataBase().query(CriacaoBD.Informacoes.tabelaPalavra, null, null, null, null, null, null);
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
