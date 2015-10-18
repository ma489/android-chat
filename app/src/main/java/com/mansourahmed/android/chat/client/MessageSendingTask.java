package com.mansourahmed.android.chat.client;

import android.os.AsyncTask;

import java.io.PrintWriter;

/**
 * Created by root on 12/10/15.
 */
public class MessageSendingTask extends AsyncTask<String, Void, Void> {

    private final PrintWriter outputStream;

    public MessageSendingTask(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    protected Void doInBackground(String... params) {
        String message = params[0];
        System.out.println("Client Sending message: " + message);
        outputStream.println(message);
        System.out.println("Client Sent message: " + message);
        return null;
    }

}