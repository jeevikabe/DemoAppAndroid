package com.example.demoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReceiverTwo extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = getResultData();
        Toast.makeText(context, "Receiver Two: " + data, Toast.LENGTH_SHORT).show();

        // Uncomment to stop further processing
        // abortBroadcast();
    }
}

