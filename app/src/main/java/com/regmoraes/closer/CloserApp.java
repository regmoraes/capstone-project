package com.regmoraes.closer;

import android.app.Application;

import com.regmoraes.closer.di.AndroidModule;
import com.regmoraes.closer.di.ApplicationComponent;
import com.regmoraes.closer.di.DaggerApplicationComponent;

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
    }

    public ApplicationComponent getComponentsInjector() {
        return components;
    }
}
