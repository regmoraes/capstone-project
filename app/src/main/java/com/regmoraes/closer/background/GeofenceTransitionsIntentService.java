package com.regmoraes.closer.background;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.domain.RemindersManager;
import com.regmoraes.closer.notification.NotificationUtils;
import com.regmoraes.closer.presentation.addreminder.ReminderData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class GeofenceTransitionsIntentService extends IntentService {

    private CompositeDisposable disposables;

    @Inject
    public RemindersManager remindersManager;

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName() + " created");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((CloserApp) getApplication()).getComponentsInjector()
                .inject(this);

        disposables = new CompositeDisposable();
    }

    protected void onHandleIntent(Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {

            Timber.e("Geofence Error: %s", String.valueOf(geofencingEvent.getErrorCode()));
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            Timber.d("Geofences: %s", triggeringGeofences.toString());

            for (Geofence geofence : triggeringGeofences) {

                disposables.add(
                        remindersManager.getReminder(Integer.valueOf(geofence.getRequestId()))
                                .compose(SchedulerTransformers.applySingleBaseScheduler())
                                .subscribe( reminder -> NotificationUtils
                                        .sendNotificationForReminder(getApplicationContext(),
                                                ReminderData.fromReminder(reminder))
                                )
                );
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
