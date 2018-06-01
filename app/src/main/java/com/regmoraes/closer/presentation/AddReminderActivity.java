package com.regmoraes.closer.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.regmoraes.closer.CloserApp;
import com.regmoraes.closer.R;
import com.regmoraes.closer.databinding.ActivityAddReminderBinding;
import com.regmoraes.closer.domain.RemindersManager;

import javax.inject.Inject;

public class AddReminderActivity extends AppCompatActivity {

    private ActivityAddReminderBinding viewBinding;

    @Inject
    public RemindersManager remindersManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpInjections();

        super.onCreate(savedInstanceState);

        setUpView();
    }

    private void setUpInjections() {

        ((CloserApp) getApplication()).getComponentsInjector().inject(this);
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_reminder);

        setSupportActionBar(viewBinding.included.toolbar);

        viewBinding.buttonConfirm.setOnClickListener(__-> {

        });
    }
}
