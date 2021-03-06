package com.regmoraes.closer.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.res.ResourcesCompat;

import com.regmoraes.closer.R;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.presentation.addreminder.ReminderData;
import com.regmoraes.closer.presentation.reminderdetail.ReminderDetailActivity;
import com.regmoraes.closer.presentation.reminders.RemindersActivity;
import com.regmoraes.closer.background.DoneReminderReceiver;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class NotificationUtils {

    private static final String CHANNEL_ID = "reminder-channel";

    public static void sendNotificationForReminder(Context context, ReminderData reminder) {

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);

            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            mNotificationManager.createNotificationChannel(mChannel);
        }

        PendingIntent reminderDetailPendingIntent =
                createTaskStack(context, createReminderDetailIntent(context, reminder))
                        .getPendingIntent(reminder.getUid(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        builder.setContentTitle(reminder.getTitle())
                .setSmallIcon(R.drawable.ic_location_on_inverse)
                .setColor(ResourcesCompat.getColor(context.getResources(),
                        R.color.colorAccent, null)
                )
                .setContentText(reminder.getLocationName())
                .setContentIntent(reminderDetailPendingIntent)
                .addAction(createDoneAction(context, reminder.getUid()))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        mNotificationManager.notify(reminder.getUid(), builder.build());
    }

    private static NotificationCompat.Action createDoneAction(Context context, int reminderId) {

        Intent doneIntent = new Intent(context, DoneReminderReceiver.class);
        doneIntent.setAction(DoneReminderReceiver.ACTION_DONE);
        doneIntent.putExtra(Reminder.REMINDER_ID, reminderId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderId,
                doneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action(R.drawable.ic_done,
                context.getString(R.string.action_done), pendingIntent);
    }

    private static Intent createReminderDetailIntent(Context context, ReminderData reminder) {

        Intent intent = new Intent(context.getApplicationContext(), ReminderDetailActivity.class);
        intent.putExtra(ReminderData.class.getSimpleName(), reminder);

        return intent;
    }

    private static TaskStackBuilder createTaskStack(Context context, Intent nextIntent) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(RemindersActivity.class);
        stackBuilder.addNextIntent(nextIntent);

        return stackBuilder;
    }
}
