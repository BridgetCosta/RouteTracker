package com.example.android.routetracker.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.android.routetracker.Class.LocationEntry;
import com.example.android.routetracker.Database.LocationDBManager;
import com.example.android.routetracker.Database.TravelSessionDBManager;
import com.example.android.routetracker.Preference.SessionPreference;
import com.example.android.routetracker.Util.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bridget on 8/20/2016.
 */
public class LocationTrackerService extends Service
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final String TAG = "LOCATION_TRACER_SERVICE";

    GoogleApiClient googleApiClient;
    LocationRequest request;
    Location location;
    LocationDBManager manager;
    LocationEntry locationEntry;
    int currentSessionId;
    SessionPreference preference;
    TravelSessionDBManager sessionDBManager;

    Intent intent;

    @Override
    public void onCreate() {
        Log.d(TAG, "service created");

        manager = new LocationDBManager(this);
        sessionDBManager = new TravelSessionDBManager(this);

        preference = new SessionPreference(this);
        currentSessionId = preference.getCurrentSession();

        buildGoogleApiClient();
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();

        request = LocationRequest.create();
        request.setInterval(Constants.INTERVAL_PERIOD);
        request.setFastestInterval(Constants.FASTEST_INTERVAL_PERIOD);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        request.setSmallestDisplacement(Constants.SMALLEST_DISPLACEMENT_METERS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "service stated");
        if (googleApiClient != null && !googleApiClient.isConnected())
            googleApiClient.connect();
        else {
            buildGoogleApiClient();
            googleApiClient.connect();
        }
        //return super.onStartCommand(intent, flags, startId);

        // If we get killed, after returning from here, restart
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,
                this);
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
        super.onDestroy();
        Log.v(TAG, "I am destroyed");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    request, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.i(TAG, "Location changed");
            if (preference.isTrackAllowed()) //as the service is running it is sure that tracking is
                //allowed. Though crosscheck.
                insertLocation(currentSessionId, location);
        }
    }

    public void insertLocation(int sessionId, Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        long time = System.currentTimeMillis();

        String addressLine = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addressList.get(0);

            addressLine = address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocationEntry locationEntry = new LocationEntry(latitude, longitude, time, addressLine, sessionId);
        boolean inserted = manager.addLocation(locationEntry);
        if (inserted) {
            //Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "data saved");
        }
    }
}
