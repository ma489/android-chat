package com.mansourahmed.android.chat.server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * Created by Mansour on 12/10/15.
 */
public class ClientResponder implements Callable<Void> {

    private final ArrayList<Socket> clientSockets;
    private final Queue<String> messageQueue;


    public ClientResponder(ArrayList<Socket> clientSockets, Queue<String> messageQueue) {
        this.clientSockets = clientSockets;
        this.messageQueue = messageQueue;
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            String message = messageQueue.poll();
            if (message == null) {
                continue;
            }
            for (Socket socket : clientSockets) {
                PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
                outputStream.println(message);
            }
        }
    }

}