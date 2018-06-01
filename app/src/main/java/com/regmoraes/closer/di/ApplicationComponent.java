package com.regmoraes.closer.di;

import com.regmoraes.closer.presentation.AddReminderActivity;
import com.regmoraes.closer.presentation.RemindersActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = { AndroidModule.class })
public interface ApplicationComponent {

    void inject(RemindersActivity remindersActivity);
    void inject(AddReminderActivity addReminderActivity);
}