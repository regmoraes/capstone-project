package com.regmoraes.closer.data.entity;

import android.database.Cursor;

import com.regmoraes.closer.data.database.ReminderContract;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class ReminderMapper {

    public static Reminder fromCursor(Cursor cursor) {

        Reminder reminder = null;

        if(cursor != null && cursor.getCount() > 0) {

            reminder = new Reminder(
                    cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_LOCATION_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_LAT)),
                    cursor.getDouble(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_LNG))
            );
        }

        return reminder;
    }
}
