package servidor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author Nads
 */
public class Inicio implements Runnable{
    
    private Socket cliente;

    public Inicio(Socket cliente) {
        this.cliente = cliente;
        
    }
            
    
    public void run() {

        try {
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            String valor = entrada.readUTF();
            
             if(valor.equals("atualizacao")){
             
                //Iniciado a thread
                AtualizacaoWEB atualizacao = new AtualizacaoWEB(cliente, saida, entrada);
                Thread thread = new Thread(atualizacao);
                thread.start();
                
             } else if (valor.equals("")) {

                //Se o que cliente escolher alguma informação que não exista
                saida.writeBoolean(false);
                saida.close();
                entrada.close();
                cliente.close();

            }else{
                
                //Se o que cliente escolher alguma informação que não exista
                saida.writeBoolean(false);
                saida.close();
                entrada.close();
                cliente.close();
                
            }
        } catch (IOException e) {

        }
    }
}
