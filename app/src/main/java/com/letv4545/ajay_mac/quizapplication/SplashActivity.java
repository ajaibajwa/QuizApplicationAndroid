package com.letv4545.ajay_mac.quizapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT=100;
    private String intendedActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences exitTokenPrefs=getSharedPreferences("exitToken",MODE_PRIVATE);
        boolean exitTokenCheck=exitTokenPrefs.getBoolean("tokenValue",false);
        if (exitTokenCheck){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mIntent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mIntent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            },SPLASH_TIMEOUT);
        }

    }
}
