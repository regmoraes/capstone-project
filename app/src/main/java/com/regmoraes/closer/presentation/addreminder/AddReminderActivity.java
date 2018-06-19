package com.regmoraes.closer.presentation.addreminder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.databinding.ActivityAddReminderBinding;
import com.regmoraes.closer.widget.RemindersWidget;

import javax.inject.Inject;

public class AddReminderActivity extends AppCompatActivity implements AddReminderViewModel.Observer {

    private int PLACE_REQUEST_CODE = 101;

    private ActivityAddReminderBinding viewBinding;
    private AddReminderViewModel viewModel;
    private ReminderData reminderData;

    @Inject
    public AddReminderViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();

        setUpViewModel();

        if(savedInstanceState != null) {
            reminderData = savedInstanceState.getParcelable(ReminderData.class.getSimpleName());

        } else {

            String reminderExtra = ReminderData.class.getSimpleName();

            if(getIntent().hasExtra(reminderExtra)) {
                reminderData = getIntent().getParcelableExtra(reminderExtra);
            }
        }

        loadReminderData(reminderData);
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector()
                .presentationComponent()
                .inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_reminder);

        setSupportActionBar(viewBinding.appBar.toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewBinding.buttonConfirm.setOnClickListener(onConfirmReminderClickListener);
        viewBinding.textViewPlace.setOnClickListener(onEditPlaceClickListener);
    }

    private void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AddReminderViewModel.class);

        viewModel.getReminderAddedEvent().observe(this, this::handleReminderAddedEvent);
    }

    private void loadReminderData(ReminderData reminderData) {

        if(reminderData == null) {
            this.reminderData = new ReminderData();

        } else {

            viewBinding.editTextTitle.setText(reminderData.getTitle());
            viewBinding.editTextDescription.setText(reminderData.getDescription());
            viewBinding.textViewPlace.setText(reminderData.getLocationName());
        }
    }

    private View.OnClickListener onConfirmReminderClickListener = __->  {

        if(isReminderFieldsFilled()) {

            fillReminderAttributes();

            viewModel.insertReminder(reminderData);
        }
    };

    private boolean isReminderFieldsFilled() {

        boolean allFieldsFilled = true;

        if(viewBinding.editTextTitle.getText().toString().length() == 0) {
            viewBinding.editTextDescription.setError(getString(R.string.empty_field));
            allFieldsFilled = false;
        }

        if(viewBinding.editTextDescription.getText().toString().length() == 0) {
            viewBinding.editTextDescription.setError(getString(R.string.empty_field));
            allFieldsFilled = false;
        }

        if(viewBinding.textViewPlace.getText().toString().length() == 0) {
            viewBinding.textViewPlace.setError(getString(R.string.empty_field));
            allFieldsFilled = false;
        }

        return allFieldsFilled;
    }

    private void fillReminderAttributes() {

        reminderData.setTitle(viewBinding.editTextTitle.getText().toString());
        reminderData.setDescription(viewBinding.editTextDescription.getText().toString());
        reminderData.setLocationName(viewBinding.textViewPlace.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                String placeName = place.getName().toString();

                reminderData.setLocationName(placeName);
                reminderData.setLatitude(place.getLatLng().latitude);
                reminderData.setLongitude(place.getLatLng().longitude);

                viewBinding.textViewPlace.setText(placeName);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

            } else if (resultCode == RESULT_CANCELED) { }
        }
    }

    private View.OnClickListener onEditPlaceClickListener = __-> {

        try {

            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

            Intent intent = intentBuilder.build(AddReminderActivity.this);

            startActivityForResult(intent, PLACE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    };

    @Override
    public void handleReminderAddedEvent(Void aVoid) {

        if(getCallingActivity() != null) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra(ReminderData.class.getSimpleName(), reminderData);
            setResult(RESULT_OK, resultIntent);
        }

        RemindersWidget.updateWidget(getApplicationContext());

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ReminderData.class.getSimpleName(), reminderData);
        super.onSaveInstanceState(outState);
    }
}
