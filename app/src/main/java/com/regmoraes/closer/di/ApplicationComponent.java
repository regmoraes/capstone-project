package com.regmoraes.closer.di;

import com.regmoraes.closer.background.DoneReminderReceiver;
import com.regmoraes.closer.background.GeofenceTransitionsIntentService;
import com.regmoraes.closer.widget.RemindersRemoteViewFactory;
import com.regmoraes.closer.widget.RemindersWidget;

import javax.inject.Singleton;

import dagger.Component;

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = { AndroidModule.class, DatabaseModule.class })
public interface ApplicationComponent {

    void inject(GeofenceTransitionsIntentService geofenceTransitionsIntentService);
    void inject(DoneReminderReceiver doneReminderReceiver);

    void inject(RemindersWidget remindersWidget);
    void inject(RemindersRemoteViewFactory remindersRemoteViewFactory);

    PresentationComponent presentationComponent();
}