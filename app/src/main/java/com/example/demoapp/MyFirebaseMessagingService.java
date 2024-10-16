package com.example.demoapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = "MyFirebaseMsgService";
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // Check if the device is in direct boot mode
//        UserManager userManager = (UserManager) getSystemService(Context.USER_SERVICE);
//        if (userManager.isUserUnlocked()) {
//            // Handle incoming message
//            Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//            if (remoteMessage.getNotification() != null) {
//                // Display notification
//                new ShowNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//            }
//        } else {
//            Log.d(TAG, "Device is in direct boot mode. Cannot handle the message now.");
//        }
//    }
//
//    @Override
//    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshed token: " + token);
//        // Optionally send this token to your server for further usage
//    }
//}
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle notification for foreground and background
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String imageUrl = remoteMessage.getNotification().getImageUrl() != null
                    ? remoteMessage.getNotification().getImageUrl().toString() : null;

            // Show notification
            if (imageUrl != null) {
                showNotificationWithImage(title, message, imageUrl);
            } else {
                showBasicNotification(title, message);
            }
        }
    }

    private void showBasicNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, builder.build());
    }

    private void showNotificationWithImage(String title, String message, String imageUrl) {
        Bitmap bitmap = getBitmapFromURL(imageUrl);

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(style)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(2, builder.build());
    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            java.net.URL url = new java.net.URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
