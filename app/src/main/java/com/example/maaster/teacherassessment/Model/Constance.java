package com.example.maaster.teacherassessment.Model;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Administrator on 7/12/2559.
 */

public class Constance {

    public static final String IP_ADDRESS = "mongodb://192.168.1.39:27017";

    public static final boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
