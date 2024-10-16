package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.interfaces.APIResponseListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import networkapi.APIServer;
import networkapi.AuthResponse;
import networkapi.Links;
import networkapi.LocalStorage;

public class GetOtpActivity extends AppCompatActivity {

    EditText otpInput;
    Button verifyOtpButton;
    private static final String PREFS_NAME = "OtpPrefs";
    private static final String KEY_IS_VERIFIED = "isVerified";
    private LocalStorage localStorage;

    //private static final String KEY_PHONE = "phone";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);
//        // Check if OTP is already verified
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        boolean isVerified = sharedPreferences.getBoolean(KEY_IS_VERIFIED, false);
localStorage=new LocalStorage(this);
//        if (isVerified) {
//            // If already verified, navigate to GetListActivity
//            Intent intent = new Intent(GetOtpActivity.this, GetListActivity.class);
//            startActivity(intent);
//            finish();
//            return; // Exit onCreate
//        }
//        // Retrieve the phone number from SharedPreferences
//        String phone = sharedPreferences.getString("phone", null);
//        if (phone == null) {
//            Toast.makeText(this, "Phone number not found", Toast.LENGTH_SHORT).show();
//            finish(); // Close the activity if phone number is not found
//            return;
//        }
        otpInput = findViewById(R.id.otp_input);
        verifyOtpButton = findViewById(R.id.verify_otp_button);

        // Handle Verify OTP button click
        verifyOtpButton.setOnClickListener(view -> {
            String otp = otpInput.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(GetOtpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                // Call method to verify OTP
                verifyOtp(otp);
            }
        });
    }

    private void verifyOtp(String otp) {
        // Get the phone number passed from the previous activity
        String phone = getIntent().getStringExtra("phone");

        // Create JSON object to send to the server
        JSONObject otpObject = new JSONObject();
        try {
            otpObject.put("otp", otp);
            otpObject.put("mobile", phone);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating OTP request", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make the API request
        APIServer networkHelper = new APIServer(this);
        networkHelper.postJSON1(Links.OTP_VERIFY, otpObject, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) { // assuming 200 is the success code
                    Toast.makeText(GetOtpActivity.this, "OTP Verified: " + response, Toast.LENGTH_SHORT).show();
                    AuthResponse authResponse = new GsonBuilder().create().fromJson(response, AuthResponse.class);

                    String authJson = new Gson().toJson(authResponse);
                    localStorage.put(LocalStorage.AUTH, authJson);

                    localStorage.put(LocalStorage.APP_AUTH_PASSWORD, otp);
                    localStorage.put(LocalStorage.APP_AUTH_USER_NAME, phone);

                    Intent intent = new Intent(GetOtpActivity.this, GetListActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(GetOtpActivity.this, "Error: " + response, Toast.LENGTH_SHORT).show();
                }
            }

//            @Override
//            public void onError(String error) {
//                Toast.makeText(GetOtpActivity.this, "OTP Verification failed: " + error, Toast.LENGTH_SHORT).show();
//            }
        });

    }
}
