//package com.regmoraes.closer.services;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//
//import com.regmoraes.closer.CloserApp;
//import com.regmoraes.closer.di.ApplicationComponent;
//
///**
// * Copyright {2018} {Rômulo Eduardo G. Moraes}
// **/
//public class GeofencesBuilderIntentService extends IntentService {
//
//    private ApplicationComponent components;
//
//    public GeofencesBuilderIntentService() {
//        super(GeofencesBuilderIntentService.class.getSimpleName() + " created");
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        components = ((CloserApp) getApplication()).getComponentsInjector().inject();
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
//    }
//}
