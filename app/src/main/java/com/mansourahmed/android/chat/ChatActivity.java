package com.mansourahmed.android.chat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
//    private Socket clientSocket;
    private BufferedReader inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        String hostname = getIntent().getStringExtra(ConnectionActivity.HOSTNAME);
//        Integer portNumber = getIntent().getIntExtra(ConnectionActivity.PORTNUMBER, 8080);
        String hostname = "192.168.1.68";
        String portNumber = "8080";
        ServerConnectionTask serverConnectionTask = new ServerConnectionTask();
        AsyncTask<String, Void, Socket> asyncResult = serverConnectionTask.execute(hostname, portNumber);
        try {
//            clientSocket = new Socket(hostname, portNumber);
            Socket clientSocket = asyncResult.get();
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
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
        TextView messagesView = (TextView) findViewById(R.id.messages);
        clearMessageWindowIfEmpty(messagesView);
        String input = getInput();
        handleInput(messagesView, input);
    }

    private void handleInput(TextView messagesView, String input) {
        int lineCount = messagesView.getLineCount();
        String text = lineCount + ": " + input + "\n";
        messagesView.append(text);
        getServerResponse(messagesView, text);
    }

    private void getServerResponse(TextView messagesView, String text) {
        ServerResponseRetrievalTask serverResponseRetrievalTask = new ServerResponseRetrievalTask(inputStream, outputStream);
        AsyncTask<String, Void, String> asyncResult = serverResponseRetrievalTask.execute(text);
        try {
            String response = asyncResult.get();
            messagesView.append("Server response: " + response + "\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private String getInput() {
        return ((EditText) findViewById(R.id.input_message)).getText().toString();
    }

    private void clearMessageWindowIfEmpty(TextView messagesView) {
        String noMessages = getResources().getString(R.string.text_nomessages);
        if (messagesView.getText().equals(noMessages)) {
            messagesView.setText("");
        }
    }

}