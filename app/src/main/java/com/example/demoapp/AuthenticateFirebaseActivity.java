package com.example.demoapp;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticateFirebaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_firebase);

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            // Link UI elements
            emailField = findViewById(R.id.emailField);
            passwordField = findViewById(R.id.passwordField);
        }

        // Handle Sign-Up
        public void handleSignUp(View view) {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            signUp(email, password);
        }

        // Handle Sign-In
        public void handleSignIn(View view) {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            signIn(email, password);
        }

        // Handle Sign-Out
        public void handleSignOut(View view) {
            signOut();
        }

        private void signUp(String email, String password) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthenticateFirebaseActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AuthenticateFirebaseActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void signIn(String email, String password) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthenticateFirebaseActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AuthenticateFirebaseActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void signOut() {
            mAuth.signOut();
            Toast.makeText(AuthenticateFirebaseActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        }
    }