package com.regmoraes.closer.presentation.reminders;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

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
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class RemindersActivity extends AppCompatActivity implements
        RemindersItemAdapter.AdapterClickListener, RemindersViewModel.Observer {

    private static final int PERMISSION_REQUEST_LOCATION = 0;

    private ActivityRemindersBinding viewBinding;
    private RemindersViewModel viewModel;
    private RemindersItemAdapter remindersItemAdapter;

    @Inject
    public RemindersViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminders);
        setSupportActionBar(viewBinding.appBar.toolbar);

        setUpView();

        checkLocationPermission();
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector()
                .presentationComponent()
                .inject(this);
    }

    private void setUpView() {

        DividerItemDecoration divider = new DividerItemDecoration(
                viewBinding.recyclerViewReminders.getContext(), VERTICAL);

        remindersItemAdapter = new RemindersItemAdapter(this);

        viewBinding.recyclerViewReminders.addItemDecoration(divider);
        viewBinding.recyclerViewReminders.setAdapter(remindersItemAdapter);

        setUpViewModel();
    }


    void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RemindersViewModel.class);

        viewModel.getReminders().observe(this, this::handleReminders);

        viewModel.loadReminders();
    }

    @Override
    public void handleReminders(List<Reminder> reminders) {

        if(reminders != null && !reminders.isEmpty()) {
            remindersItemAdapter.setData(reminders);
            viewBinding.recyclerViewReminders.setVisibility(View.VISIBLE);
            viewBinding.textViewNoReminders.setVisibility(View.INVISIBLE);

        } else {
            viewBinding.recyclerViewReminders.setVisibility(View.INVISIBLE);
            viewBinding.textViewNoReminders.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReminderClicked(Reminder reminder) {

        Intent reminderDetail = new Intent(this, ReminderDetailActivity.class);
        reminderDetail.putExtra(ReminderData.class.getSimpleName(),
                ReminderData.fromReminder(reminder));

        startActivity(reminderDetail);
    }

    private void checkLocationPermission() {

        boolean locationPermissionGranted = (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        setFabListenerAccordingToLocationPermission(locationPermissionGranted);

        if(!locationPermissionGranted) {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(viewBinding.getRoot(), R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, __-> ActivityCompat.requestPermissions(this,
                            new String[]{ Manifest.permission.ACCESS_FINE_LOCATION },
                            PERMISSION_REQUEST_LOCATION)
                    )
                    .show();

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_LOCATION) {

            boolean locationPermissionGranted = (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED);

            setFabListenerAccordingToLocationPermission(locationPermissionGranted);
        }
    }

    private void setFabListenerAccordingToLocationPermission(boolean locationPermissionGranted) {

        if(locationPermissionGranted) {
            viewBinding.fab.setOnClickListener(locationPermissionGrantedFabListener);

        } else {
            viewBinding.fab.setOnClickListener(locationPermissionDeniedFabListener);
        }
    }

    private FloatingActionButton.OnClickListener locationPermissionDeniedFabListener = __-> {
        requestLocationPermission();
    };

    private FloatingActionButton.OnClickListener locationPermissionGrantedFabListener = __-> {

        Intent addReminder = new Intent(RemindersActivity.this,
                AddReminderActivity.class);

        startActivity(addReminder);
    };
}
