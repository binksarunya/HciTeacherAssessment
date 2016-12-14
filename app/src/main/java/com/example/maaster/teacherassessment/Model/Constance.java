package com.example.maaster.teacherassessment.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by Administrator on 7/12/2559.
 */

public class Constance {

    public static final String IP_ADDRESS = "mongodb://172.20.10.3:27017";

    public static final boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }



}
