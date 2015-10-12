package com.mansourahmed.android.chat;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by root on 12/10/15.
 */
public class ServerConnectionTask extends AsyncTask<String, Void, Socket> {

    @Override
    protected Socket doInBackground(String... params) {
        try {
            Socket clientSocket = new Socket(params[0], Integer.valueOf(params[1]));
//            PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return clientSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}