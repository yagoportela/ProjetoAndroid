package com.projeto.dicionario.dicionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Nads on 22/05/2017.
 */

public class HistoricoCRUD {

    private CriacaoBD banco;
    private SQLiteDatabase dataBase;

    public HistoricoCRUD(Context context){
        banco = new CriacaoBD(context, 1);
    }

    public SQLiteDatabase getDataBase(){

        if(dataBase==null){
            dataBase = banco.getWritableDatabase();
        }

        return dataBase;
    }

    public ArrayList<Palavra> SelectPalavras(){

        ArrayList<Palavra> aux = new ArrayList<>();

        //Utlizando INNER JOIN
        String query = "SELECT idPalavra, palavra, grupoGramatical, texto from "+ CriacaoBD.Informacoes.tabelaPalavra+
                " INNER JOIN "+ CriacaoBD.Informacoes.tabelaHistorico +" on "+
                CriacaoBD.Informacoes.idPalavra +" = "+CriacaoBD.Informacoes.fk_idPalavra;;

        Cursor cursor =getDataBase().rawQuery(query, null);

        while(cursor.moveToNext()){
            Palavra palavra = new Palavra(cursor.getInt(0),cursor.getString(1), "", "");
            aux.add(palavra);
        }

        cursor.close();

        //Invertendo oredem das palavras
        ArrayList<Palavra> todasPalavras = new ArrayList<>();

        for(int i = aux.size() - 1; i > 0;i--){

            todasPalavras.add(aux.get(i));

        }

        return todasPalavras;

    }

    public Palavra SelectPalavra(String pesquisa){

        String[] newPesquisa = {pesquisa};

        Cursor cursor =getDataBase().query(CriacaoBD.Informacoes.tabelaPalavra,
                CriacaoBD.Informacoes.colunasDicionario, CriacaoBD.Informacoes.palavra+" = ?", newPesquisa, null, null, null);
        Palavra palavra = null;

        while(cursor.moveToNext()){
            palavra = new Palavra(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));;
        }

        cursor.close();

        return palavra;
    }

    public long Inserir(Palavra palavra){

        String query = "SELECT COUNT("+CriacaoBD.Informacoes.idHistorico+") from "+CriacaoBD.Informacoes.tabelaHistorico;

        Cursor cursor = getDataBase().rawQuery(query, null);

        int tamanho = 0;

        while(cursor.moveToNext()){
            tamanho = cursor.getInt(0);
        }

        if(tamanho > 10){
            ArrayList<Palavra> arrayPalavras = SelectPalavras();
            Excluir(arrayPalavras.get(arrayPalavras.size()-1));
        }

        ContentValues valores = new ContentValues();

        valores.put(CriacaoBD.Informacoes.fk_idPalavra, palavra.getIdPalavra());


        return getDataBase().insert(CriacaoBD.Informacoes.tabelaHistorico, null, valores);
    }

    public boolean Alterar(Palavra palavra){

        ContentValues valores = new ContentValues();

        valores.put(CriacaoBD.Informacoes.palavra, palavra.getPalavra());
        valores.put(CriacaoBD.Informacoes.grupoGramatical, palavra.getGrupoGramatical());
        valores.put(CriacaoBD.Informacoes.texto, palavra.getTexto());

        String whereCause = CriacaoBD.Informacoes.fk_idPalavra + " = ?";
        String whereArgs[] = {String.valueOf(palavra.getIdPalavra())};

        return getDataBase().update(CriacaoBD.Informacoes.tabelaHistorico,valores, whereCause, whereArgs) > 0;

    }

    public boolean Excluir(Palavra palavra){

        ContentValues valores = new ContentValues();

        String whereCause = CriacaoBD.Informacoes.fk_idPalavra + " = ?";
        String whereArgs[] = {String.valueOf(palavra.getIdPalavra())};

        return getDataBase().delete(CriacaoBD.Informacoes.tabelaHistorico, whereCause, whereArgs) > 0;
    }

    public boolean ExistenciaBD(){

        try {
            Cursor cursor = getDataBase().query(CriacaoBD.Informacoes.tabelaHistorico, null, null, null, null, null, null);
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
