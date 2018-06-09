package com.regmoraes.closer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Dao
public interface RemindersRepository {

    @Query("SELECT * FROM reminders")
    Flowable<List<Reminder>> getReminders();

    @Query("SELECT * FROM reminders WHERE uid = :uid")
    Single<Reminder> getReminderById(long uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Delete
    void delete(Reminder reminder);
}