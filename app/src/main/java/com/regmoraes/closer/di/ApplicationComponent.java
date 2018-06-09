package com.regmoraes.closer.di;

import com.regmoraes.closer.presentation.addreminder.AddReminderActivity;
import com.regmoraes.closer.presentation.reminders.RemindersActivity;
import com.regmoraes.closer.services.DoneReminderReceiver;
import com.regmoraes.closer.services.GeofenceTransitionsIntentService;

import javax.inject.Singleton;

import dagger.Component;

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = { AndroidModule.class, DatabaseModule.class })
public interface ApplicationComponent {

    void inject(RemindersActivity remindersActivity);
    void inject(AddReminderActivity addReminderActivity);
    void inject(GeofenceTransitionsIntentService geofenceTransitionsIntentService);
    void inject(DoneReminderReceiver doneReminderReceiver);
}