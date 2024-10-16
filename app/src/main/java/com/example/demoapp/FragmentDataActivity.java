package com.example.demoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FragmentDataActivity extends AppCompatActivity {
    private SharedViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_data);
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Observe LiveData from ViewModel to get data from Fragment
        viewModel.getSelected().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String data) {
                // Handle data received from Fragment
                Toast.makeText(FragmentDataActivity.this, "Received from Fragment: " + data, Toast.LENGTH_SHORT).show();
            }
        });

        // Load Fragment
        if (savedInstanceState == null) {
            DataTransferFragment fragment = new DataTransferFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        // Button to send message to Fragment
        Button sendMessageButton = findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(v -> {
            String messageToSend = "Hello from MainActivity!";
            viewModel.select(messageToSend); // Pass data to Fragment via ViewModel
        });
    }
}