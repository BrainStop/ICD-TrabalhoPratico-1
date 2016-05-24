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
            // o construtor ServerSocket gera uma excep��o IOException (mais especificamente BindException)
            // se o socket n�o poder ser associado ao porto indicado. Esta excep��o significa que ou
            // existe outro programa a utilizar o porto pretendido ou o socket est� a ser associado a um 
            // porto entre 1 e 1023 sem privil�gios de administrador (como, por exemplo, no sistema UNIX)
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

                    // PrinterWriter instanciado com 2� arg true o que indica auto flush quando 
                    // utilizarmos os m�todos println ou printf (ver documenta��o)
                    os = new PrintWriter(newSock.getOutputStream(), true);

                    while (true) {
                        String inputLine = is.readLine(); 
                        // String inputLine = socketInput.nextLine(); 
                        System.out.println("Recebi -> " + inputLine);
                        if (inputLine.equals("")) { break; }
                    }

//                    os.println("Ol� para ti tamb�m!!");
                    os.println("<HTML><BODY><H1>Ola pessoal!</H1></BODY></HTML>");
                }
                catch (IOException e) {
                    // Excep��o relacionada com a liga��o actual - newSock, pode ser
                    // originada, por exemplo, pelo cliente ter terminado a liga��o.
                    // Em princ�pio n�o se pretende realizar qualquer ac��o, eventualmente,
                    // registar o acontecimento num ficheiro de log
                    System.err.println("erro na liga�ao " + newSock + ": " + e.getMessage());
                }
                finally {
                    // garantir que o socket � fechado
                    try {
                        if (is != null) is.close();  
                        if (os != null) os.close();
       
                        if (newSock != null) newSock.close();                    
                    } catch (IOException e) { }
                }
            } // end for
        } 
        catch (IOException e) {
            System.err.println("Excep��o no servidor: " + e);
        }
    } // end main
    
} // end ServidorTCP
