package com.example.android.routetracker.Service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.android.routetracker.Class.LocationEntry;
import com.example.android.routetracker.Database.LocationDBManager;
import com.example.android.routetracker.Util.Constants;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bridget on 8/19/2016.
 */
public class RouteTrackerService extends IntentService {
    protected static final String TAG = "RouteTrackerService";
    LocationDBManager manager;

    public RouteTrackerService() {
        super(TAG);

        manager = new LocationDBManager(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        // Log each activity.
        Log.i(TAG, "activities detected");
        for (DetectedActivity da : detectedActivities) {
            Log.i(TAG, Constants.getActivityString(
                    getApplicationContext(),
                    da.getType()) + " " + da.getConfidence() + "%"
            );
        }

        //if ((result.getMostProbableActivity().getType() != DetectedActivity.STILL) && LocationResult.hasResult(intent)) {
            /*LocationResult locationResult = LocationResult.extractResult(intent);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                int sessionId = localIntent.getIntExtra(Constants.CURRENT_SESSION_ID_EXTRA, 0);
                insertLocation(sessionId, location);
                Log.d("locationtesting", "accuracy: " + location.getAccuracy() + " lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            }*/
        //}

        // Broadcast the list of detected activities.
        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }


}
