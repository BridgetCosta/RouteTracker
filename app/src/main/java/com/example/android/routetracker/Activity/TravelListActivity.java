package com.example.android.routetracker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.android.routetracker.Adapter.CustomAdapter;
import com.example.android.routetracker.Class.TravelSession;
import com.example.android.routetracker.Database.TravelSessionDBManager;
import com.example.android.routetracker.Preference.SessionPreference;
import com.example.android.routetracker.R;
import com.example.android.routetracker.Service.LocationTrackerService;
import com.example.android.routetracker.Util.AppUtils;

import java.util.ArrayList;

public class TravelListActivity extends AppCompatActivity {

    Toolbar toolbar;

    final static String TAG = "travelListActivity";
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<TravelSession> sessionArrayList = new ArrayList<>();
    ListFragment listFragment;
    SwitchCompat trackingToggle;
    boolean isTrackAllowed;
    SessionPreference preference;
    TravelSessionDBManager sessionDBManager;
    int currentSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.map_list);

        recyclerView = (RecyclerView) findViewById(R.id.rvTravelList);
        trackingToggle = (SwitchCompat) findViewById(R.id.trackingToggle);

        sessionDBManager = new TravelSessionDBManager(this);
        preference = new SessionPreference(this);

        final int currentSessionId = preference.getCurrentSession();
        isTrackAllowed = preference.isTrackAllowed();

        trackingToggle.setChecked(isTrackAllowed);

        trackingToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                long currentTime = System.currentTimeMillis();

                Intent mServiceIntent = new Intent(getApplicationContext(), LocationTrackerService.class);
                if (isChecked) {
                    checkPermission();

                    //start the service to track route
                    startService(mServiceIntent);

                    TravelSession session = new TravelSession(currentTime, currentTime);
                    long sessionId = sessionDBManager.addSession(session);

                    if (sessionId > 0) {
                        preference.saveCurrentSession((int) sessionId);
                        preference.saveTrackAllowed(true);

                        Toast.makeText(getApplicationContext(), String.valueOf(preference.getCurrentSession()), Toast.LENGTH_SHORT).show();

                        TravelSession travelSession = sessionDBManager.getTravelSessionById((int) sessionId);

                        Toast.makeText(getApplicationContext(), AppUtils.getFormattedTime(getApplicationContext(), travelSession.getStartTime()) + " "
                                + AppUtils.getFormattedTime(getApplicationContext(), travelSession.getStopTime()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    stopService(mServiceIntent);

                    TravelSession travelSession = sessionDBManager.getTravelSessionById(currentSessionId);
                    travelSession.setStopTime(currentTime);

                    preference.saveTrackAllowed(false);

                    sessionDBManager.updateSession(currentSessionId, travelSession);

                    /*Toast.makeText(getApplicationContext(), AppUtils.getFormattedTime(getApplicationContext(), travelSession.getStartTime()) + " "
                            + AppUtils.getFormattedTime(getApplicationContext(), travelSession.getStopTime()), Toast.LENGTH_SHORT).show();*/
                }
            }
        });


        getData();
    }


    public void getData() {
        sessionArrayList = sessionDBManager.getAllSessions();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CustomAdapter(this, sessionArrayList);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    protected void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
