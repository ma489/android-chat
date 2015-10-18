package com.mansourahmed.android.chat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;


public class ChatActivity extends ActionBarActivity {

    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private String username;
    private TextView messagesView;
//    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String hostname = getIntent().getStringExtra(ConnectionActivity.HOSTNAME);
        String portNumber = getIntent().getStringExtra(ConnectionActivity.PORTNUMBER);
        username = getIntent().getStringExtra(ConnectionActivity.USERNAME);
        AsyncTask<String, Void, Socket> asyncResult =
                new ServerConnectionTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, hostname, portNumber, username);
        Socket clientSocket;
        try {
            clientSocket = asyncResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        messagesView = (TextView) findViewById(R.id.messages);
        messagesView.setMovementMethod(new ScrollingMovementMethod());
        pollForServerResponse(messagesView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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


    public void sendMessage(View view) {
        String input = getInput();
        handleInput(input);
        messagesView.append("/local/ - " + input + "\n");
    }

    private void handleInput(String input) {
        System.out.println("Handling input: " + input);
        AsyncTask<String, Void, Void> asyncResult =
                new MessageSendingTask(outputStream).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, input);
        System.out.println("Handled input: " + input);
    }

    private void pollForServerResponse(TextView messagesView) {
        System.out.println("Executing server-response-poller");
        AsyncTask<Void, Void, String> asyncResult =
                new ServerResponsePollingTask(inputStream, messageHandler).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private Handler messageHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = msg.getData().getString("message");
            messagesView.append("msg.what: " + msg.what + " - " + message + "\n");
        }
    };

    private String getInput() {
        EditText inputView = (EditText) findViewById(R.id.input_message);
        String input = inputView.getText().toString();
        inputView.getText().clear();
        inputView.setText("");
        return input;
    }

    private void clearMessageWindowIfEmpty(TextView messagesView) {
        String noMessages = getResources().getString(R.string.text_nomessages);
        if (messagesView.getText().equals(noMessages)) {
            messagesView.setText("");
        }
    }

}