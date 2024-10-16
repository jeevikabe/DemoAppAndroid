package com.example.demoapp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class CircleActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView seekBarTV;
    private SeekBar seekBar;
    private ArrayList<String> valuesList;
    private double max_radius_in_meters;
    private String areaLength = "100";
    private Marker myLocationMarker;
    private Circle circle;
    private LatLng currentLatLng;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        seekBarTV = findViewById(R.id.seekBarTV);
        seekBar = findViewById(R.id.seekBar);
        setUpSeekBar();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setUpSeekBar() {
        valuesList = new ArrayList<>();
        valuesList.add("100");
        valuesList.add("200");
        valuesList.add("300");
        valuesList.add("400");
        valuesList.add("500");
        valuesList.add("600");
        valuesList.add("700");
        valuesList.add("800");
        valuesList.add("900");
        valuesList.add("1000");

        seekBar.setMax(valuesList.size() - 1);
        seekBarTV.setText(valuesList.get(0) + " m");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                areaLength = valuesList.get(progress);
                seekBarTV.setText(areaLength + " m");
                drawCircle(currentLatLng);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable map click to move the marker
        mMap.setOnMapClickListener(latLng -> {
            currentLatLng = latLng;
            drawCircle(latLng);
        });

        // Fetch the user's current location and place the marker there
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                drawCircle(currentLatLng);
            }
        });
    }

    private void drawCircle(LatLng latLng) {
        if (mMap == null || latLng == null) {
            return;
        }

        max_radius_in_meters = Double.parseDouble(areaLength);

        // Remove the existing marker and circle if they exist
        if (myLocationMarker != null) {
            myLocationMarker.remove();
        }
        if (circle != null) {
            circle.remove();
        }

        // Add the default red marker at the current or clicked location
        myLocationMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Selected Location"));

        // Draw the circle around the marker
        circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(max_radius_in_meters)
                .strokeColor(0xFFADD8E6)
                .fillColor(0x3C12B06A));

        Log.d("CircleActivity", "Latitude: " + latLng.latitude + ", Longitude: " + latLng.longitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }
}
