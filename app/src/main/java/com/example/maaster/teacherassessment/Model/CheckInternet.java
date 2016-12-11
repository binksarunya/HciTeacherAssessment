package com.example.maaster.teacherassessment.Model;

/**
 * Created by Administrator on 11/12/2559.
 */

import android.app.Application;

public class CheckInternet extends Application {

    private static CheckInternet mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized CheckInternet getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}