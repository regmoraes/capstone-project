package com.regmoraes.closer.di;

import com.regmoraes.closer.presentation.addreminder.AddReminderActivity;
import com.regmoraes.closer.presentation.reminderdetail.ReminderDetailActivity;
import com.regmoraes.closer.presentation.reminders.RemindersActivity;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 *   Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@PresentationScope
@Subcomponent
public interface PresentationComponent {

    void inject(RemindersActivity remindersActivity);
    void inject(AddReminderActivity addReminderActivity);
    void inject(ReminderDetailActivity reminderDetailActivity);
}