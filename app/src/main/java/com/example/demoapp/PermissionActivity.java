//package com.example.demoapp;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//public class PermissionActivity extends AppCompatActivity {
//
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
//    private GoogleMap googleMap;
//    private MapView mapView;
//    private TextView coordinatesTextView;
//    private FusedLocationProviderClient fusedLocationClient;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_permission);
//        mapView = findViewById(R.id.mapView);
//        coordinatesTextView = findViewById(R.id.coordinatesTextView);
//
//        // Initialize location client
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Initialize the map
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume(); // Needed to display the map immediately
//
//        MapsInitializer.initialize(getApplicationContext());
//
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap map) {
//                googleMap = map;
//                if (checkLocationPermission()) {
//                    // Permission granted, get the last known location
//                    getLastKnownLocation();
//                } else {
//                    // Request location permission
//                    requestLocationPermission();
//                }
//            }
//        });
//    }
//
//    private boolean checkLocationPermission() {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestLocationPermission() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                LOCATION_PERMISSION_REQUEST_CODE);
//    }
//
//    private void getLastKnownLocation() {
//        if (checkLocationPermission()) {
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(location -> {
//                        if (location != null) {
//                            // Update map with the last known location
//                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            googleMap.addMarker(new MarkerOptions().position(latLng).title("You are here"));
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//                            // Update the TextView with coordinates
//                            coordinatesTextView.setText("Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
//                        }
//                    });
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, get the last known location
//                getLastKnownLocation();
//            } else {
//                // Permission denied
//                Toast.makeText(this, "Location permission is required for this feature", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
////    @Override
////    protected void onLowMemory() {
////        super.onLowMemory();
////        mapView.onLowMemory();
////    }
//}


//package com.example.demoapp;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class PermissionActivity extends AppCompatActivity {
//
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
//    private static final int LOCATION_SETTINGS_REQUEST_CODE = 1001;
//
//    private GoogleMap googleMap;
//    private MapView mapView;
//    private TextView coordinatesTextView;
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationManager locationManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_permission);
//
//        mapView = findViewById(R.id.mapView);
////        coordinatesTextView = findViewById(R.id.coordinatesTextView);
//
//        // Initialize location client and manager
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        // Initialize the map
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume(); // Needed to display the map immediately
//        MapsInitializer.initialize(getApplicationContext());
//
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap map) {
//                googleMap = map;
//                if (checkLocationServices()) {
//                    // Check for permission
//                    if (checkLocationPermission()) {
//                        getLastKnownLocation();
//                    } else {
//                        requestLocationPermission();
//                    }
//                } else {
//                    showLocationServicesDialog();
//                }
//            }
//        });
//    }
//
//    // Check if GPS or Network location is enabled
//    private boolean checkLocationServices() {
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//    // Show a dialog to ask the user to enable location services
//    private void showLocationServicesDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Enable Location Services")
//                .setMessage("Location services are disabled. Please enable them to continue.")
//                .setCancelable(false)
//                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Open the location settings
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(intent, LOCATION_SETTINGS_REQUEST_CODE);
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .show();
//    }
//
//    private boolean checkLocationPermission() {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestLocationPermission() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                LOCATION_PERMISSION_REQUEST_CODE);
//    }
//
////    private void getLastKnownLocation() {
////        if (checkLocationPermission()) {
////            fusedLocationClient.getLastLocation()
////                    .addOnSuccessListener(location -> {
////                        if (location != null) {
////                            // Update map with the last known location
////                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
////                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
////                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
////
////                            // Update the TextView with coordinates
//////                            coordinatesTextView.setText("Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
////                        }
////                    });
////        }
////    }
//private void getLastKnownLocation() {
//    if (checkLocationPermission()) {
//        fusedLocationClient.getLocationAvailability()
//                .addOnSuccessListener(locationAvailability -> {
//                    if (locationAvailability.isLocationAvailable()) {
//                        fusedLocationClient.getLastLocation()
//                                .addOnSuccessListener(location -> {
//                                    if (location != null) {
//                                        updateLocationOnMap(location);
//                                    } else {
//                                        // If no location is returned, request location updates
//                                        requestLocationUpdates();
//                                    }
//                                });
//                    } else {
//                        // Request location updates if location is not available
//                        requestLocationUpdates();
//                    }
//                });
//    }
//}
//
//    private void requestLocationUpdates() {
//        if (checkLocationPermission()) {
//            fusedLocationClient.requestLocationUpdates(LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
//                    new LocationCallback() {
//                        @Override
//                        public void onLocationResult(LocationResult locationResult) {
//                            if (locationResult != null) {
//                                Location location = locationResult.getLastLocation();
//                                if (location != null) {
//                                    updateLocationOnMap(location);
//                                    // Optionally, stop location updates once a location is found
//                                    fusedLocationClient.removeLocationUpdates(this);
//                                }
//                            }
//                        }
//                    }, getMainLooper());
//        }
//    }
//
//    private void updateLocationOnMap(Location location) {
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//
//        // Update the TextView with coordinates if needed
//        // coordinatesTextView.setText("Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastKnownLocation();
//            } else {
//                Toast.makeText(this, "Location permission is required for this feature", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Handle the result of the location settings request
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
//            // Check again if location services are enabled
//            if (checkLocationServices()) {
//                if (checkLocationPermission()) {
//                    getLastKnownLocation();
//                } else {
//                    requestLocationPermission();
//                }
//            } else {
//                Toast.makeText(this, "Location services are still disabled", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//}

package com.example.demoapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PermissionActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final int LOCATION_SETTINGS_REQUEST_CODE = 1001;

    private GoogleMap googleMap;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        mapView = findViewById(R.id.mapView);

        // Initialize location client and manager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Initialize the map
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // Needed to display the map immediately
        MapsInitializer.initialize(getApplicationContext());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                if (checkLocationServices()) {
                    // Check for permission
                    if (checkLocationPermission()) {
                        startLocationUpdates(); // Start location updates
                    } else {
                        requestLocationPermission();
                    }
                } else {
                    showLocationServicesDialog();
                }
            }
        });

        // Setup the LocationCallback to receive updates
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                // Handle location result
                LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        };

        // Configure location request settings
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);  // Set update interval to 10 seconds
        locationRequest.setFastestInterval(5000);  // Fastest update interval at 5 seconds
    }

    // Start requesting location updates
    private void startLocationUpdates() {
        if (checkLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Check if GPS or Network location is enabled
    private boolean checkLocationServices() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // Show a dialog to ask the user to enable location services
    private void showLocationServicesDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enable Location Services")
                .setMessage("Location services are disabled. Please enable them to continue.")
                .setCancelable(false)
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Open the location settings
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, LOCATION_SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates(); // Start location updates after permission is granted
            } else {
                Toast.makeText(this, "Location permission is required for this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
            if (checkLocationServices()) {
                if (checkLocationPermission()) {
                    startLocationUpdates(); // Start location updates when location services are enabled
                } else {
                    requestLocationPermission();
                }
            } else {
                Toast.makeText(this, "Location services are still disabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        // Recheck if location services are enabled and permissions are granted when resuming
        if (checkLocationServices()) {
            if (checkLocationPermission()) {
                startLocationUpdates();
            }
        } else {
            showLocationServicesDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        // Stop location updates to save battery when not needed
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
