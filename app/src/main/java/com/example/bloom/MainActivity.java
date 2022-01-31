package com.example.bloom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    ImageView image;
    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bloom));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bloom, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bloom));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {

                SharedPreferences sharedPreferences = getSharedPreferences(welcome.PREFS_NAME,0);
            boolean hasloggedin = sharedPreferences.getBoolean("hasloggedin",false);
            if(hasloggedin)
            {
                Intent homeintent = new Intent(MainActivity.this, Mainwindow.class);
                startActivity(homeintent);
                finish();
            }
            else{
                Intent homeintent = new Intent(MainActivity.this, welcome.class);
                startActivity(homeintent);
                finish();
            }


        },SPLASH_TIME_OUT);
        image = findViewById(R.id.imageView);
    }
}