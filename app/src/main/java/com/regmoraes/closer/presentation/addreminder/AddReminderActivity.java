package com.regmoraes.closer.presentation.addreminder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector().inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_reminder);

        setSupportActionBar(viewBinding.included.toolbar);

        viewBinding.buttonConfirm.setOnClickListener(onConfirmReminderClickListener);
        viewBinding.editTextPlace.setOnClickListener(onEditPlaceClickListener);

        reminderData = new ReminderData();
    }

    private void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AddReminderViewModel.class);

        viewModel.getReminderAddedEvent().observe(this, this::handleReminderAddedEvent);
    }

    private void fillReminderAttributes() {

        reminderData.setTitle(viewBinding.editTextTitle.getText().toString());
        reminderData.setDescription(viewBinding.editTextDescription.getText().toString());
        reminderData.setLocationName(viewBinding.editTextPlace.getText().toString());
    }

    private boolean isReminderFieldsFilled() {

        boolean allFieldsFilled = true;

        if(viewBinding.editTextTitle.getText().toString().length() == 0) {
            //viewBinding.editTextDescription.setError();
            allFieldsFilled = false;
        }

        if(viewBinding.editTextDescription.getText().toString().length() == 0) {
            //viewBinding.editTextDescription.setError();
            allFieldsFilled = false;
        }

        if(viewBinding.editTextPlace.getText().toString().length() == 0) {
            //viewBinding.editTextPlace.setError();
            allFieldsFilled = false;
        }

        return allFieldsFilled;
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

                viewBinding.editTextPlace.setText(placeName);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

            } else if (resultCode == RESULT_CANCELED) { }
        }
    }

    private View.OnClickListener onConfirmReminderClickListener = __->  {

        if(isReminderFieldsFilled()) {

            fillReminderAttributes();

            viewModel.insertReminder(reminderData);
        }
    };

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
        finish();
    }
}
