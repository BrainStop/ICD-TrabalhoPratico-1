package Web;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class GetHTMLPage {

    public static void main(String[] args) {

        String host = "www.isel.ipl.pt"; // M�quina ligar ao socket servidor
        int port = 80; // Porto para ligar ao socket servidor

        Socket socket     = null;
        BufferedReader is = null;
        PrintWriter os    = null;

        try {
            socket = new Socket(host, port);

            // Mostrar os parametros da liga��o
            System.out.println("Endere�o do Servidor: " + socket.getInetAddress()
                    + " Porto: " + socket.getPort());
            System.out.println("Endere�o Local: " + socket.getLocalAddress()
                    + " Porto: " + socket.getLocalPort());

            // Stream para escrita no socket
            os = new PrintWriter(socket.getOutputStream(), true);

            // Stream para leitura do socket
            is = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // Escreve no socket
            os.println("GET http://www.isel.pt HTTP/1.0");
            os.println();

            // Mostrar o que se recebe do socket
            String inputLine = is.readLine();
            while ( inputLine != null ) {
                System.out.println(inputLine);
                inputLine = is.readLine();
            }
        }
        catch (IOException e) {
            System.err.println("Erro na liga��o: " + e.getMessage());
        }
        finally {
            // No fim de tudo, fechar os streams e o socket
            try {
                if (os != null) os.close();
                if (is != null) is.close();
                if (socket != null ) socket.close();
            }
            catch (IOException e) { 
                // if an I/O error occurs when closing this socket
            }
        } // end finally
    } // end main

}
