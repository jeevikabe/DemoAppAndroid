package com.example.demoapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class LocalBroadcastActivity extends AppCompatActivity {
    private LocalBroadcastManager localBroadcastManager;
    private MyReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);
        // Initialize LocalBroadcastManager
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        // Initialize the receiver
        myReceiver = new MyReceiver();

        // Register the receiver with an IntentFilter
        IntentFilter intentFilter = new IntentFilter("com.example.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(myReceiver, intentFilter);

        Button sendBroadcastButton = findViewById(R.id.send_broadcast_button);
        sendBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocalBroadcast();
            }
        });
    }

    // Method to send the local broadcast
    private void sendLocalBroadcast() {
        Intent intent = new Intent("com.example.LOCAL_BROADCAST");
        intent.putExtra("message", "Hello from Local Broadcast!");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the receiver
        localBroadcastManager.unregisterReceiver(myReceiver);
    }
}