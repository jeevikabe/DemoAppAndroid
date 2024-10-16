package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ModernUiActivity extends AppCompatActivity {

    private TextInputLayout nameInputLayout;
    private TextInputEditText nameEditText;
    private MaterialButton submitButton;
    private FloatingActionButton fab;
    private CardView infoCard;
    private TextView cardText;
    private ImageView cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modern_ui);

        // Initialize UI components
        nameInputLayout = findViewById(R.id.nameInputLayout);
        nameEditText = findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);
        fab = findViewById(R.id.fab);
        infoCard = findViewById(R.id.infoCard);
        cardText = findViewById(R.id.cardText);
        cardImage = findViewById(R.id.cardImage);

        // Handle Submit Button Click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                if (name.isEmpty()) {
                    nameInputLayout.setError("Please enter your name");
                } else {
                    nameInputLayout.setError(null); // Clear the error
                    cardText.setText("Hello, " + name + "! Welcome to Material Design.");
                    cardImage.setImageResource(R.drawable.ic_welcome); // Set an image in the CardView
                    Toast.makeText(ModernUiActivity.this, "Name submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle FAB Click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModernUiActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ModernUiActivity.this, AutoCompleteActivity.class);
                startActivity(intent);
            }
        });
    }
}
