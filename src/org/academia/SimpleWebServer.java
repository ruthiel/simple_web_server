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

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("À espera de ligação...");

        Socket clientSocket = serverSocket.accept();
        System.out.println(("Ligado a: " + clientSocket));

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String[] header = in.readLine().split(" ");
        System.out.println(in.readLine());

        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

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

    public static void headerBuilder(String type) {
        switch (type) {
            case ("html"):
                break;
            case ("image"):
                break;
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
