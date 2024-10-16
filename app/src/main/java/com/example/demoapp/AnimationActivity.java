package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button fadeButton, scaleButton, rotateButton, bounceButton, slideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        imageView = findViewById(R.id.imageView);
        fadeButton = findViewById(R.id.fadeButton);
        scaleButton = findViewById(R.id.scaleButton);
        rotateButton = findViewById(R.id.rotateButton);
        bounceButton = findViewById(R.id.bounceButton);
        slideButton = findViewById(R.id.slideButton);

        // Fade In Animation
        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(fadeIn);
                showToast("Fade In Animation");
            }
        });

        // Scale Animation
        scaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                imageView.startAnimation(scale);
                showToast("Scale Animation");
            }
        });

        // Rotate Animation
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                imageView.startAnimation(rotate);
                showToast("Rotate Animation");
            }
        });

        // Bounce Animation
        bounceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                imageView.startAnimation(bounce);
                showToast("Bounce Animation");
            }
        });

        // Slide Animation
        slideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
                imageView.startAnimation(slide);
                showToast("Slide Animation");
            }
        });
    }

    // Helper method to display a toast message
    private void showToast(String message) {
        Toast.makeText(AnimationActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
