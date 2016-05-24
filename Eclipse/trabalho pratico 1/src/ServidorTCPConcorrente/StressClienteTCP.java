package ServidorTCPConcorrente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class StressClienteTCP {

    public final static String DEFAULT_HOSTNAME = "localhost";
    
    public final static int DEFAULT_PORT = 5025; 
    
    public final static int DEFAULT_CONNECTION_NUMBER = 120;
    
    
    public static void main(String[] args) {

        String host = DEFAULT_HOSTNAME;  // Máquina onde reside a aplicação servidora
        int    port = DEFAULT_PORT;      // Porto da aplicação servidora

        int    threadNum = DEFAULT_CONNECTION_NUMBER;

        
        if (args.length > 0) {
            host = args[0];
        }
        
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
                if (port < 1 || port > 65535) port = DEFAULT_PORT;
            }
            catch (NumberFormatException e) {
                System.err.println("Erro no porto indicado");
            }
        }
        
        if (args.length > 2) {
            try {
                threadNum = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                System.err.println("Erro no número de ligações");
            }
        }
        
        System.out.println("-> " + host + ":" + port + " " + threadNum + " ligações");
        
        
        Socket socket     = null;

        for (int thId=0; thId < threadNum; ++thId ) {
            try {
                socket = new Socket(host, port);
                Thread th = new ConnectionThread(thId, socket, 100000);
                th.start();
            } 
            catch (IOException e) {
                System.err.println("Erro na ligação " + thId + ": " + e.getMessage());
                break;
            }
        } // end for

    } // end main

} // end StressClienteTCP



class ConnectionThread extends Thread {

    private int id;

    private Socket socket;

    private long disturbTime;


    public ConnectionThread(int id, Socket socket, long disturbTime) {
        this.id = id;
        this.socket = socket;
        this.disturbTime = disturbTime;
    }

    
    public void run() {

        BufferedReader is = null;
        PrintWriter    os = null;

        try {
            // Mostrar os parametros da ligação
            System.out.println("Thread " + id + ": " + socket);

            try {
                Thread.sleep(disturbTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // Stream para escrita no socket
            os = new PrintWriter(socket.getOutputStream(), true); 

            // Stream para leitura do socket
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Escreve no socket
            os.println("Olá mundo!!!");

            // Mostrar o que se recebe do socket
            System.out.println("Recebi -> " + is.readLine()); 

        } 
        catch (IOException e) {
            System.err.println("[thread " + id + "] Erro na ligação: " + e.getMessage());
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
    } // end run

} // end ConnectionThread
