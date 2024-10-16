//package com.example.demoapp;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//public class SimCardActivity extends AppCompatActivity {
//    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
//    private TextView textView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sim_card);
//
//        // Initialize the TextView
//        textView = findViewById(R.id.textView);
//
//        // Check for permission
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_PHONE_STATE},
//                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//        } else {
//            // Permission already granted, proceed with functionality
//            Log.d("SimCardActivity", "READ_PHONE_STATE permission granted");
//            // You can add functionality to check the SIM state here
//        }
//    }
//
//    // Method to update the TextView
//    public void updateSimState(String stateMessage) {
//        textView.setText(stateMessage); // Update the TextView with SIM state
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with accessing phone state
//                Log.d("SimCardActivity", "READ_PHONE_STATE permission granted");
//            } else {
//                // Permission denied, handle accordingly
//                Log.d("SimCardActivity", "READ_PHONE_STATE permission denied");
//            }
//        }
//    }
//}


package com.example.demoapp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SimCardActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private TextView tvSimHolderName;
    private TextView tvPhoneNumber;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_card);

        tvSimHolderName = findViewById(R.id.tvSimHolderName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        btnRefresh = findViewById(R.id.btnRefresh);

        if (tvSimHolderName == null || tvPhoneNumber == null || btnRefresh == null) {
            Toast.makeText(this, "View initialization failed!", Toast.LENGTH_SHORT).show();
            return; // Exit if any view is null
        }

        // Check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        } else {
            getSimInfo(); // Proceed with accessing the telephony services
        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSimInfo();
            }
        });
    }

    private void getSimInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            String simHolderName = telephonyManager.getSimOperatorName(); // Retrieves the SIM carrier name
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String phoneNumber = telephonyManager.getLine1Number(); // Retrieves the phone number (if available)

            tvSimHolderName.setText("SIM Name: " + (simHolderName != null ? simHolderName : "Not Available"));
            tvPhoneNumber.setText("Phone Number: " + (phoneNumber != null ? phoneNumber : "Not Available"));
        } else {
            Toast.makeText(this, "Failed to retrieve TelephonyManager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSimInfo(); // Permission granted, retrieve SIM info
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}



