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

    }



    public static String headerBuilder(String resource) {

        String statusCode = "";
        String fileType = "";
        String size = "";

        File file = new File(resource);

       if ( file.exists()) {
           statusCode = "200";
           fileType = file.getName();
           size = String.valueOf(file.length());
       } else {
           statusCode = "404 File Not Found";
           fileType = "null";
           size = "null";
       }



        return "HTTP/1.0 " + statusCode + "\r\n" +
                "Content-Type: " + fileType + "; charset=UTF-8\r\n" +
                "Content-Length: " + size + "\r\n" +
                "\r\n";

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
