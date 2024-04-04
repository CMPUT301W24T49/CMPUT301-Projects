package com.example.qr.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
// or for AndroidX
import androidx.core.app.NotificationCompat;

import com.example.qr.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            // Extract the notification details
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();

            // Call sendNotification to create and show the notification
            sendNotification(messageTitle, messageBody);
        }

    }

    private void sendNotification(String messageTitle, String messageBody) {
        // Create a notification builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher) // Replace with your own drawable resource
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true); // Dismisses the notification on click

        // Notification ID should be unique for each notification
        int mNotificationId = (int) System.currentTimeMillis();

        // Get the NotificationManager system service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Build and show the notification
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    public void onNewToken(String token) {
    }
}
