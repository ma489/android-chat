package com.mansourahmed.android.chat;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by root on 12/10/15.
 */
public class ServerConnectionTask extends AsyncTask<String, Void, Socket> {

    @Override
    protected Socket doInBackground(String... params) {
        try {
            String hostname = params[0];
            Integer portNumber = Integer.valueOf(params[1]);
            Socket clientSocket = new Socket(hostname, portNumber);
            new PrintWriter(clientSocket.getOutputStream(), true).println("/setUsername " + params[2]);
            return clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}