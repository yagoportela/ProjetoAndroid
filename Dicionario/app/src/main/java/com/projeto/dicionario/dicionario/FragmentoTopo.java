package com.projeto.dicionario.dicionario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentoTopo extends Fragment {

    View view;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragmento_topo, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        final EditText textPalavra = (EditText) view.findViewById(R.id.palavra);

        //Evento do Edit Text
        textPalavra.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                    Lista(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        final HistoricoCRUD historico = new HistoricoCRUD(getContext());
        ArrayList<Palavra> palavra = historico.SelectPalavras();

        final ListView listaItens = (ListView) view.findViewById(R.id.listaItens);
        final ArrayAdapter<Palavra> lista = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, palavra);
        listaItens.setAdapter(lista);

        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Palavra palavra = historico.SelectPalavra(lista.getItem(position).getPalavra());

                Intent intent = new Intent(view.getContext(), LayoutPalavraSelecionada.class);
                intent.putExtra("palavra", palavra);
                startActivity(intent);
            }
        });
    }

    public void Lista(String valor) {

        if (valor != null && !valor.equalsIgnoreCase("")) {

            DicionarioCRUD dicionario = new DicionarioCRUD(getContext());
            ArrayList<Palavra> palavra = dicionario.SelectPalavras(valor);

            final ListView listaItens = (ListView) view.findViewById(R.id.listaItens);
            final ArrayAdapter<Palavra> lista = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, palavra);
            listaItens.setAdapter(lista);

            listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Palavra palavra = (Palavra) lista.getItem(position);

                    new HistoricoCRUD(getContext()).Inserir(palavra);

                    Intent intent = new Intent(view.getContext(), LayoutPalavraSelecionada.class);
                    intent.putExtra("palavra", palavra);
                    startActivity(intent);
                }
            });

        }else{

            final HistoricoCRUD historico = new HistoricoCRUD(getContext());
            ArrayList<Palavra> palavra = historico.SelectPalavras();

            final ListView listaItens = (ListView) view.findViewById(R.id.listaItens);
            final ArrayAdapter<Palavra> lista = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, palavra);
            listaItens.setAdapter(lista);

            listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Palavra palavra = historico.SelectPalavra(lista.getItem(position).getPalavra());

                    Intent intent = new Intent(view.getContext(), LayoutPalavraSelecionada.class);
                    intent.putExtra("palavra", palavra);
                    startActivity(intent);
                }
            });

        }
    }


}
