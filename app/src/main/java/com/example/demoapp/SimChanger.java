//package com.example.demoapp;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//
//public class SimChanger extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // Check if the received intent action is for SIM state change
//        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.SIM_STATE_CHANGED")) {
//            // Get the SIM state from the intent
//            String state = intent.getStringExtra("state");
//
//            // Log the SIM state and update the UI
//            if (state != null) {
//                if (TelephonyManager.SIM_STATE_ABSENT == Integer.parseInt(state)) {
//                    Log.d("SimChangeReceiver", "SIM card is absent");
//                    ((SimCardActivity) context).updateSimState("SIM card is absent");
//                } else if (TelephonyManager.SIM_STATE_READY == Integer.parseInt(state)) {
//                    Log.d("SimChangeReceiver", "SIM card is ready");
//                    ((SimCardActivity) context).updateSimState("SIM card is ready");
//                } else if (TelephonyManager.SIM_STATE_UNKNOWN == Integer.parseInt(state)) {
//                    Log.d("SimChangeReceiver", "SIM card state is unknown");
//                    ((SimCardActivity) context).updateSimState("SIM card state is unknown");
//                } else {
//                    Log.d("SimChangeReceiver", "Received SIM state: " + state);
//                    ((SimCardActivity) context).updateSimState("Received SIM state: " + state);
//                }
//            }
//        }
//    }
//}

package com.example.demoapp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimChanger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the received intent action is for SIM state change
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.SIM_STATE_CHANGED")) {
            // Notify the user about the SIM state change
            Toast.makeText(context, "SIM card state has changed!", Toast.LENGTH_LONG).show();
        }
    }
}

