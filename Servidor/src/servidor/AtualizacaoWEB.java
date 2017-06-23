package servidor;

import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Nads
 */
public class AtualizacaoWEB implements Runnable {

    private Socket cliente;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;

    public AtualizacaoWEB(Socket cliente, ObjectOutputStream saida, ObjectInputStream entrada) {

        this.cliente = cliente;
        this.saida = saida;
        this.entrada = entrada;

    }

    @Override
    public void run() {
        try {

            saida.writeBoolean(true);
            saida.flush();

            double versao = entrada.readDouble();

            ArrayList<Atualizacao> atualizar = VerificarAtualizacao(versao);
            saida.writeInt(atualizar.size());
            saida.flush();
            
            for(int i = 0; i < atualizar.size(); i++){
                
                //Saida informações
                saida.writeDouble(atualizar.get(i).getVersao());
                saida.flush();
                saida.writeUTF(atualizar.get(i).getObjetivo());
                saida.flush();
                
                //Tamanho do arrayList Palavra
                saida.writeInt(atualizar.get(i).palavra.size());
                saida.flush();
                
                //Saida ArrayList Palavra
                for(int j = 0; j < atualizar.get(i).palavra.size(); j++){
                    saida.writeUTF(atualizar.get(i).palavra.get(j).getPalavra());
                    saida.flush();
                    saida.writeUTF(atualizar.get(i).palavra.get(j).getGrupoGramatical());
                    saida.flush();
                    saida.writeUTF(atualizar.get(i).palavra.get(j).texto);
                    saida.flush();
                }
                
            }
                        
                saida.writeBoolean(true);
                saida.flush();
            
            entrada.close();
            saida.close();
            cliente.close();

            System.out.println("Descontectou " + cliente.getInetAddress().getHostAddress());

        } catch (IOException e) {

        }
    }

    public ArrayList<Atualizacao> VerificarAtualizacao(double versao) {

        ArrayList<Atualizacao> atualizar = new ArrayList();
        
        
        Atualizacao atualizacao = new Atualizacao(1.3, "Alterar");
        
        if(versao < atualizacao.getVersao()){
            
        atualizacao.palavra.add(new Palavra("Feijoada", "teste", "Teste"));
        atualizar.add(atualizacao);
        }


        return atualizar;
    }

}
