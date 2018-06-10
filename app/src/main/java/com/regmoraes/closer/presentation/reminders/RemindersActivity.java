package com.regmoraes.closer.presentation.reminders;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.data.Reminder;
import com.regmoraes.closer.databinding.ActivityRemindersBinding;
import com.regmoraes.closer.presentation.addreminder.AddReminderActivity;
import com.regmoraes.closer.presentation.addreminder.ReminderData;
import com.regmoraes.closer.presentation.reminderdetail.ReminderDetailActivity;

import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class RemindersActivity extends AppCompatActivity implements
        RemindersItemAdapter.AdapterClickListener, RemindersViewModel.Observer {

    private ActivityRemindersBinding viewBinding;
    private RemindersViewModel viewModel;
    private RemindersItemAdapter remindersItemAdapter;

    @Inject
    public RemindersViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();

        setUpViewModel();
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector()
                .presentationComponent()
                .inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminders);

        viewBinding.fab.setOnClickListener(__-> {

            Intent addReminder =
                    new Intent(RemindersActivity.this, AddReminderActivity.class);

            startActivity(addReminder);
        });

        setSupportActionBar(viewBinding.appBar.toolbar);

        DividerItemDecoration divider =
                new DividerItemDecoration(
                        viewBinding.recyclerViewReminders.getContext(),
                        HORIZONTAL
                );

        remindersItemAdapter = new RemindersItemAdapter(this);

        viewBinding.recyclerViewReminders.addItemDecoration(divider);
        viewBinding.recyclerViewReminders.setAdapter(remindersItemAdapter);
    }

    void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RemindersViewModel.class);

        viewModel.getReminders().observe(this, this::handleReminders);

        viewModel.loadReminders();
    }

    @Override
    public void handleReminders(List<Reminder> reminders) {
        remindersItemAdapter.setData(reminders);
    }

    @Override
    public void onReminderClicked(Reminder reminder) {

        Intent reminderDetail = new Intent(this, ReminderDetailActivity.class);
        reminderDetail.putExtra(ReminderData.class.getSimpleName(),
                ReminderData.fromReminder(reminder));

        startActivity(reminderDetail);
    }
}
