package com.letv4545.ajay_mac.quizapplication;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.IOException;
import java.io.InputStream;

public class HelpScreenActivity extends AppCompatActivity {
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        myWebView=findViewById(R.id.webViewHelp);

        AssetManager assetManager=this.getAssets();
        try{
            InputStream inputStream=assetManager.open("Help.html");
            int size=inputStream.available();
            byte[] buffer=new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String content=new String(buffer,"UTF-8");
            Log.d("Data",content);
            myWebView.loadData(content,"text/html","utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
