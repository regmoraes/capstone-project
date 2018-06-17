package com.regmoraes.closer.domain;

import com.regmoraes.closer.background.SchedulerTransformers;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.data.RemindersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
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

    public Completable setUpReminders() {

        return getReminders()
                .compose(SchedulerTransformers.applyFlowableBaseScheduler())
                .firstOrError()
                .filter(reminders -> !reminders.isEmpty())
                .flatMapCompletable(reminders ->
                        Completable.fromAction(() ->
                                geofencesManager.createGeofenceForReminders(reminders)
                        )
                );
    }

    public Flowable<List<Reminder>> getReminders() {
        return remindersRepository.getReminders();
    }

    public Single<Reminder> getReminder(int uid) {
        return remindersRepository.getReminderById(uid);
    }

    public Completable insertReminder(Reminder reminder) {

        return Single.fromCallable(() -> remindersRepository.insert(reminder))
                .flatMap(reminderId -> remindersRepository.getReminderById(reminderId))
                .flatMapCompletable(newReminder ->
                        Completable.fromAction(() -> geofencesManager.createGeofenceForReminder(newReminder))
                );
    }

    public void updateReminder(Reminder reminder) {
        remindersRepository.insert(reminder);
    }

    public Completable deleteReminder(Reminder reminder) {

        return Completable.fromAction(() -> {
            remindersRepository.delete(reminder);
            geofencesManager.deleteGeofence(reminder.getUid().toString());
        });
    }
}
