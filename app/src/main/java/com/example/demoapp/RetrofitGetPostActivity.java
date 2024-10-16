package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RetrofitGetPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_get_post);

        Button getButton = findViewById(R.id.getButton);
        Button postButton = findViewById(R.id.postButton);



        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger GET request
                Intent in = new Intent(RetrofitGetPostActivity.this, RetrofitActivity.class);
                startActivity(in);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RetrofitGetPostActivity.this, RetrofitPostActivity.class);
                startActivity(in);

            }
        });
        Button APiButton = findViewById(R.id.ApiButton);
        APiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RetrofitGetPostActivity.this, HttpPostActivity.class);
                startActivity(in);

            }
        });

        Button APigetButton = findViewById(R.id.ApigetButton);
        APigetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RetrofitGetPostActivity.this, HttpGetActivity.class);
                startActivity(in);
            }
        });

    }


}