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
            Socket clientSocket = new Socket(params[0], Integer.valueOf(params[1]));
            new PrintWriter(clientSocket.getOutputStream(), true).println("/setUsername " + params[2]);
            return clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}