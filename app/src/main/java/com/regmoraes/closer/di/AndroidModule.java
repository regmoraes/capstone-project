package com.regmoraes.closer.di;

import android.content.ContentResolver;
import android.content.Context;

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
    public ContentResolver providesContentResolver() {
        return this.context.getContentResolver();
    }
}
