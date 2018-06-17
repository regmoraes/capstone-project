package com.regmoraes.closer.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Implementation of App Widget functionality.
 */
public class RemindersWidgetViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemindersRemoteViewFactory(getApplicationContext());
    }
}


