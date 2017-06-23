/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

  
    public static void main(String[] args) {
         
         try {

            //Iniciando controlador
            ServerSocket servidor = new ServerSocket(1777);
            System.out.println("Servidor aberto rodando na porta 1777");
             
                        
            // while rodando loop e esperando conexão com o servidor e iniciando a thread  
            while (true) {

                Socket cliente = servidor.accept();
                System.out.println("Conectado " + cliente.getInetAddress().getHostAddress());

                Inicio inicio = new Inicio(cliente);
                Thread thread = new Thread(inicio);
                thread.start();

            }
        } catch (IOException e) {

        }
    }

}
