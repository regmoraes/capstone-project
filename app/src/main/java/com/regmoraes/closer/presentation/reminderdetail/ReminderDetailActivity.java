package com.regmoraes.closer.presentation.reminderdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.databinding.ActivityReminderDetailBinding;
import com.regmoraes.closer.presentation.addreminder.AddReminderActivity;
import com.regmoraes.closer.presentation.addreminder.ReminderData;
import com.regmoraes.closer.widget.RemindersWidget;

import javax.inject.Inject;

public class ReminderDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, ReminderDetailViewModel.Observer {

    public static int EDIT_REMINDER_REQ_CODE = 100;
    private static float MAP_ZOOM = 17;

    private ActivityReminderDetailBinding viewBinding;
    private ReminderDetailViewModel viewModel;

    private GoogleMap map;
    private Marker reminderMarker;
    private ReminderData reminderData = null;

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
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpViewModel();

        setUpMap();
    }

    private void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReminderDetailViewModel.class);

        viewModel.getReminderDeletedEvent().observe(this, this::handleReminderDeletedEvent);
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

        map = googleMap;

        loadReminderDetail(null);
    }

    private void loadReminderDetail(ReminderData reminderData) {

        if(reminderData == null) {

            String reminderDataExtra = ReminderData.class.getSimpleName();

            if (getIntent().hasExtra(reminderDataExtra)) {
                reminderData = getIntent().getParcelableExtra(reminderDataExtra);
                this.reminderData = reminderData;
            } else {
                return;
            }

        } else {

            if(reminderMarker != null) {
                reminderMarker.remove();
            }
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(reminderData.getTitle());
            getSupportActionBar().setSubtitle(reminderData.getLocationName());
        }

        if(map != null) {

            LatLng position = new LatLng(reminderData.getLatitude(), reminderData.getLongitude());

            reminderMarker = map.addMarker(new MarkerOptions().position(position)
                    .title(reminderData.getDescription()));

            reminderMarker.showInfoWindow();

            CameraPosition cameraPosition = new CameraPosition.Builder().target(position)
                    .zoom(MAP_ZOOM).build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_edit: {

                Intent editIntent = new Intent(this, AddReminderActivity.class);
                editIntent.putExtra(ReminderData.class.getSimpleName(), reminderData);
                startActivityForResult(editIntent, EDIT_REMINDER_REQ_CODE);

                return true;
            }

            case android.R.id.home: {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            default: {
                viewModel.deleteReminder(reminderData);
                return true;
            }
        }
    }

    @Override
    public void handleReminderDeletedEvent(Void aVoid) {
        RemindersWidget.updateWidget(getApplicationContext());
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == EDIT_REMINDER_REQ_CODE && resultCode == RESULT_OK) {

            final String reminderDataExtra = ReminderData.class.getSimpleName();

            if(data != null && data.hasExtra(reminderDataExtra)) {

                loadReminderDetail(data.getParcelableExtra(reminderDataExtra));

                Toast.makeText(this, R.string.reminder_updated, Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
