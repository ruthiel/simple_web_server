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

        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String[] browserMessage = in.readLine().split(" ");
        System.out.println(browserMessage[1]);

        File file = getFileFromResource(browserMessage[1].substring(1));
        System.out.println(file.exists());
        String x = headerBuilder(file);

        readFile(file);

        out.write(x.getBytes());
        out.write(readFile(file));
        out.close();


    }

    public static File getFileFromResource(String resource) {

        if ( resource.isEmpty() ) {
            resource = "index.html";
        }

        return new File("web-root/" + resource);

    }


    public static String headerBuilder(File file) {

        String statusCode = "";
        String fileType = "";
        String size = "";

        if ( file.exists() ) {
            statusCode = "200 Document Follows";
            fileType = file.getName();
            size = String.valueOf(file.length());

        } else {
            statusCode = "404 Not Found";
            fileType = "text/html";
            size = "0";

        }



        return "HTTP/1.0 " + statusCode + "\r\n" +
                "Content-Type: " + fileType + "; charset=UTF-8\r\n" +
                "Content-Length: " + size + "\r\n" +
                "\r\n";

    }

    public static byte[] readFile(File file) throws IOException {

        byte[] indexData;

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            indexData = new byte[1024];

            inputStream.read(indexData);
            inputStream.close();

        return indexData;
    }

}
