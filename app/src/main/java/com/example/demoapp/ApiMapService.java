package com.example.demoapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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

public class ApiMapService extends Service {
    private static final String CHANNEL_ID = "LocationServiceChannel";
    private static final long LOCATION_UPDATE_INTERVAL = 10000; // 10 seconds
    private static final long LOCATION_UPDATE_FASTEST_INTERVAL = 5000; // 5 seconds
    private static final int LOCATION_SERVICE_NOTIF_ID = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initLocationRequest();

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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( this, Manifest.permission.FOREGROUND_SERVICE)!= PackageManager.PERMISSION_GRANTED
            // ActivityCompat.checkSelfPermission( this, Manifest.permission.RECEIVE_BOOT_COMPLETED)!= PackageManager.PERMISSION_GRANTED
        )
        {
            stopSelf(); // Stop service if permissions are not granted
            return;
        }

        startLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, ApiMapActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Service Running")
                .setContentText("Tap to return to the map")
                .setSmallIcon(R.drawable.ic_location) // Ensure this drawable exists
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .build();

        startForeground(LOCATION_SERVICE_NOTIF_ID, notification);

        // Start location updates
//        startLocationUpdates();

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
        return null; // No binding is provided for this service
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Service Channel",
                    NotificationManager.IMPORTANCE_HIGH // Set high importance
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private void initLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private boolean canStartForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                    if (MapService.class.getName().equals(service.service.getClassName())) {
                        return service.foreground;
                    }
                }
            }
        }
        return true; // Fallback for pre-Android 12
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // Trigger your RestartServiceReceiver when the app is closed
        Intent broadcastIntent = new Intent(this, RestartServiceReceiver.class);
        sendBroadcast(broadcastIntent);

        // Optionally, restart the service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, MapService.class));
        } else {
            startService(new Intent(this, MapService.class));
        }

        super.onTaskRemoved(rootIntent);
    }

}

