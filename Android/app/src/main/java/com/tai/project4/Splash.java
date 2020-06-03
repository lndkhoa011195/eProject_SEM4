package com.tai.project4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends FragmentActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    private Animation animation;
    private ImageView logo;
    private TextView appTitle;
    private TextView appSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        appTitle = findViewById(R.id.grocery);
        appSlogan = findViewById(R.id.slogan);
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        if (ConnectivityReceiver.isConnected()) {

            if (savedInstanceState == null) {
                flyIn();
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    endSplash();
                }
            }, 3000);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection ");
            builder.setMessage("Do you want to go to settings");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent j = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(j);
                    System.exit(0);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    System.exit(0);
                }
            });
            builder.show();
        }
    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);
    }

    private void endSplash() {
        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_animation_back);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation_back);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.pro_animation_back);
        appSlogan.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent;
                if (sp.getString("loginid", null) != null) {
                    intent = new Intent(getApplicationContext(),
                            HomeActivity.class);
                }
                else {
                    intent = new Intent(getApplicationContext(),
                            LoginActivity.class);
                }

                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}

