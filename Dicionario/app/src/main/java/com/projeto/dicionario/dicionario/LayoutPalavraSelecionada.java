package com.projeto.dicionario.dicionario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutPalavraSelecionada extends AppCompatActivity {

    Palavra palavra;
    Informacao informacao;
    TextView textPalavra;
    TextView textGrupoGramatical;
    TextView textDefinicao;
    TextView textVersao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_palavra_selecionada);

        textPalavra = (TextView) findViewById(R.id.textPalavra);
        textGrupoGramatical = (TextView) findViewById(R.id.textGrupoGramatical);
        textDefinicao = (TextView) findViewById(R.id.textDefinicao);
        textVersao = (TextView) findViewById(R.id.textVersao);

        palavra = (Palavra) getIntent().getSerializableExtra("palavra");
        informacao = new InformacaoCRUD(getBaseContext()).SelectInformacao();

        textVersao.setText("Versao: "+informacao.getVersao());
        textPalavra.setText(palavra.getPalavra());
        textGrupoGramatical.setText(palavra.getGrupoGramatical());
        textDefinicao.setText(palavra.getTexto());

    }
}
