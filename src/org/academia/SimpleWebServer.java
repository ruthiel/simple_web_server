package org.academia;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 08/11/16.
 */
public class SimpleWebServer {


    public static void main(String[] args) throws IOException {



        String hostName = "localhost";
        int portNumber = 4000;

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("À espera de ligação...");

        ExecutorService poll = Executors.newFixedThreadPool(1);


        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Entrou este IP: " + clientSocket.getRemoteSocketAddress());
            poll.submit(new MultipleClient(clientSocket));
        }


    }

}
