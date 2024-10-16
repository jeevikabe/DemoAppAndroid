package com.example.demoapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MapService extends Service {
    private static final String CHANNEL_ID = "LocationServiceChannel";
    private static final int LOCATION_SERVICE_NOTIF_ID = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create the notification channel
        createNotificationChannel();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                locationResult.getLocations().forEach(location -> {
                    Log.d("MapService", "Location: " + location.getLatitude() + ", " + location.getLongitude());
                });
            }
        };

        if (!hasLocationPermissions()) {
            stopSelf(); // Stop service if permissions are not granted
            return;
        }

        startLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MapService", "Service started.");

        // Intent to open the map when the notification is clicked
        Intent notificationIntent = new Intent(this, MapActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // PendingIntent with updated flags for Android 12+
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Service Running")
                .setContentText("Tap to return to the map")
                .setSmallIcon(R.drawable.icon_location) // Your custom icon
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Ensure it's high priority
                .setOngoing(true)
                .build();

        // Start the foreground service with notification
        startForeground(LOCATION_SERVICE_NOTIF_ID, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // For Android 8.0 and higher, create a notification channel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Location Service Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
                Log.d("MapService", "Notification channel created.");
            } else {
                Log.e("MapService", "Failed to create notification channel.");
            }
        }
    }

//    @SuppressLint("MissingPermission")
//    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setInterval(10000) // 10 seconds
//                .setFastestInterval(5000) // 5 seconds
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//    }
private void startLocationUpdates() {
    LocationRequest locationRequest = LocationRequest.create()
            .setInterval(10000) // Set your interval
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
}


    private boolean hasLocationPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
