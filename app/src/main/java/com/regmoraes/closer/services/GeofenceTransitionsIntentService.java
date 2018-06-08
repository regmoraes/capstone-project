package com.regmoraes.closer.services;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.domain.RemindersManager;
import com.regmoraes.closer.notification.NotificationUtils;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class GeofenceTransitionsIntentService extends IntentService {

    @Inject
    public RemindersManager remindersManager;

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName() + " created");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((CloserApp) getApplication()).getComponentsInjector().inject(this);
    }

    protected void onHandleIntent(Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {

            Timber.e("Geofence Error: %s", String.valueOf(geofencingEvent.getErrorCode()));
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            Timber.d("Geofences: %s", triggeringGeofences.toString());

            // Send notification and log the transition details.
            for (Geofence geofence : triggeringGeofences) {

                remindersManager.getReminder(Integer.valueOf(geofence.getRequestId()))
                        .compose(SchedulerTransformers.applySingleBaseScheduler())
                        .subscribe( reminder -> {


                            NotificationUtils
                                    .sendNotificationForReminder(getApplicationContext(), reminder);
                        });
            }
        }
    }
}
