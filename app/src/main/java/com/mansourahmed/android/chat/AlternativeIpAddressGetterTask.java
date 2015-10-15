package com.mansourahmed.android.chat;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by root on 15/10/15.
 */
public class AlternativeIpAddressGetterTask extends AsyncTask<WifiManager, Void, String> {

    @Override
    protected String doInBackground(WifiManager... params) {
//        WifiManager wm = params[0];
//        int ipAddress = wm.getConnectionInfo().getIpAddress();
//        return Formatter.formatIpAddress(ipAddress);
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
//                        return inetAddress.getHostAddress();
                        return Formatter.formatIpAddress(inetAddress.hashCode());
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("error", ex.toString());
        }
        return null;
    }

}