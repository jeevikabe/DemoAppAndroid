package com.example.demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demoapp.domains.LocationResponse;
import com.example.demoapp.interfaces.APIResponseListener;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import networkapi.APIServer;
import networkapi.Links;
public class ApiMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private Handler handler = new Handler();
    private final long FETCH_INTERVAL = 5000;

    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView addressTextView;
    private LocationRequest locationRequest;
    private Geocoder geocoder;
    private SearchView searchView;
    private String responseData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_map);
        mapView = findViewById(R.id.mapView);
        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        addressTextView = findViewById(R.id.addressTextView); // Added for displaying address
        searchView = findViewById(R.id.searchView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        createLocationRequest();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            checkLocationSettings();
        }

        Intent serviceIntent = new Intent(this, MapService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        // Set up SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When search is submitted
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optionally handle text change
                return false;
            }
        });
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed by showing the user a dialog
                    try {
                        ((ResolvableApiException) e).startResolutionForResult(ApiMapActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        // Ignore the error
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                updateLocationInfo(latLng);
            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<Location> locationResult = fusedLocationClient.getLastLocation();
        locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                    googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                    startFetchingTruckLocations();
                    // Update TextViews with latitude and longitude
                    latitudeTextView.setText("Latitude: " + location.getLatitude());
                    longitudeTextView.setText("Longitude: " + location.getLongitude());
                } else {
                    Toast.makeText(ApiMapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    Log.d("ApiMapActivity", "Location is null");
                }
            }
        });
    }

    private void searchLocation(String query) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address address = addresses.get(0);
                LatLng locationLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                googleMap.clear(); // Clear previous markers
                googleMap.addMarker(new MarkerOptions().position(locationLatLng).title(query));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));

                latitudeTextView.setText("Latitude: " + address.getLatitude());
                longitudeTextView.setText("Longitude: " + address.getLongitude());
                addressTextView.setText("Address: " + address.getAddressLine(0)); // Display address
            } else {
                Toast.makeText(this, "No results found for: " + query, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder service not available", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettings();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            } else {
                Toast.makeText(this, "Location settings were not satisfied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateLocationInfo(LatLng latLng) {
        latitudeTextView.setText("Latitude: " + latLng.latitude);
        longitudeTextView.setText("Longitude: " + latLng.longitude);
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address address = addresses.get(0);
                addressTextView.setText("Address: " + address.getAddressLine(0));
            } else {
                addressTextView.setText("Address: Not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            addressTextView.setText("Address: Error");
        }
    }
    private void startFetchingTruckLocations() {
        handler.postDelayed(fetchTruckLocationRunnable, 0); // Start immediately
    }

    // Runnable to handle delayed execution of getTruckLocation
    private final Runnable fetchTruckLocationRunnable = new Runnable() {
        @Override
        public void run() {
            getTruckLocation(); // Fetch truck location
            handler.postDelayed(this, FETCH_INTERVAL); // Schedule again after interval
        }
    };



    /*private BitmapDescriptor getTruckMarkerIcon() {
        return BitmapDescriptorFactory.fromResource(R.drawable.tipper); // Load your drawable
    }*/


    private void getTruckLocation ()
    {

        new GetTruckLocationTask().execute();
    }


    private class GetTruckLocationTask extends AsyncTask<Void, Void, List<LocationResponse>> {
        @Override
        protected List<LocationResponse> doInBackground(Void... params) {
            String url = Links.GET_VEHICLE_LOCATION;

            // This method will be called asynchronously, but it won't return data as you're expecting
            new APIServer(ApiMapActivity.this).getString(url, new APIResponseListener() {
                @Override
                public void onResponse(int statusCode, String response) {
                    if (response != null && !response.isEmpty()) {
                        responseData = response;
                    } else {
                        responseData = null; // or set a default value
                    }
                }
            });

            // Make sure to wait for the response or adjust the implementation to handle it properly
            return parseTruckLocationResponse(responseData);
        }

        @Override
        protected void onPostExecute(List<LocationResponse> truckLocations) {
            if (truckLocations != null && !truckLocations.isEmpty()) {
                // Clear existing markers
                googleMap.clear();

                for (LocationResponse location : truckLocations) {
                    LatLng latLng = parseLocation(location.getLocation());

                    // Create and add a new marker
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Truck Location")
                            .icon(getTruckMarkerIcon()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    // Break if you only want the first location (or handle accordingly)
                    break;
                }
            }
        }

        private BitmapDescriptor getTruckMarkerIcon() {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.tipper);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 40, 100, false);
            return BitmapDescriptorFactory.fromBitmap(scaledBitmap);
        }
    }


    private List<LocationResponse> parseTruckLocationResponse(String response) {
        if (response == null || response.isEmpty()) {
            return new ArrayList<>(); // Return an empty list to avoid null pointer exceptions
        }

        Gson gson = new Gson();
        List<LocationResponse> truckLocations = new ArrayList<>();
        try {
            // Parse the JSON array directly
            JSONArray jsonArray = new JSONArray(response);

            Type listType = new TypeToken<List<LocationResponse>>() {}.getType();
            truckLocations = gson.fromJson(jsonArray.toString(), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return truckLocations;
    }

    private LatLng parseLocation (String location)
    {
        try
        {
            String[] parts = location.split(",");
            double lat = Double.parseDouble(parts[0].trim());
            double lng = Double.parseDouble(parts[1].trim());
            return new LatLng(lat, lng);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
