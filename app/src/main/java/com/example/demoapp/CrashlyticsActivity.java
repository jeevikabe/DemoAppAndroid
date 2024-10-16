package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class CrashlyticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crashlytics);
        //Initialize Crashlytics
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);



        // Initialize Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        // Set up a default uncaught exception handler
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            // Log the exception
            Log.e("MyApplication", "Uncaught exception: ", throwable);
            // Optionally, rethrow the exception
            System.exit(1);
        });
    }
}