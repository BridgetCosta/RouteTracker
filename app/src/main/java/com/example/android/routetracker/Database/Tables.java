package com.example.android.routetracker.Database;

import android.provider.BaseColumns;

/**
 * Created by Bridget on 8/15/2016.
 */
public class Tables {
    public static final class LocationEntryTable implements BaseColumns {
        public static final String TABLE_NAME = "location_entry";

        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
        public static final String TIME_IN_MILLIS="time_in_millis";
        public static final String SESSION_ID="session_id";
    }

    public static final class TravelSessionTable implements BaseColumns{
        public static final String TABLE_NAME="travel_session";

        public static final String START_TIME_IN_MILLIS="start_time_in_millis";
        public static final String STOP_TIME_IN_MILLIS="stop_time_in_millis";
    }
}
