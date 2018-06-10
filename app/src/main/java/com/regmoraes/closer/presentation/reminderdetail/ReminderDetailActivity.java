package com.regmoraes.closer.presentation.reminderdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
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

        viewBinding.fabNavigate.setOnClickListener(onNavigateClickListener);

        setSupportActionBar(viewBinding.appBar.toolbar);

        loadReminderDetail();

        setUpMap();
    }

    private void loadReminderDetail() {

        String reminderDataExtra = ReminderData.class.getSimpleName();

        if(getIntent().hasExtra(reminderDataExtra)) {
            reminderData = getIntent().getParcelableExtra(reminderDataExtra);
        }
    }

    private void setUpMap() {

        GoogleMapOptions options = new GoogleMapOptions()
                .mapToolbarEnabled(false);

        MapFragment mapFragment = MapFragment.newInstance(options);

        getFragmentManager()
                .beginTransaction()
                .add(viewBinding.content.getId(), mapFragment)
                .commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng position = new LatLng(reminderData.getLatitude(), reminderData.getLongitude());

        googleMap.addMarker(new MarkerOptions().position(position).title(reminderData.getTitle()));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(position)
                .zoom(17).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private View.OnClickListener onNavigateClickListener = __->  {

        Uri uri = Uri.parse("https://www.google.com/maps/dir/")
                .buildUpon()
                .appendQueryParameter("api","1")
                .appendQueryParameter("destination",reminderData.getLocationName())
                .build();

        Intent navigationIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(navigationIntent);
    };
}
