package com.regmoraes.closer.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.regmoraes.closer.data.database.CloserDatabase;
import com.regmoraes.closer.data.database.RemindersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public class DatabaseModule {

    @Provides
    public CloserDatabase providesDatabase(Context context) {
        return Room.databaseBuilder(context, CloserDatabase.class, "closer").build();
    }

    @Provides
    @Singleton
    public RemindersRepository providesRemindersRepository(CloserDatabase closerDatabase) {
        return closerDatabase.getReminderRepository();
    }
}
