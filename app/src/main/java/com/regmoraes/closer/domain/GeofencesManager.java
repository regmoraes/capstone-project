package com.regmoraes.closer.domain;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.regmoraes.closer.data.database.ReminderContract;
import com.regmoraes.closer.data.entity.Reminder;
import com.regmoraes.closer.data.entity.ReminderMapper;
import com.regmoraes.closer.services.GeofenceTransitionsIntentService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class GeofencesManager {

    private ContentResolver contentResolver;
    private Context context;
    private GeofencingClient geofencingClient;
    private PendingIntent pendingIntent;

    @Inject
    public GeofencesManager(Context context, ContentResolver contentResolver,
                            GeofencingClient geofencingClient) {
        this.context = context;
        this.contentResolver = contentResolver;
        this.geofencingClient = geofencingClient;
    }

    public void setUpGeofences() {

        List<Geofence> geofences = getAllRemindersGeofences();

        GeofencingRequest request = createGeofencingRequest(geofences);

        geofencingClient.addGeofences(request, getGeofencingPendingIntent());
    }

    private List<Geofence> getAllRemindersGeofences() {

        List<Geofence> geofences = new ArrayList<>();

        Cursor cursor = contentResolver.query(ReminderContract.ReminderEntry.CONTENT_URI, null,
                null, null, null);

        if(cursor != null) {

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Reminder reminder = ReminderMapper.fromCursor(cursor);

                geofences.add(new Geofence.Builder()
                        .setRequestId(String.valueOf(reminder.getId()))
                        .setCircularRegion(
                                reminder.getLat(),
                                reminder.getLng(),
                                100
                        )
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
                );

                cursor.moveToNext();
            }

            cursor.close();
        }

        return geofences;
    }

    public void deleteGeofence(String reminderId) {

        List<String> geofencesIds = new ArrayList<>();
        geofencesIds.add(reminderId);

        geofencingClient.removeGeofences(geofencesIds);
    }

    public Geofence createGeofenceForReminder(Reminder reminder) {

        return new Geofence.Builder()
                .setRequestId(String.valueOf(reminder.getId()))
                .setCircularRegion(
                        reminder.getLat(),
                        reminder.getLng(),
                        100
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();
    }

    public GeofencingRequest createGeofencingRequest(List<Geofence> geofences) {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
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
