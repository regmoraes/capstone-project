package com.regmoraes.closer.domain;

import android.content.ContentResolver;
import android.database.Cursor;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.regmoraes.closer.data.database.ReminderContract;
import com.regmoraes.closer.data.entity.Reminder;
import com.regmoraes.closer.data.entity.ReminderMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class GeofencesManager {

    private ContentResolver contentResolver;

    @Inject
    public GeofencesManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Geofence> getAllRemindersGeofences() {

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

    public void createGeofenceForReminder(Reminder reminder) {

        List<Geofence> geofences = new ArrayList<>();

        geofences.add(new Geofence.Builder()
                        .setRequestId(String.valueOf(reminder.getId()))
                        .setCircularRegion(
                                reminder.getLat(),
                                reminder.getLng(),
                                100
                        )
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build());

        createGeofencingRequest(geofences);
    }

    public GeofencingRequest createGeofencingRequest(List<Geofence> geofences) {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
    }
}
