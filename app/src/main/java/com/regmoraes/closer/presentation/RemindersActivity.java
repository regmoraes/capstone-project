package com.regmoraes.closer.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.SchedulerTransformers;
import com.regmoraes.closer.databinding.ActivityRemindersBinding;
import com.regmoraes.closer.domain.GeofencesManager;
import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class RemindersActivity extends AppCompatActivity implements
        RemindersItemAdapter.AdapterClickListener {

    private ActivityRemindersBinding viewBinding;
    private RemindersItemAdapter remindersItemAdapter;

    @Inject
    public RemindersManager remindersManager;
    @Inject
    public GeofencesManager geofencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();

        remindersManager.setUpReminders();

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

        remindersManager.getReminders()
                .compose(SchedulerTransformers.applyFlowableBaseScheduler())
                .subscribe( reminders -> remindersItemAdapter.setData(reminders) );
    }


    @Override
    public void onReminderClicked(long reminderId) {

    }
}
