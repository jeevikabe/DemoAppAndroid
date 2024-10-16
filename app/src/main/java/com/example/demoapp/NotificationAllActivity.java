package com.example.demoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
public class NotificationAllActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "channel_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_all);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel();  // Create notification channel (for Android 8.0+)

        Button btnBasic = findViewById(R.id.btnBasic);
        Button btnBigText = findViewById(R.id.btnBigText);
        Button btnBigPicture = findViewById(R.id.btnBigPicture);
        Button btnActionButton = findViewById(R.id.btnActionButton);

        btnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBasicNotification();
            }
        });

        btnBigText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBigTextStyleNotification();
            }
        });

        btnBigPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBigPictureStyleNotification();
            }
        });

        btnActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationWithActionButton();
            }
        });
    }

    private void createNotificationChannel() {
        // Create notification channel for Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "This is the channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showBasicNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Basic Notification")
                .setContentText("This is a basic notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(1, notification);
    }

    private void showBigTextStyleNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("BigTextStyle Notification")
                .setContentText("This is a longer content for BigTextStyle.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("This is a longer text that will be displayed when the notification is expanded."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(2, notification);
    }

    private void showBigPictureStyleNotification() {
        Bitmap largeImage = BitmapFactory.decodeResource(getResources(), R.drawable.img_15);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("BigPictureStyle Notification")
                .setContentText("This notification contains a large image.")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largeImage)
                        .bigLargeIcon(null))  // Hide large icon when expanded
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(3, notification);
    }

//    private void showNotificationWithActionButton() {
//        // Create an intent to trigger when the button is pressed
//        Intent intent = new Intent(this, NotificationReceiver.class);
//        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notifications)
//                .setContentTitle("Notification with Action Button")
//                .setContentText("This notification has a button.")
//                .addAction(R.drawable.baseline_notifications_none_24, "Continue", pendingIntent)  // Button with action
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .build();
//        notificationManager.notify(4, notification);
//    }

    private void showNotificationWithActionButton() {
        // Intent to redirect to NotificationAllActivity when "Continue" is clicked
        Intent continueIntent = new Intent(this, NotificationAllActivity.class);
        continueIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent continuePendingIntent = PendingIntent.getActivity(this, 0, continueIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Intent to cancel the notification
        Intent cancelIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, 1, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Create the notification with both "Continue" and "Cancel" action buttons
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Notification with Action Buttons")
                .setContentText("This notification has Continue and Cancel actions.")
                .addAction(R.drawable.baseline_notifications_none_24, "Continue", continuePendingIntent)  // Continue button
                .addAction(R.drawable.baseline_notifications_none_24, "Cancel", cancelPendingIntent)  // Cancel button
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(4, notification);
    }

}