package Web;

import java.net.*;
import java.io.*;

public class h extends Thread {
    Socket c;

    public h(Socket s) {
        c = s;
        start();
    }

    public static void main(String[] a) {
        try {
            ServerSocket s = new ServerSocket(8181);
            for (;;) {
                new h(s.accept());
            }
        } catch (Exception e) {
        }
    }

    public void run() {
        try {
            BufferedReader i = new BufferedReader(new InputStreamReader(c
                    .getInputStream()));
            DataOutputStream o = new DataOutputStream(c.getOutputStream());
            try {
                String s, p;
                while ((s = i.readLine()).length() > 0) {
                    if (s.startsWith("GE")) {
                        p = (s.split(" "))[1];
                        p = ("." + (p.endsWith("/") ? p + "index.html" : p))
                                .replace('/', File.separatorChar);
                        int l = (int) new File(p).length();
                        byte[] b = new byte[l];
                        new FileInputStream(p).read(b);
                        o.writeBytes("HTTP/1.0 200 OK\nContent-Length:" + l
                                + "\n\n");
                        o.write(b, 0, l);
                    }
                }
            } catch (Exception e) {
                o.writeBytes("HTTP/1.0 404 ERROR\n\n");
            }
            o.close();
        } catch (Exception e) {
        }
    }
}