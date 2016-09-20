package com.example.android.routetracker.Preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.routetracker.Util.Constants;

import java.util.HashMap;

/**
 * Created by Bridget on 8/18/2016.
 */
public class SessionPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private HashMap<String, String> hashMap;

    private static final String PREFERENCE_NAME = "route_tracker_pref";

    private static final String SESSION_ID_KEY = "current_session_id";
    private static final String TRACK_ALLOWED_KEY = "track_allowed";

    public SessionPreference(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveCurrentSession(int sessionId) {
        editor.putInt(SESSION_ID_KEY, sessionId);

        editor.commit();
    }

    public int getCurrentSession() {
        int sessionId = sharedPreferences.getInt(SESSION_ID_KEY, 0);
        return sessionId;
    }

    public void saveTrackAllowed(boolean trackAllowed) {
        editor.putBoolean(TRACK_ALLOWED_KEY, trackAllowed);
        editor.commit();
    }

    public boolean isTrackAllowed() {
        boolean trackAllowed = sharedPreferences.getBoolean(TRACK_ALLOWED_KEY, false);
        return trackAllowed;
    }

    /**
     * Sets the boolean in SharedPreferences that tracks whether we are requesting activity
     * updates.
     */
    public void setUpdatesRequestedState(boolean requestingUpdates) {
        editor.putBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, requestingUpdates);
        editor.commit();
    }

    /**
     * Retrieves the boolean from SharedPreferences that tracks whether we are requesting activity
     * updates.
     */
    public boolean getUpdatesRequestedState() {
        boolean requestState = sharedPreferences.getBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, false);
        return requestState;
    }
}
