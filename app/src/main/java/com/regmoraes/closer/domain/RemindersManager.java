package com.regmoraes.closer.domain;

import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.data.database.Reminder;
import com.regmoraes.closer.data.database.RemindersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class RemindersManager {

    private RemindersRepository remindersRepository;
    private GeofencesManager geofencesManager;

    @Inject
    public RemindersManager(RemindersRepository remindersRepository,
                            GeofencesManager geofencesManager) {

        this.remindersRepository = remindersRepository;
        this.geofencesManager = geofencesManager;
    }

    public void setUpReminders() {

        getReminders()
                .compose(SchedulerTransformers.applyFlowableBaseScheduler())
                .firstOrError()
                .filter(reminders -> !reminders.isEmpty())
                .subscribe(
                        reminders -> geofencesManager.createGeofenceForReminders(reminders),
                        error -> {}
                );
    }

    public Flowable<List<Reminder>> getReminders() {
        return remindersRepository.getReminders();
    }

    public Single<Reminder> getReminder(int uid) {
        return remindersRepository.getReminderById(uid);
    }

    public void insertReminder(Reminder reminder) {

        Single.fromCallable(() -> remindersRepository.insert(reminder))
                .flatMap( reminderId -> remindersRepository.getReminderById(reminderId) )
                .compose(SchedulerTransformers.applySingleBaseScheduler())
                .subscribe(newReminder -> geofencesManager.createGeofenceForReminder(newReminder));
    }

    public void updateReminder(Reminder reminder) {
        remindersRepository.insert(reminder);
    }

    public void deleteReminder(Reminder reminder) {
        remindersRepository.delete(reminder);
    }
}
