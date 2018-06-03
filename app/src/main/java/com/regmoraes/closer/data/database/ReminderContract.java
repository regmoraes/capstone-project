package com.regmoraes.closer.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.regmoraes.closer.BuildConfig;
import com.regmoraes.closer.data.entity.Reminder;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class ReminderContract {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_URI_CONTENT = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_REMINDERS = "reminders";
    public static final String PATH_REMINDERS_WITH_ID = "reminders/#";

    private ReminderContract() {}

    public static final class ReminderEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_URI_CONTENT.buildUpon().appendPath(PATH_REMINDERS).build();

        public static final String TABLE_NAME = "reminder";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_LOCATION_NAME = "location_name";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";

        public interface Query {
            String[] PROJECTION = {
                    ReminderEntry._ID,
                    ReminderEntry.COLUMN_NAME_TITLE,
                    ReminderEntry.COLUMN_NAME_DESCRIPTION,
                    ReminderEntry.COLUMN_LOCATION_NAME,
                    ReminderEntry.COLUMN_NAME_LAT,
                    ReminderEntry.COLUMN_NAME_LNG
            };

            int _ID = 0;
            int TITLE = 1;
            int DESCRIPTION = 2;
            int LOCATION_NAME = 3;
            int LAT = 4;
            int LNG = 5;
        }
    }
}