package com.tai.project4;

import android.app.Application;
import android.os.SystemClock;


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
