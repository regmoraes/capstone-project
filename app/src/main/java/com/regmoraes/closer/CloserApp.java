package com.regmoraes.closer;

import android.app.Application;

import com.regmoraes.closer.di.AndroidModule;
import com.regmoraes.closer.di.ApplicationComponent;
import com.regmoraes.closer.di.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class CloserApp extends Application {

    private ApplicationComponent components;

    @Override
    public void onCreate() {
        super.onCreate();

        components = DaggerApplicationComponent.builder()
                        .androidModule(new AndroidModule(this)).build();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ApplicationComponent getComponentsInjector() {
        return components;
    }
}
