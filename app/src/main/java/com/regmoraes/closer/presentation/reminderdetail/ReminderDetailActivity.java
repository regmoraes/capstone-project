package com.regmoraes.closer.presentation.reminderdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.databinding.ActivityReminderDetailBinding;
import com.regmoraes.closer.presentation.addreminder.ReminderData;

import javax.inject.Inject;

public class ReminderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityReminderDetailBinding viewBinding;
    private ReminderDetailViewModel viewModel;
    private MapFragment mapFragment;
    private ReminderData reminderData;

    @Inject
    public ReminderDetailViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();

        setUpMap();
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector()
                .presentationComponent()
                .inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminder_detail);

        setSupportActionBar(viewBinding.appBar.toolbar);

        viewBinding.fabNavigate.setOnClickListener(onNavigateClickListener);

        String reminderDataExtra = ReminderData.class.getSimpleName();

        if(getIntent().hasExtra(reminderDataExtra)) {
            reminderData = getIntent().getParcelableExtra(reminderDataExtra);
        }

        setUpMap();
    }

    private void setUpMap() {

        MapFragment mapFragment = MapFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .add(viewBinding.content.getId(), mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(reminderData.getLatitude(), reminderData.getLongitude()))
                .title(reminderData.getTitle()));
    }

    private View.OnClickListener onNavigateClickListener = __->  {

        Intent mapsItent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&daddr=20.5666,45.345"));
        startActivity(mapsItent);
    };
}
