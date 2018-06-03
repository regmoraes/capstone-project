package com.regmoraes.closer.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.regmoraes.closer.R;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class NotificationUtils {

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
//    private static void sendNotification(Context context, String notificationDetails) {
//        // Get an instance of the Notification manager
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Android O requires a Notification Channel.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = context.getString(R.string.app_name);
//            // Create the channel for the notification
//            NotificationChannel mChannel =
//                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
//
//            // Set the Notification Channel for the Notification Manager.
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
//
//        // Create an explicit content Intent that starts the main Activity.
//        Intent notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
//
//        // Construct a task stack.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//
//        // Add the main Activity to the task stack as the parent.
//        stackBuilder.addParentStack(MainActivity.class);
//
//        // Push the content Intent onto the stack.
//        stackBuilder.addNextIntent(notificationIntent);
//
//        // Get a PendingIntent containing the entire back stack.
//        PendingIntent notificationPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Get a notification builder that's compatible with platform versions >= 4
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//        // Define the notification settings.
//        builder.setSmallIcon(R.drawable.ic_launcher)
//                // In a real app, you may want to use a library like Volley
//                // to decode the Bitmap.
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                        R.drawable.ic_launcher))
//                .setColor(Color.RED)
//                .setContentTitle(notificationDetails)
//                .setContentText(getString(R.string.geofence_transition_notification_text))
//                .setContentIntent(notificationPendingIntent);
//
//        // Set the Channel ID for Android O.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder.setChannelId(CHANNEL_ID); // Channel ID
//        }
//
//        // Dismiss notification once the user touches it.
//        builder.setAutoCancel(true);
//
//        // Issue the notification
//        mNotificationManager.notify(0, builder.build());
//    }
}
