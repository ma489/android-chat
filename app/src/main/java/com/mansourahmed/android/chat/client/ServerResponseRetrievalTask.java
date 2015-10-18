package com.mansourahmed.android.chat.client;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by root on 12/10/15.
 */
public class ServerResponseRetrievalTask extends AsyncTask<String, Void, String> {

    private final BufferedReader inputStream;

    public ServerResponseRetrievalTask(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            System.out.println("Calling inputStream.readLine()");
            String lineRead = inputStream.readLine();
            System.out.println("Read: " + lineRead);
            return lineRead;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}