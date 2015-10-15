package com.mansourahmed.android.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mansour on 08/10/15.
 */
public class ChatServer {

    private static final int MAX_MESSAGES = 100;
    private final ServerSocket serverSocket;
    private final Queue<String> messageQueue = new ArrayBlockingQueue<>(MAX_MESSAGES);

    public ChatServer(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForClientsAndRespond() {
        ExecutorService executorService = Executors.newCachedThreadPool(); //TODO shutdown eventually
        ArrayList<Socket> clientSockets = new ArrayList<>();
        ClientResponder clientResponder = new ClientResponder(clientSockets, messageQueue);
        executorService.submit(clientResponder);
        while (true) {
            Socket clientSocket = acceptClientConnection();
            clientSockets.add(clientSocket);
            executorService.submit(new ClientListener(clientSocket, messageQueue));
        }
    }

    private Socket acceptClientConnection() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ChatServer initialise(String arg) {
        System.out.println("\n========== Chat Server =======\n");
        int portNumber = Integer.valueOf(arg);
        System.out.println("Starting server on port: " + portNumber);
        return new ChatServer(portNumber);
    }

    public static void main(String[] args) {
        ChatServer chatServer = initialise(args[0]);
        System.out.println("Server started. Waiting for clients...");
        chatServer.listenForClientsAndRespond();
    }

}