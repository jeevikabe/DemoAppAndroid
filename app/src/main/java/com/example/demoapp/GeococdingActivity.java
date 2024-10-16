package com.example.demoapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeococdingActivity extends AppCompatActivity {

    private EditText addressInput, latitudeInput, longitudeInput;
    private TextView resultText;
    private Button geoButton, reverseGeoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geococding);

        addressInput = findViewById(R.id.addressInput);
        latitudeInput = findViewById(R.id.latitudeInput);
        longitudeInput = findViewById(R.id.longitudeInput);
        resultText = findViewById(R.id.resultText);
        geoButton = findViewById(R.id.geoButton);
        reverseGeoButton = findViewById(R.id.reverseGeoButton);

        // Handle Geocoding (Address to Coordinates)
        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressInput.getText().toString();
                if (!address.isEmpty()) {
                    getCoordinatesFromAddress(address);
                }
            }
        });

        // Handle Reverse Geocoding (Coordinates to Address)
        reverseGeoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitudeStr = latitudeInput.getText().toString();
                String longitudeStr = longitudeInput.getText().toString();

                if (!latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);
                    getAddressFromCoordinates(latitude, longitude);
                } else {
                    resultText.setText("Please enter valid latitude and longitude");
                }
            }
        });
    }

    // Geocoding: Convert Address to Coordinates
    public void getCoordinatesFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                resultText.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                resultText.setText("Address not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            resultText.setText("Error: " + e.getMessage());
        }
    }

    // Reverse Geocoding: Convert Coordinates to Address
    public void getAddressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                resultText.setText("Address: " + address);
            } else {
                resultText.setText("Address not found for the given coordinates");
            }
        } catch (IOException e) {
            e.printStackTrace();
            resultText.setText("Error: " + e.getMessage());
        }
    }
}
