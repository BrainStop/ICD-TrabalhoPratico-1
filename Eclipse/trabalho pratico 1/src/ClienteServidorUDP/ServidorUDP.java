package ClienteServidorUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP {

    public final static int DIM_BUFFER  = 100;


    public static void main(String args[]) {

        int port = 5025;

        DatagramSocket sockfd = null;


        try { 
            // Cria socket - UDP no porto indicado (well-known port)
            // Este construtor pode gerar uma excep��o SocketException o que significa que ou
            // existe outro programa a utilizar o porto pretendido ou o socket est� a ser 
            // associado a um porto entre 1 e 1023 sem privil�gios de administrador 
            // (como, por exemplo, no sistema UNIX)
            sockfd = new DatagramSocket(port);

            // Cria um datagramaPacket para recep��o
            byte inputBuffer[]  = new byte[DIM_BUFFER];
            DatagramPacket inputPacket = new DatagramPacket(inputBuffer, inputBuffer.length);

            for ( ; ; ) {
                try {
                    System.out.println("Servidor aguarda recep��o de mensagem no porto " + port);

                    // Recep��o de um datagrama
                    inputPacket.setLength(DIM_BUFFER);
                    sockfd.receive(inputPacket);

                    String messageStr = new String(inputPacket.getData(), 0, inputPacket.getLength());
                    
                    System.out.println("Dados recebidos: " + messageStr);
                    System.out.println("N�mero de bytes recebidos: " + inputPacket.getLength());
                    System.out.println("Endere�o do cliente: " + inputPacket.getAddress()
                            + " Porto: " + inputPacket.getPort());

                    // Criar um datagrama para enviar a resposta
                    messageStr = "Resp. Srv. -> " + messageStr;
                    DatagramPacket outputPacket = new DatagramPacket(messageStr.getBytes(), messageStr.length(), 
                            inputPacket.getAddress(), inputPacket.getPort());

                    // Enviar datagrama de resposta 
                    sockfd.send(outputPacket);
                } 
                catch (IOException e) {
                    System.err.println("Erro nas comunica��es: " + e.getMessage());
                }
            } // end for
        } 
        catch (SocketException e) {
            System.err.println("Erro na cria��o do socket: " + e.getMessage());
        }
    } // end main

} // end ServidorUDP
