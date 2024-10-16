package com.example.demoapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFeatureActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_feature);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Call methods to demonstrate features
        addMarkers();
        moveCamera();
        customizeMarkers();
        handleMarkerClickEvents();
    }

    // Adding markers to the map for Hassan and Chikkamagaluru
    private void addMarkers() {
        LatLng hassan = new LatLng(13.0068, 76.1004); // Coordinates for Hassan
        mMap.addMarker(new MarkerOptions().position(hassan).title("Hassan"));

        LatLng chikkamagaluru = new LatLng(13.3161, 75.7750); // Coordinates for Chikkamagaluru
        mMap.addMarker(new MarkerOptions().position(chikkamagaluru).title("Marker in Chikkamagaluru"));
    }

    // Moving the camera to Hassan
    private void moveCamera() {
        LatLng hassan = new LatLng(13.0068, 76.1004); // Coordinates for Hassan
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hassan));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10)); // Set zoom level

    }

    // Customizing a marker for Chikkamagaluru with a green color
    private void customizeMarkers() {
        LatLng chikkamagaluru = new LatLng(13.3161, 75.7750); // Coordinates for Chikkamagaluru
        mMap.addMarker(new MarkerOptions()
                .position(chikkamagaluru)
                .title("Chikkamagaluru")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); // Custom color

            // Bangalore
            LatLng bangalore = new LatLng(12.9716, 77.5946);
            mMap.addMarker(new MarkerOptions()
                    .position(bangalore)
                    .title("Bangalore")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            // Mysore
            LatLng mysore = new LatLng(12.2958, 76.6394);
            mMap.addMarker(new MarkerOptions()
                    .position(mysore)
                    .title("Mysore")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

            // Hubli
            LatLng hubli = new LatLng(15.3647, 75.1239);
            mMap.addMarker(new MarkerOptions()
                    .position(hubli)
                    .title("Hubli")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            // Mangalore
            LatLng mangalore = new LatLng(12.9141, 74.8560);
            mMap.addMarker(new MarkerOptions()
                    .position(mangalore)
                    .title("Mangalore")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

            // Belgaum
            LatLng belgaum = new LatLng(15.8497, 74.4977);
            mMap.addMarker(new MarkerOptions()
                    .position(belgaum)
                    .title("Belgaum")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


            // Bijapur (Vijayapura)
            LatLng bijapur = new LatLng(16.8302, 75.7100);
            mMap.addMarker(new MarkerOptions()
                    .position(bijapur)
                    .title("Bijapur (Vijayapura)")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

            // Gulbarga (Kalaburagi)
            LatLng gulbarga = new LatLng(17.3297, 76.8343);
            mMap.addMarker(new MarkerOptions()
                    .position(gulbarga)
                    .title("Gulbarga (Kalaburagi)")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

            // Udupi
            LatLng udupi = new LatLng(13.3409, 74.7421);
            mMap.addMarker(new MarkerOptions()
                    .position(udupi)
                    .title("Udupi")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

            // Shimoga (Shivamogga)
            LatLng shimoga = new LatLng(13.9299, 75.5681);
            mMap.addMarker(new MarkerOptions()
                    .position(shimoga)
                    .title("Shimoga (Shivamogga)")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


    }

    // Handling marker click events
    private void handleMarkerClickEvents() {
        mMap.setOnMarkerClickListener(marker -> {
            // Show a Toast when the marker is clicked
            Toast.makeText(this, "Marker clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
            return false; // Return false to indicate we have not consumed the event
        });
    }
}
