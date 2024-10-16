package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AutoCompleteActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    private Button navigateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        // AutoCompleteTextView setup
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        navigateButton = findViewById(R.id.navigateButton);
        String[] suggestions = {"Audi", "Benz", "Lamborghini", "Renault", "Volvo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to AnimationActivity
                Intent intent = new Intent(AutoCompleteActivity.this, AnimationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}