package com.regmoraes.closer.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.regmoraes.closer.data.database.CloserDatabaseHelper;
import com.regmoraes.closer.data.database.ReminderContract;

import static com.regmoraes.closer.data.database.ReminderContract.ReminderEntry.TABLE_NAME;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class ReminderContentProvider extends ContentProvider {

    public CloserDatabaseHelper mCloserDbHelper;

    public static final int REMINDERS = 100;
    public static final int REMINDER_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ReminderContract.AUTHORITY, ReminderContract.PATH_REMINDERS, REMINDERS);
        uriMatcher.addURI(ReminderContract.AUTHORITY, ReminderContract.PATH_REMINDERS_WITH_ID, REMINDER_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mCloserDbHelper = new CloserDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase mDb = mCloserDbHelper.getReadableDatabase();
        Cursor mCursor;

        switch (sUriMatcher.match(uri)) {

            case REMINDERS: {

                mCursor = mDb.query(TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                mCursor.setNotificationUri(getContext().getContentResolver(), uri);

                getContext().getContentResolver().notifyChange(uri, null);

                return mCursor;
            }

            case REMINDER_WITH_ID: {

                final String id = uri.getLastPathSegment();

                mCursor = mDb.query(
                        ReminderContract.ReminderEntry.TABLE_NAME, null,"_id = ?",
                        new String[]{id}, null, null, null);

                mCursor.setNotificationUri(getContext().getContentResolver(), uri);

                getContext().getContentResolver().notifyChange(uri, null);

                return mCursor;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if(sUriMatcher.match(uri) == REMINDERS) {

            final SQLiteDatabase mDb = mCloserDbHelper.getWritableDatabase();

            long _id = mDb.insert(TABLE_NAME, null, values);

            if(_id != -1) {
                return ContentUris.withAppendedId(ReminderContract.ReminderEntry.CONTENT_URI, _id);
            } else {
                throw new SQLException("Failed to insert row into " + uri);
            }

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mCloserDbHelper.getWritableDatabase();

        if(sUriMatcher.match(uri) == REMINDER_WITH_ID) {

            String id = uri.getLastPathSegment();

            int remindersDeleted = db.delete(ReminderContract.ReminderEntry.TABLE_NAME, "_id = ?", new String[]{id});

            if (remindersDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return remindersDeleted;

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mCloserDbHelper.getWritableDatabase();

        if(sUriMatcher.match(uri) == REMINDER_WITH_ID) {

            String id = uri.getLastPathSegment();

            int remindersUpdated = db.update(ReminderContract.ReminderEntry.TABLE_NAME, values,"_id = ?", new String[]{id});

            if (remindersUpdated != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return remindersUpdated;

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
