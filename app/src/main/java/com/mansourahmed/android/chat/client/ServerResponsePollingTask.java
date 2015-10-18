package com.mansourahmed.android.chat.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by root on 12/10/15.
 */
public class ServerResponsePollingTask extends AsyncTask<Void, Void, String> {

    private final BufferedReader inputStream;
    private Handler messageHandler;

    public ServerResponsePollingTask(BufferedReader inputStream, Handler messageHandler) {
        this.inputStream = inputStream;
        this.messageHandler = messageHandler;
    }

    @Override
    protected String doInBackground(Void... params) {
        while (true) {
            System.out.println("Polling for server responses...");
            AsyncTask<String, Void, String> asyncResult =
                    new ServerResponseRetrievalTask(inputStream).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            System.out.println("Getting asyncResult...");
            String response;
            String text;
            try {
                response = asyncResult.get(10L, TimeUnit.SECONDS);
                System.out.println("Response from server: " + response);
                if (response != null) {
                    String[] responseSplit = response.split(" - ");
                    String user = responseSplit[1];
                    String actualMessage = responseSplit.length == 3 ? responseSplit[2] : "";
                    text = formatMessage(actualMessage, user);
                } else {
                    text = "null response from Server\n";
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println(e.getClass());
                text = "Failed to get response from server\n";
            }
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("message", text);
            msg.setData(bundle);
            messageHandler.sendMessage(msg);
        }
    }

    private String formatMessage(String response, final String userName) {
        return "(" + getTime() + ") " + userName + ": " + response + "\n";
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(Calendar.getInstance().getTime());
    }

}