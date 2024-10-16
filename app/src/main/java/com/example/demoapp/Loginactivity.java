package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Loginactivity extends AppCompatActivity implements View.OnClickListener {

    EditText Username, Password;
    Button Lbutton;
    ImageView eyeIcon;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private boolean isPasswordVisible = false; // Flag to toggle password visibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Lbutton = findViewById(R.id.button);
        eyeIcon = findViewById(R.id.eye_icon);

        Lbutton.setOnClickListener(this);
        eyeIcon.setOnClickListener(this);

        // Check if the user is already logged in
        checkIfLoggedIn();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            String user = Username.getText().toString();
            String pass = Password.getText().toString();

            if (user.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                authenticate(user, pass);
            }
        } else if (view.getId() == R.id.eye_icon) {
            togglePasswordVisibility();
        }
    }

    private void authenticate(String user, String pass) {
        // Sample hardcoded credentials
        if (!user.trim().equals("Jeevika")) {
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.trim().equals("1234")) {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        } else {
            // Save the credentials in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERNAME_KEY, user);
            editor.putString(PASSWORD_KEY, pass);
            editor.apply();  // Save

            // Navigate to TopicsActivity after successful login
            Intent intent = new Intent(Loginactivity.this, TopicsActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("pass", pass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void checkIfLoggedIn() {
        String savedUsername = sharedPreferences.getString(USERNAME_KEY, null);
        String savedPassword = sharedPreferences.getString(PASSWORD_KEY, null);

        if (savedUsername != null && savedPassword != null) {
            // If credentials are found in SharedPreferences, go to TopicsActivity directly
            Intent intent = new Intent(Loginactivity.this, TopicsActivity.class);
            intent.putExtra("user", savedUsername);
            intent.putExtra("pass", savedPassword);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            Password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eyeIcon.setImageResource(R.drawable.eye);
        } else {
            // Show password
            Password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eyeIcon.setImageResource(R.drawable.eye);
        }
        // Move cursor to the end of the text
        Password.setSelection(Password.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }
}
