package com.regmoraes.closer.domain;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.regmoraes.closer.data.database.ReminderContract;
import com.regmoraes.closer.data.entity.Reminder;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RemindersManager {

    private ContentResolver mContentResolver;

    @Inject
    public RemindersManager(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;

    }

    public CursorLoader getRemindersCursorLoader(Context context) {

        Uri remindersUri = ReminderContract.BASE_URI_CONTENT;

        return new CursorLoader(context, remindersUri, ReminderContract.Query.PROJECTION, null,
                null, null);

//        Cursor cursor = mContentResolver.query(ReminderContract.ReminderEntry.CONTENT_URI,
//                null, null, null, null);
//
//        List<Reminder> reminders = new ArrayList<>();
//
//        if(cursor != null && cursor.getCount() > 0) {
//
//            cursor.moveToFirst();
//
//            while (!cursor.isAfterLast()) {
//
//                Reminder reminder = new Reminder(
//                        cursor.getInt(cursor.getColumnIndex(ReminderContract.ReminderEntry._ID)),
//                        cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_TITLE)),
//                        cursor.getString(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_DESCRIPTION)),
//                        cursor.getDouble(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_LAT)),
//                        cursor.getDouble(cursor.getColumnIndex(ReminderContract.ReminderEntry.COLUMN_NAME_LNG))
//                );
//
//                reminders.add(reminder);
//                cursor.moveToNext();
//            }
//
//            cursor.close();
//        }
//
//        return cursor;
    }

    public long insertReminder(Reminder reminder) {

        final ContentValues mValues = new ContentValues();
            mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_TITLE, reminder.getTitle());
            mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_DESCRIPTION, reminder.getDescription());
            mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_LAT, reminder.getLat());
            mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_LNG, reminder.getLng());

            Uri mUri = mContentResolver.insert(ReminderContract.ReminderEntry.CONTENT_URI, mValues);

            if(mUri != null) {
                return Integer.valueOf(mUri.getLastPathSegment());
            } else {
                return -1L;
            }
    }

    public int updateReminder(Reminder reminder) {

        final ContentValues mValues = new ContentValues();
        mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_TITLE, reminder.getTitle());
        mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_DESCRIPTION, reminder.getDescription());
        mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_LAT, reminder.getLat());
        mValues.put(ReminderContract.ReminderEntry.COLUMN_NAME_LNG, reminder.getLng());

        Uri mUri = ReminderContract.ReminderEntry.CONTENT_URI
                .buildUpon().appendPath(String.valueOf(reminder.getId())).build();

        return mContentResolver.update(mUri, mValues, null, null);
    }

    public int deleteReminder(Integer reminderId) {

        final Uri uri = ReminderContract.ReminderEntry.CONTENT_URI
                .buildUpon().appendPath(String.valueOf(reminderId)).build();

        return mContentResolver.delete(uri, null, null);
    }
}
