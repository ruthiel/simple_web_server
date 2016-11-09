package org.academia;

import java.io.*;
import java.net.Socket;

/**
 * Created by codecadet on 09/11/16.
 */
public class MultipleClient implements Runnable {

    Socket clientSocket;

    public MultipleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {


        System.out.println(("Ligado a: " + clientSocket));

        DataOutputStream out = null;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String[] browserMessage = in.readLine().split(" ");
            System.out.println(browserMessage[1]);

            File file = getFileFromResource(browserMessage[1].substring(1));
            System.out.println("This file exists: " + file.exists());
            String header = headerBuilder(file);
            readFile(file);

            out.write(header.getBytes());
            out.write(readFile(file));
            out.close();

        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    public File getFileFromResource(String resource) {

        if (resource.isEmpty()) {
            resource = "index.html";
        }

        return new File("web-root/" + resource);

    }


    public String headerBuilder(File file) {

        String statusCode = "";
        String fileType = "";
        String size = "";

        if (file.exists()) {
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

    public byte[] readFile(File file) throws IOException {

        byte[] indexData;

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        indexData = new byte[1024];

        inputStream.read(indexData);
        inputStream.close();

        return indexData;
    }
}
