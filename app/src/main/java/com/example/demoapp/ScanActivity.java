package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class ScanActivity extends AppCompatActivity {

    private static final int SCANNER_REQUEST_CODE = 1;
    private TextView scannedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scannedDataTextView = findViewById(R.id.scanned_data_text_view);

        findViewById(R.id.start_scanner_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, ScannerActivity.class);
                startActivityForResult(intent, SCANNER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("scanned_data")) {
                String scannedData = data.getStringExtra("scanned_data");
                // Update the TextView with the scanned data
                scannedDataTextView.setText("Scanned: " + scannedData);
            }
        }
    }
}
