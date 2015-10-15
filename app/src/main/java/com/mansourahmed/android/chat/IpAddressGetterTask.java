package com.mansourahmed.android.chat;

import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by root on 15/10/15.
 */
public class IpAddressGetterTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
//        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        return ip;
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}