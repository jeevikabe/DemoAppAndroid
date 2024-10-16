package com.example.demoapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OrderedBoardCastActivity extends AppCompatActivity {
    private ReceiverOne receiverOne;
    private ReceiverTwo receiverTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_board_cast);
       // Button sendBroadcastButton = findViewById(R.id.send_broadcast_button);

        Button sendBroadcastButton = findViewById(R.id.send_broadcast_button);

        // Instantiate receivers
        receiverOne = new ReceiverOne();
        receiverTwo = new ReceiverTwo();

        // Register ReceiverOne with higher priority
        IntentFilter filter1 = new IntentFilter("com.example.ORDERED_BROADCAST");
        filter1.setPriority(10);
        registerReceiver(receiverOne, filter1);

        // Register ReceiverTwo with lower priority
        IntentFilter filter2 = new IntentFilter("com.example.ORDERED_BROADCAST");
        filter2.setPriority(5);
        registerReceiver(receiverTwo, filter2);

        sendBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderedBroadcast();
            }
        });
    }

    // Sending an ordered broadcast
    private void sendOrderedBroadcast() {
        Intent intent = new Intent("com.example.ORDERED_BROADCAST");
        intent.putExtra("message", "Hello from MainActivity");
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister receivers
        unregisterReceiver(receiverOne);
        unregisterReceiver(receiverTwo);
    }
}