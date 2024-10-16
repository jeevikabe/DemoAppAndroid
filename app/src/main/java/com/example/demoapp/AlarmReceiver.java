package com.example.demoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "alarm_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Display a notification when the alarm is triggered
        showNotification(context);
    }

   /* private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alaram);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setSound(soundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT); // Set the sound
            notificationManager.createNotificationChannel(channel);
        }

        // Intent to open the app when the notification is clicked
        Intent intent = new Intent(context, AlaramActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Alarm Triggered")
                .setContentText("Your alarm went off!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }*/


    private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alaram);
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alaram);
            mediaPlayer.start();

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setSound(soundUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Intent to open the app when the notification is clicked
        Intent intent = new Intent(context, AlaramActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Alarm Triggered")
                //.setContentText("Your alarm went off!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Set the sound for Android versions below O
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alaram));
        }

        // Show the notification
        notificationManager.notify(0, builder.build());
    }

}
