package com.regmoraes.closer.presentation;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.databinding.ActivityRemindersBinding;
import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class RemindersActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,RemindersItemAdapter.AdapterClickListener {

    private ActivityRemindersBinding viewBinding;
    private RemindersItemAdapter remindersItemAdapter;

    @Inject
    public RemindersManager remindersManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();

        loadData();
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector().inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminders);

        viewBinding.fab.setOnClickListener(__-> {

            Intent addReminder =
                    new Intent(RemindersActivity.this, AddReminderActivity.class);

            startActivity(addReminder);
        });

        setSupportActionBar(viewBinding.included.toolbar);

        DividerItemDecoration divider =
                new DividerItemDecoration(
                        viewBinding.recyclerViewReminders.getContext(),
                        HORIZONTAL
                );

        remindersItemAdapter = new RemindersItemAdapter(this);

        viewBinding.recyclerViewReminders.addItemDecoration(divider);
        viewBinding.recyclerViewReminders.setAdapter(remindersItemAdapter);
    }

    private void loadData() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return remindersManager.getRemindersCursorLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        remindersItemAdapter.setData(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        remindersItemAdapter.setData(null);
    }

    @Override
    public void onReminderClicked(long reminderId) {

    }
}
