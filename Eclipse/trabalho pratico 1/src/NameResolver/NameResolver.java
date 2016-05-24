package NameResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NameResolver {

    public static void main(String[] args) {
        String hostname = "localhost"; // "www.isel.pt";
        
        try {      
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("localhost: " + localhost);
            System.out.println("Nome: " + localhost.getCanonicalHostName());
            
            // Obter todos os endereços da máquina actual 
            System.out.println("Endereços: ");
            InetAddress []addrs = InetAddress.getAllByName(localhost.getCanonicalHostName());
            for (int i = 0; i < addrs.length; i++) {
                System.out.println("   address["+i+"]: " + addrs[i].getHostAddress());
                
            }
            
            System.out.println("-----------------------------");
            
            // Nome -> InetAddress
            InetAddress address = InetAddress.getByName(hostname);
            String ip = address.getHostAddress();
            System.out.println("O host: " + hostname + " tem o IP: " + ip);

            // Obter todos os endereços de uma máquina
            InetAddress [] addresses = InetAddress.getAllByName(hostname);
            for (int i=0; i < addresses.length; ++i) {
                System.out.println("i= "+i+": " + addresses[i]);
            }
            
            // InetAddress -> IP
            byte[] rawIp = address.getAddress();
            InetAddress addr2 = InetAddress.getByAddress(rawIp);
            System.out.println("O IP: " + ip + " tem o nome: " + addr2.getHostName());

        } catch (UnknownHostException uhe) {
            System.err.println(uhe);
            System.err.println("Nao foi possivel encontrar " + hostname);
        }
    }

}
