package com.projeto.dicionario.dicionario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.security.PrivilegedAction;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Nads on 18/05/2017.
 */

public class LeituraXML {

    public  ArrayList<Palavra> AbrirXML(Context context, String path){

        ArrayList<Palavra> palavras = new ArrayList();

            try {

                //Loalização do arqivo XML
                DocumentBuilderFactory documento = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentoBuilder = documento.newDocumentBuilder();
                AssetManager asset = context.getResources().getAssets();
                Document documentoXML = documentoBuilder.parse(asset.open(path));

                //Pegando Dic do arquivo XML
                Node noDicionario = documentoXML.getElementsByTagName("dic").item(0);

                //Verificando existencia de elementos
                if (((Element) noDicionario) != null && noDicionario.getNodeType() == Node.ELEMENT_NODE) {

                    //Pegando entry arquivo XML
                    Element dicionario = (Element) noDicionario;
                    NodeList listaDicionario = dicionario.getElementsByTagName("entry");

                    //entry do arquivo xml possui ID, passando por todos elemento
                    for(int i =0; i <  listaDicionario.getLength() ;i++) {

                        String newPalavra = "";
                        String newGrupoGramatical = "";
                        String newDefinicao = "";

                        //Pegando os valores que se encontra dentro do entry
                        //Pegando Form do arquivo XML
                        Node noForm = listaDicionario.item(i);

                        //Pegando Sense do arquivo XML
                        Node noSense = listaDicionario.item(i);


                        //Verifificando existencia de elementos dentro do form
                        if (((Element) noForm) != null && noForm.getNodeType() == Node.ELEMENT_NODE ){

                            //Pegando orth do arquivo XML
                            Element form = (Element) noForm;
                            Node noPalavra = form.getElementsByTagName("orth").item(0);

                            //Verifincando existencia de elementos dentro do orth
                            if(((Element) noPalavra) != null && noPalavra.getNodeType() == Node.ELEMENT_NODE) {

                                //Pegando as palavras
                             Element palavra = (Element) noPalavra;
                                        newPalavra = palavra.getTextContent();
                            }
                        }

                        //Verifificando existencia de elementos dentro do sense
                        if (((Element) noSense) != null && noSense.getNodeType() == Node.ELEMENT_NODE ){

                            //Pegando def do arquivo XML
                            Element sense = (Element) noSense;

                            //Definicao
                            Node noDefinicao = sense.getElementsByTagName("def").item(0);

                            //Verifincando existencia de elementos dentro do def
                            if(((Element) noDefinicao) != null && noDefinicao.getNodeType() == Node.ELEMENT_NODE) {

                                // /Pegando as definicao
                                Element definicao = (Element) noDefinicao;
                                newDefinicao = definicao.getTextContent();
                            }

                            //Gramatica
                            Node noGramatica = sense.getElementsByTagName("gramGrp").item(0);

                            //Verifincando existencia de elementos dentro do gramGrp
                            if(((Element) noGramatica) != null && noGramatica.getNodeType() == Node.ELEMENT_NODE) {

                                //Pegando a gramatica
                                Element gramatica = (Element) noGramatica;
                                newGrupoGramatical = gramatica.getTextContent();
                            }
                        }

                        palavras.add(new Palavra(0, newPalavra, newGrupoGramatical, newDefinicao));
                    }
                }

                //Se acontecer algum tipo de erro no XML
            } catch (Exception ex) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Erro");
                alertDialog.setMessage("Erro ao ler arquivo, por favor contate o suporte");
                alertDialog.create();

            }
            return palavras;
        }

}
