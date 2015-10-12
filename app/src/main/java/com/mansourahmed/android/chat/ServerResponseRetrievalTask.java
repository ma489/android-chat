package com.mansourahmed.android.chat;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 12/10/15.
 */
public class ServerResponseRetrievalTask extends AsyncTask<String, Void, String> {

    private final BufferedReader inputStream;
    private final PrintWriter outputStream;

    public ServerResponseRetrievalTask(BufferedReader inputStream, PrintWriter outputStream) {

        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    protected String doInBackground(String... params) {
        outputStream.println(params[0]);
        try {
            String response = inputStream.readLine();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}