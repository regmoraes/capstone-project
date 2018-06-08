package com.regmoraes.closer.di;

import android.content.Context;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public class AndroidModule {

    private Context context;

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context providesApplicationContext() {
        return this.context;
    }

    @Singleton
    @Provides
    public GeofencingClient providesGeofencingClient(Context context) {

        return LocationServices.getGeofencingClient(context);
    }
}
