package com.regmoraes.closer.presentation.reminders;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.domain.RemindersManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class RemindersViewModel extends ViewModel {

    private RemindersManager remindersManager;
    private MutableLiveData<List<Reminder>> reminders;
    private CompositeDisposable compositeDisposable;

    @Inject
    public RemindersViewModel(RemindersManager remindersManager) {
        this.remindersManager = remindersManager;
        this.reminders = new MutableLiveData<>();
        this.compositeDisposable = new CompositeDisposable();
    }

    void loadReminders() {

        compositeDisposable.add(
                remindersManager.getReminders()
                        .compose(SchedulerTransformers.applyFlowableBaseScheduler())
                        .subscribe( reminders -> this.reminders.postValue(reminders))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    LiveData<List<Reminder>> getReminders() {
        return reminders;
    }

    interface Observer {
        void handleReminders(List<Reminder> reminders);
    }
}
