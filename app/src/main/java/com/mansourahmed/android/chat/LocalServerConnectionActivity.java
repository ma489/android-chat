package com.mansourahmed.android.chat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mansourahmed.android.chat.server.ChatServer;

import java.util.concurrent.ExecutionException;


public class LocalServerConnectionActivity extends ActionBarActivity {

    private String hostname;
    private String portNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_server_connection);
        hostname = getIntent().getStringExtra(ServerStartActivity.LOCAL_IP_ADDRESS);
        portNumber = getIntent().getStringExtra(ServerStartActivity.PORT_NUMBER_LOCAL_SERVER);
        boolean success = startServer();
        if (success) {
            displayDetails();
        } else {
            displayFailed();
        }
    }

    private void displayFailed() {
        TextView messageView = (TextView) findViewById(R.id.server_startup);
        messageView.append(" FAILED!");
        EditText hostnameView = (EditText) findViewById(R.id.local_hostname);
        hostnameView.setText("IP: " + hostname);
        EditText portNumberView = (EditText) findViewById(R.id.local_portnumber);
        portNumberView.setText("Port: " + portNumber);
        Button chatButtonView = (Button) findViewById(R.id.chat_button);
        chatButtonView.setEnabled(false);
    }

    private void displayDetails() {
        TextView messageView = (TextView) findViewById(R.id.server_startup);
        messageView.append(" SUCCEEDED!");
        EditText hostnameView = (EditText) findViewById(R.id.local_hostname);
        hostnameView.setText("IP: " + hostname);
        EditText portNumberView = (EditText) findViewById(R.id.local_portnumber);
        portNumberView.setText("Port: " + portNumber);
        Button chatButtonView = (Button) findViewById(R.id.chat_button);
        chatButtonView.setEnabled(true);
    }

    private boolean startServer() {
        AsyncTask<String, Void, ChatServer> asyncTask = new ServerStartTask().execute(portNumber);
        ChatServer chatServer;
        try {
            chatServer = asyncTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (chatServer != null) {
            new RunServerTask().execute(chatServer);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_server_connection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void chatOnLocalServer(View view) {
        EditText usernameView = (EditText) findViewById(R.id.local_username);
        String username = usernameView.getText().toString();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(ConnectionActivity.HOSTNAME, hostname);
        intent.putExtra(ConnectionActivity.PORTNUMBER, portNumber);
        intent.putExtra(ConnectionActivity.USERNAME, username);
        startActivity(intent);
    }

}