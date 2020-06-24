package com.group1.project4;

import android.app.Application;
import android.os.SystemClock;

import com.group1.project4.util.ConnectivityReceiver;


public class MyAppActivity extends Application {

    private static MyAppActivity mInstance;

    public static synchronized MyAppActivity getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(500);
        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
