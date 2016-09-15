package com.example.kushaalsingla.smartprix;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kushaal Singla on 30-Jun-16.
 */
public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread a = new Thread() {
            @Override
            public void run() {

                try {

                    Thread.sleep(1000);

                    Intent i = new Intent("com.ks.Home");

                    startActivity(i);
                } catch (Exception e)

                {

                    e.printStackTrace();
                }
            }
        };

        a.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}



