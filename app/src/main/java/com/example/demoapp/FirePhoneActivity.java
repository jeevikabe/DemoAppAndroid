package com.example.demoapp;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
public class FirePhoneActivity extends AppCompatActivity {
    private EditText phoneEditText, otpEditText;
    private Button sendOtpButton, verifyOtpButton;

    private FirebaseAuth mAuth;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_phone);
        phoneEditText = findViewById(R.id.phoneEditText);
        otpEditText = findViewById(R.id.otpEditText);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        sendOtpButton.setOnClickListener(v -> {
            String phoneNumber = phoneEditText.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNumber)) {
                phoneEditText.setError("Enter phone number");
                return;
            }
            sendVerificationCode(phoneNumber);
        });

        verifyOtpButton.setOnClickListener(v -> {
            String code = otpEditText.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                otpEditText.setError("Enter OTP");
                return;
            }
            verifyCode(code);
        });
    }

//    private void sendVerificationCode(String phoneNumber) {
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
//                .setPhoneNumber(phoneNumber)        // Phone number to verify
//                .setTimeout(60L, TimeUnit.SECONDS)  // Timeout duration
//                .setActivity(this)                 // Activity for callback binding
//                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }


    private void sendVerificationCode(String phoneNumber) {
        Log.d("PhoneAuth", "Sending OTP to: " + phoneNumber);
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    String code = credential.getSmsCode();
                    if (code != null) {

                        otpEditText.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(FirePhoneActivity.this, "Verification Failed", Toast.LENGTH_LONG).show();
                    Log.e("PhoneAuth", e.getMessage());
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(s, token);
                    verificationId = s;
                    Toast.makeText(FirePhoneActivity.this, "Verification Completed", Toast.LENGTH_LONG).show();

                    otpEditText.setVisibility(View.VISIBLE);
                    verifyOtpButton.setVisibility(View.VISIBLE);
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        Toast.makeText(FirePhoneActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FirePhoneActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FirePhoneActivity.this, "Verification Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}




















































//package com.example.demoapp;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//public class FirePhoneActivity extends AppCompatActivity {
//
//    private FirebaseAuth mAuth;
//    private EditText phoneEditText, otpEditText;
//    private Button sendOtpBtn, verifyOtpBtn;
//    private String verificationId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fire_phone);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        phoneEditText = findViewById(R.id.phoneEditText);
//        otpEditText = findViewById(R.id.otpEditText);
//        sendOtpBtn = findViewById(R.id.sendOtpBtn);
//        verifyOtpBtn = findViewById(R.id.verifyOtpBtn);
//
//        sendOtpBtn.setOnClickListener(v -> sendVerificationCode());
//        verifyOtpBtn.setOnClickListener(v -> verifyCode());
//    }
//
//    private void sendVerificationCode() {
//        String phoneNumber = phoneEditText.getText().toString().trim();
//
//        if (TextUtils.isEmpty(phoneNumber)) {
//            phoneEditText.setError("Phone number is required");
//            phoneEditText.requestFocus();
//            return;
//        }
//
//        // Send OTP to the entered phone number
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//    // Callbacks for handling the verification process
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//            String code = credential.getSmsCode();
//            if (code != null) {
//                otpEditText.setText(code); // Auto-fill the OTP
//                verifyCode();
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//            Toast.makeText(FirePhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String verificationId,
//                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
//            FirePhoneActivity.this.verificationId = verificationId;
//            Toast.makeText(FirePhoneActivity.this, "OTP sent!", Toast.LENGTH_SHORT).show();
//
//            // Show OTP fields after OTP is sent
//            otpEditText.setVisibility(View.VISIBLE);
//            verifyOtpBtn.setVisibility(View.VISIBLE);
//        }
//    };
//
//    private void verifyCode() {
//        String code = otpEditText.getText().toString().trim();
//        if (TextUtils.isEmpty(code)) {
//            otpEditText.setError("Enter OTP");
//            otpEditText.requestFocus();
//            return;
//        }
//        verifyCode(verificationId, code);
//    }
//
//    private void verifyCode(String verificationId, String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(FirePhoneActivity.this, "Authentication successful!", Toast.LENGTH_LONG).show();
//                        // Handle the signed-in user, proceed to the next activity or dashboard
//                    } else {
//                        Toast.makeText(FirePhoneActivity.this, "Authentication failed!", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//}
