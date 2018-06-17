package com.regmoraes.closer.presentation.reminderdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.closer.background.SchedulerTransformers;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.domain.RemindersManager;
import com.regmoraes.closer.presentation.SingleLiveEvent;
import com.regmoraes.closer.presentation.addreminder.ReminderData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class ReminderDetailViewModel extends ViewModel {

    private RemindersManager remindersManager;
    private SingleLiveEvent<Void> reminderDoneEvent;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ReminderDetailViewModel(RemindersManager remindersManager) {
        this.remindersManager = remindersManager;
        this.reminderDoneEvent = new SingleLiveEvent<>();
        this.compositeDisposable = new CompositeDisposable();
    }

    void deleteReminder(ReminderData reminderData) {

        Reminder reminder = ReminderData.createReminder(reminderData);

        compositeDisposable.add(
                remindersManager.deleteReminder(reminder)
                        .compose(SchedulerTransformers.applyCompletableBaseScheduler())
                        .subscribe(() -> reminderDoneEvent.call())
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    LiveData<Void> getReminderDeletedEvent() {
        return reminderDoneEvent;
    }

    interface Observer {
        void handleReminderDeletedEvent(Void aVoid);
    }
}
