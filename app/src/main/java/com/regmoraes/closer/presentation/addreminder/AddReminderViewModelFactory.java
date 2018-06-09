package com.regmoraes.closer.presentation.addreminder;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
class AddReminderViewModelFactory implements ViewModelProvider.Factory {

    private RemindersManager remindersManager;

    @Inject
    public AddReminderViewModelFactory(RemindersManager remindersManager) {
        this.remindersManager = remindersManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(AddReminderViewModel.class)) {
            //noinspection unchecked
            return (T) new AddReminderViewModel(remindersManager);

        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getSimpleName());
        }
    }
}
