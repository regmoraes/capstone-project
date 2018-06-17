package com.regmoraes.closer.domain;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.background.GeofenceTransitionsIntentService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class GeofencesManager {

    private Context context;
    private GeofencingClient geofencingClient;
    private PendingIntent pendingIntent;

    @Inject
    public GeofencesManager(Context context,
                            GeofencingClient geofencingClient) {

        this.context = context;
        this.geofencingClient = geofencingClient;
    }

    // Permissions handled in Activities
    @SuppressLint("MissingPermission")
    public void createGeofenceForReminder(Reminder reminder) {

        Geofence geofence = buildGeofenceForReminder(reminder);

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();

        geofencingClient.addGeofences(request, getGeofencingPendingIntent());
    }

    // Permissions handled in Activities
    @SuppressLint("MissingPermission")
    public void createGeofenceForReminders(List<Reminder> reminders) {

        List<Geofence> geofences = new ArrayList<>();

        for(Reminder reminder : reminders) {
            geofences.add(buildGeofenceForReminder(reminder));
        }

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofences)
                .build();

        geofencingClient.addGeofences(request, getGeofencingPendingIntent());
    }

    private Geofence buildGeofenceForReminder(Reminder reminder) {

        return new Geofence.Builder()
                .setRequestId(String.valueOf(reminder.getUid()))
                .setCircularRegion(
                        reminder.getLatitude(),
                        reminder.getLongitude(),
                        100
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();
    }

    public void deleteGeofence(String reminderId) {

        List<String> geofencesIds = new ArrayList<>();
        geofencesIds.add(reminderId);

        geofencingClient.removeGeofences(geofencesIds);
    }

    private PendingIntent getGeofencingPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }

        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }
}
