package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        Button crashButton = findViewById(R.id.crashButton);
        crashButton.setText(getString(R.string.crash_app));
        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Force a crash for testing
                throw new RuntimeException("This is a test crash");
            }
        });
    }
}