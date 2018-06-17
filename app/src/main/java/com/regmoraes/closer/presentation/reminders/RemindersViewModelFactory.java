package com.regmoraes.closer.presentation.reminders;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class RemindersViewModelFactory implements ViewModelProvider.Factory {

    private RemindersManager remindersManager;

    @Inject
    public RemindersViewModelFactory(RemindersManager remindersManager) {
        this.remindersManager = remindersManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(RemindersViewModel.class)) {

            Timber.d("New ViewModel created");

            //noinspection unchecked
            return (T) new RemindersViewModel(remindersManager);

        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getSimpleName());
        }
    }
}
