package org.academia;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by codecadet on 08/11/16.
 */
public class SimpleWebServer {


    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 4000;

        byte[] test = readFile("web-root/index.html");
        System.out.println(test[10]);

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("À espera de ligação...");

        Socket clienteSocket = serverSocket.accept();
        System.out.println(("Ligado a: " + clienteSocket));

        BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
        String[] header = in.readLine().split(" ");

        DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream());

        String header200 = "HTTP/1.0 200 Document Follows\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: xxx \r\n" +
                "\r\n";

        String header404 = "HTTP/1.0 404 Not Found\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: xxx \r\n" +
                "\r\n";


        if (header[1].equals("/")) {
            out.write(header200.getBytes());
            out.write(readFile("web-root/index.html"));
            out.flush();

        } else {
            out.write(header404.getBytes());
            out.flush();
        }

    }

    public static byte[] readFile(String path) throws IOException {

        byte[] indexData;

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
            indexData = new byte[1024];

            inputStream.read(indexData);
            inputStream.close();

        return indexData;
    }

}
