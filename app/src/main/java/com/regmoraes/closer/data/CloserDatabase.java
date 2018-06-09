package com.regmoraes.closer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import javax.inject.Singleton;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Database(entities = {Reminder.class}, version = 1, exportSchema = false)
public abstract class CloserDatabase extends RoomDatabase {

   public abstract RemindersRepository getReminderRepository();
}
