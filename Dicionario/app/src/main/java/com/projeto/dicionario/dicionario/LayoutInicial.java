package com.projeto.dicionario.dicionario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class LayoutInicial extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inicial);

        TextView text = (TextView) findViewById(R.id.text);
        new VerificacaoInicial(this, this, text).execute("");



    }



}
