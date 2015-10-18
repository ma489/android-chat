package com.mansourahmed.android.chat.server;

import android.os.AsyncTask;

/**
 * Created by root on 15/10/15.
 */
public class RunServerTask extends AsyncTask<ChatServer, Void, Void> {


    @Override
    protected Void doInBackground(ChatServer... params) {
        ChatServer chatServer = (ChatServer) params[0];
        chatServer.listenForClientsAndRespond();
        return null;
    }
}