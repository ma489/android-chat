package com.mansourahmed.android.chat;

import android.os.AsyncTask;

import com.mansourahmed.android.chat.server.ChatServer;

/**
 * Created by root on 15/10/15.
 */
public class ServerStartTask extends AsyncTask<String, Void, ChatServer> {

    @Override
    protected ChatServer doInBackground(String... params) {
        int portNumber = Integer.valueOf(params[0]);
        return new ChatServer(portNumber);
    }

}