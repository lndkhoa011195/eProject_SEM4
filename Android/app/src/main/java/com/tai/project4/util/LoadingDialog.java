package com.tai.project4.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import com.tai.project4.R;


public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void showLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoadingDialog(){
        alertDialog.dismiss();
    }
}
