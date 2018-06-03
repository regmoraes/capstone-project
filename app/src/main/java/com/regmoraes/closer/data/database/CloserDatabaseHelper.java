package com.regmoraes.closer.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class CloserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "closer.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            ReminderContract.ReminderEntry.TABLE_NAME + " (" +
            ReminderContract.ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
            ReminderContract.ReminderEntry.COLUMN_NAME_TITLE + " INTEGER NOT NULL," +
            ReminderContract.ReminderEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL," +
            ReminderContract.ReminderEntry.COLUMN_LOCATION_NAME + " TEXT NOT NULL," +
            ReminderContract.ReminderEntry.COLUMN_NAME_LAT + " TEXT NOT NULL," +
            ReminderContract.ReminderEntry.COLUMN_NAME_LNG + " TEXT NOT NULL" +
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReminderContract.ReminderEntry.TABLE_NAME;

    public CloserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
