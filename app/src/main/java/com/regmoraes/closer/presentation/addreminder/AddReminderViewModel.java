package com.regmoraes.closer.presentation.addreminder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.domain.RemindersManager;
import com.regmoraes.closer.presentation.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class AddReminderViewModel extends ViewModel {

    private RemindersManager remindersManager;
    private SingleLiveEvent<Void> reminderAddedEvent;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AddReminderViewModel(RemindersManager remindersManager) {
        this.remindersManager = remindersManager;
        this.reminderAddedEvent = new SingleLiveEvent<>();
        this.compositeDisposable = new CompositeDisposable();
    }

    void insertReminder(ReminderData reminderData) {

        Reminder reminder = ReminderData.createReminder(reminderData);

        compositeDisposable.add(
                remindersManager.insertReminder(reminder)
                        .compose(SchedulerTransformers.applyCompletableBaseScheduler())
                        .subscribe(() -> reminderAddedEvent.call())
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    LiveData<Void> getReminderAddedEvent() {
        return reminderAddedEvent;
    }

    interface Observer {
        void handleReminderAddedEvent(Void aVoid);
    }
}
