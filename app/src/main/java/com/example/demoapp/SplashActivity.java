package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import networkapi.Links;

public class SplashActivity extends AppCompatActivity {
    // Set the duration for which the splash screen will be shown
    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2000 milliseconds = 2 seconds

    // Constants for SharedPreferences
    private static final String PREFS_NAME = "MyPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // setLocale("kn");
        // Use a Handler to delay the transition to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo get user name from local storage
                // Get username and password from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString(USERNAME_KEY, null);
                String savedPassword = sharedPreferences.getString(PASSWORD_KEY, null);

                Intent intent;
                if (savedUsername != null && savedPassword != null) {
                    // If both username and password exist, the user is logged in, go to TopicsActivity
                    intent = new Intent(SplashActivity.this, TopicsActivity.class);
                    new Links(SplashActivity.this).setLinks(SplashActivity.this);
                    intent.putExtra("user", savedUsername);
                    intent.putExtra("pass", savedPassword);
                } else {
                    // If no username and password are saved, redirect to the LoginActivity
                    intent = new Intent(SplashActivity.this, Loginactivity.class);
                }

                startActivity(intent);
                finish(); // Finish the splash activity so the user cannot go back to it
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Reload the activity to apply language change
       // Intent refresh = new Intent(this, TopicsActivity.class);
       // startActivity(refresh);
      //  finish();
    }
}
