package tp_01.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCPConcorrente {

    public final static int DEFAULT_PORT = 5025; 
    
    
    public static void main(String[] args) 
    {
        int port = DEFAULT_PORT; 

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.err.println("Erro no porto indicado");
            }
        }
        
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);

            Socket newSock = null;

            for( ; ; ) {
                System.out.println("Servidor TCP concorrente aguarda ligacao no porto " + port + "..." );
                
                // Espera connect do cliente
                newSock = serverSocket.accept(); 
                            
                Thread th = new RequestHandler(newSock);
                th.start();
            }
            
            
        } 
        catch (IOException e) {
            System.err.println("Excepção no servidor: " + e);
        }
    } // end main

} // end ServidorTCP
