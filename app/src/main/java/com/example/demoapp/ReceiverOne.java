package com.example.demoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverOne extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        Toast.makeText(context, "Receiver One: " + message, Toast.LENGTH_SHORT).show();

        // Modify the result and pass it to the next receiver
        setResultData("Modified by Receiver One");
    }
}

