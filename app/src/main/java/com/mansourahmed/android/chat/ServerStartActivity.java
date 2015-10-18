package com.mansourahmed.android.chat;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class ServerStartActivity extends ActionBarActivity {

    public static final String PORT_NUMBER_LOCAL_SERVER = "com.mansourahmed.myfirstapp.PORT_NUMBER_LOCAL_SERVER";
    public static final String LOCAL_IP_ADDRESS = "com.mansourahmed.myfirstapp.LOCAL_IP_ADDRESS";
    private String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_start);
        TextView ipAddressView = (TextView) findViewById(R.id.ip_address);
        String ipAddress = alternativeGetIpAddress();
        ipAddressView.setText("IP: " + ipAddress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_start, menu);
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

    public void startServer(View view) {
        EditText portNumberView = (EditText) findViewById(R.id.portnumber_local_server);
        String portNumber = portNumberView.getText().toString();
        String ipAddress = alternativeGetIpAddress();
        Intent intent = new Intent(this, LocalServerConnectionActivity.class);
        intent.putExtra(PORT_NUMBER_LOCAL_SERVER, portNumber);
        intent.putExtra(LOCAL_IP_ADDRESS, ipAddress);
        startActivity(intent);
    }

    private String getIpAddress() {
        if (ipAddress == null) {
            AsyncTask<Void, Void, String> asyncResult = new IpAddressGetterTask().execute();
            try {
                ipAddress = asyncResult.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return ipAddress;
    }

    private String alternativeGetIpAddress() {
        if (ipAddress == null) {
            WifiManager systemService = (WifiManager) getSystemService(WIFI_SERVICE);
            AsyncTask<WifiManager, Void, String> asyncResult =
                    new AlternativeIpAddressGetterTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, systemService);
            try {
                ipAddress = asyncResult.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return ipAddress;
    }

}