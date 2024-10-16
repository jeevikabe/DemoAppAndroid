package com.example.demoapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import Slider.CarouselAdapter;

public class CarouselsliderActivity extends AppCompatActivity {

    private static final String TAG = "CarouselsliderActivity";
    private ViewPager2 viewPager;
    private LinearLayout dotContainer;
    private Handler handler;
    private Runnable runnable;
    private final int SLIDE_DELAY = 3000; // Delay in milliseconds (3 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carouselslider);

        viewPager = findViewById(R.id.viewPager);
        dotContainer = findViewById(R.id.dotContainer);

        int[] imageResources = {
                R.drawable.img_4,
                R.drawable.img_7,
                R.drawable.img_5
        };

        CarouselAdapter adapter = new CarouselAdapter(imageResources);
        viewPager.setAdapter(adapter);

        setupDots(imageResources.length);
        setupAutoSlide();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
            }
        });
    }

    private void setupDots(int count) {
        dotContainer.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getDrawable(R.drawable.dot_indicator)); // Use the custom dot drawable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotContainer.addView(dot);
        }
        updateDots(0); // Set initial dot
    }

    private void updateDots(int position) {
        for (int i = 0; i < dotContainer.getChildCount(); i++) {
            ImageView dot = (ImageView) dotContainer.getChildAt(i);
            if (i == position) {
                dot.setImageDrawable(getDrawable(R.drawable.dot_indicator_filled)); // Set filled drawable for the current dot
            } else {
                dot.setImageDrawable(getDrawable(R.drawable.dot_indicator)); // Set default drawable for others
            }
        }
    }

    private void setupAutoSlide() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % viewPager.getAdapter().getItemCount();
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, SLIDE_DELAY);
            }
        };
        handler.postDelayed(runnable, SLIDE_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
