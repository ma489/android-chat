package com.mansourahmed.android.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ConnectionActivity extends ActionBarActivity {

    public static final String HOSTNAME = "com.mansourahmed.myfirstapp.HOSTNAME";
    public static final String PORTNUMBER = "com.mansourahmed.myfirstapp.PORTNUMBER";
    public static final String USERNAME = "com.mansourahmed.myfirstapp.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void connect(View view) {
        EditText hostnameView = (EditText) findViewById(R.id.hostname);
        String hostname = hostnameView.getText().toString();
        EditText portnumberView = (EditText) findViewById(R.id.portnumber);
        String portNumber = portnumberView.getText().toString();
        EditText usernameView = (EditText) findViewById(R.id.username);
        String username = usernameView.getText().toString();
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(HOSTNAME, hostname);
        intent.putExtra(PORTNUMBER, portNumber);
        intent.putExtra(USERNAME, username);
        startActivity(intent);
    }

    public void clearTextHostname(View view) {
        EditText inputMessageView = (EditText) findViewById(R.id.hostname);
        inputMessageView.setText("");
    }

    public void clearTextUsername(View view) {
        EditText inputMessageView = (EditText) findViewById(R.id.username);
        inputMessageView.setText("");
    }

    public void clearTextPortNumber(View view) {
        EditText inputMessageView = (EditText) findViewById(R.id.portnumber);
        inputMessageView.setText("");
    }

}