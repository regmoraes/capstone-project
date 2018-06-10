package com.regmoraes.closer.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.domain.GeofencesManager;
import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class DoneReminderReceiver extends BroadcastReceiver {

    public static final String ACTION_DONE = "action-done";

    @Inject
    public RemindersManager remindersManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        ((CloserApp) context.getApplicationContext())
                .getComponentsInjector().inject(this);

        if (intent.getAction() != null && intent.getAction().equals(ACTION_DONE)) {

            int reminderId = intent.getIntExtra(Reminder.REMINDER_ID, -1);

            if(reminderId >= 0) {

                remindersManager.getReminder(reminderId)
                        .compose(SchedulerTransformers.applySingleBaseScheduler())
                        .subscribe(
                                reminder -> remindersManager.deleteReminder(reminder)
                        );
            }
        }
    }


}
