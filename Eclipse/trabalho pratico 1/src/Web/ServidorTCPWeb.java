package Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//import java.util.Scanner;


public class ServidorTCPWeb {

    public static void main(String[] args) 
    {
        int port = 5025; 

        ServerSocket serverSocket = null;


        try {
            // o construtor ServerSocket gera uma excepção IOException (mais especificamente BindException)
            // se o socket não poder ser associado ao porto indicado. Esta excepção significa que ou
            // existe outro programa a utilizar o porto pretendido ou o socket está a ser associado a um 
            // porto entre 1 e 1023 sem privilégios de administrador (como, por exemplo, no sistema UNIX)
            serverSocket = new ServerSocket(port);

            
            Socket newSock    = null;
            BufferedReader is = null;
            PrintWriter os    = null;

            for( ; ; ) {
                System.out.println("Servidor aguarda ligacao no porto " + port + "..." );
                
                // Espera connect do cliente
                newSock = serverSocket.accept(); 
                
                try {
                    // circuito virtual estabelecido: socket cliente na variavel newSock
                    System.out.println("Servidor aceitou a ligacao: " +  newSock.getRemoteSocketAddress());

                    is = new BufferedReader(new InputStreamReader(newSock.getInputStream()));
                    // Scanner socketInput = new Scanner( newSock.getInputStream() );

                    // PrinterWriter instanciado com 2º arg true o que indica auto flush quando 
                    // utilizarmos os métodos println ou printf (ver documentação)
                    os = new PrintWriter(newSock.getOutputStream(), true);

                    while (true) {
                        String inputLine = is.readLine(); 
                        // String inputLine = socketInput.nextLine(); 
                        System.out.println("Recebi -> " + inputLine);
                        if (inputLine.equals("")) { break; }
                    }

//                    os.println("Olá para ti também!!");
                    os.println("<HTML><BODY><H1>Ola pessoal!</H1></BODY></HTML>");
                }
                catch (IOException e) {
                    // Excepção relacionada com a ligação actual - newSock, pode ser
                    // originada, por exemplo, pelo cliente ter terminado a ligação.
                    // Em princípio não se pretende realizar qualquer acção, eventualmente,
                    // registar o acontecimento num ficheiro de log
                    System.err.println("erro na ligaçao " + newSock + ": " + e.getMessage());
                }
                finally {
                    // garantir que o socket é fechado
                    try {
                        if (is != null) is.close();  
                        if (os != null) os.close();
       
                        if (newSock != null) newSock.close();                    
                    } catch (IOException e) { }
                }
            } // end for
        } 
        catch (IOException e) {
            System.err.println("Excepção no servidor: " + e);
        }
    } // end main
    
} // end ServidorTCP
