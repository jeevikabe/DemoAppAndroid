package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateProfile extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Initialize views
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        updateButton = findViewById(R.id.updateButton);

        // Get data from intent
        Intent intent = getIntent();
        usernameEditText.setText(intent.getStringExtra("username"));
        emailEditText.setText(intent.getStringExtra("email"));
        addressEditText.setText(intent.getStringExtra("address"));

        // Set button click listener
        updateButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", usernameEditText.getText().toString());
            resultIntent.putExtra("email", emailEditText.getText().toString());
            resultIntent.putExtra("address", addressEditText.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
