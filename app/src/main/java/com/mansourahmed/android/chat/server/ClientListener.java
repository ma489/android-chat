package com.mansourahmed.android.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * Created by Mansour on 08/10/15.
 */
public class ClientListener implements Callable<Void> {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private Socket clientSocket;
    private Queue<String> messageQueue;
    private BufferedReader inputStream;
    private String clientHostname;
    private String username;

    public ClientListener(Socket clientSocket, Queue<String> messageQueue) {
        this.clientSocket = clientSocket;
        this.messageQueue = messageQueue;
    }

    @Override
    public Void call() throws Exception {
        initialise();
        respondToMessages();
        return null;
    }

    private void initialise() throws IOException {
        clientHostname = clientSocket.getInetAddress().getHostName();
        System.out.println("Client connected from: " + clientHostname);
        username = clientHostname;
        inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void respondToMessages() throws IOException {
        String inputLine = inputStream.readLine();
        while (inputLine != null) {
            if (inputLine.startsWith("/setUsername")) {
                username = inputLine.split(" ")[1];
                String usernameChangedMessage = getDateTime() + " - [Server] - "
                        + clientHostname + " is now known as " + username;
                messageQueue.add(usernameChangedMessage);
                System.out.println(usernameChangedMessage);
            }
            String incomingChatMessage = getDateTime() + " - [" + username + "] - " + inputLine;
            messageQueue.offer(incomingChatMessage);
            System.out.println(incomingChatMessage);
            inputLine = inputStream.readLine();
        }
    }

    private String getDateTime() {
        DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return dateFormatter.format(new Date());
    }

}