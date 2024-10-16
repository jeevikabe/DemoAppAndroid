package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.interfaces.APIResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import networkapi.APIServer;
import networkapi.Links;
import networkapi.LocalStorage;
import networkapi.SmsVerification;

public class AuthenticationOtpActivity extends AppCompatActivity {

    EditText phoneNumber;
    Button loginButton;
    private String hashcode;
    private LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_otp);
        localStorage=new LocalStorage(this);
        String phonenum = localStorage.get(LocalStorage.APP_AUTH_USER_NAME);
        String otp = localStorage.get(LocalStorage.APP_AUTH_PASSWORD);
if(phonenum!= null && otp!= null){
    Intent intent= new Intent(AuthenticationOtpActivity.this,GetListActivity.class);
    startActivity(intent);
    finish();
}
        ArrayList<String> appCodes = new ArrayList<>();
        SmsVerification hash = new SmsVerification(this);
        appCodes = hash.getAppSignatures();
        hashcode = appCodes.get(0);
        phoneNumber = findViewById(R.id.phone_number);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {
            String phone = phoneNumber.getText().toString().trim();

            // Validate phone number
            if (phone.isEmpty() || !isPhoneNumberValid(phone)) {
                Toast.makeText(AuthenticationOtpActivity.this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
            } else {
                // Call the getOtp method to request OTP
                getOtp(phone);
            }
        });
    }

    private boolean isPhoneNumberValid(String phone) {
        // Check if the phone number is exactly 10 digits and contains only numbers
        return phone.matches("\\d{10}");
    }

    private void getOtp(String phone) {
        // Create JSON object for OTP request
        JSONObject otpRequest = new JSONObject();
        try {
            otpRequest.put("mobile", phone);
            otpRequest.put("hashcode",hashcode);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating OTP request", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make the API call
        APIServer networkHelper = new APIServer(this);
        networkHelper.postJSON1(Links.OTP_GET, otpRequest, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                // Handle the response here based on the statusCode and response
                if (statusCode == 200) {
                    // OTP successfully sent
                    Toast.makeText(AuthenticationOtpActivity.this, "OTP Sent: " + response, Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(AuthenticationOtpActivity.this, GetOtpActivity.class);
                    intent.putExtra("phone", phone); // Pass the phone number to the next activity
                    startActivity(intent);
                } else {
                    // Error case
                    Toast.makeText(AuthenticationOtpActivity.this, "Failed to send OTP: " + response, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }}