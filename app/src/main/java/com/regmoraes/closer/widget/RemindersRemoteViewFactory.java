package com.regmoraes.closer.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.domain.RemindersManager;
import com.regmoraes.closer.presentation.addreminder.ReminderData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Implementation of App Widget functionality.
 */
public class RemindersRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Reminder> reminders;
    private CompositeDisposable disposables;

    @Inject
    RemindersManager remindersManager;

    public RemindersRemoteViewFactory(Context applicationContext) {
        context = applicationContext;
        disposables = new CompositeDisposable();
    }

    @Override
    public void onCreate() {

        ((CloserApp) context.getApplicationContext()).getComponentsInjector()
                .inject(this);
    }

    @Override
    public void onDataSetChanged() {

        disposables.add(remindersManager.getReminders()
                .subscribe(reminders -> this.reminders = reminders));
    }

    @Override
    public int getCount() {
        return reminders == null ? 0 : reminders.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || reminders == null ||
                reminders.isEmpty()) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_reminder_item);

        Reminder reminder = reminders.get(position);

        views.setTextViewText(R.id.textView_title, reminder.getTitle());
        views.setTextViewText(R.id.textView_location, reminder.getLocationName());

        Intent fillIntent = new Intent();
        fillIntent.putExtra(ReminderData.class.getSimpleName(), ReminderData.fromReminder(reminder));

        views.setOnClickFillInIntent(R.id.relativeLayout_reminder, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}


