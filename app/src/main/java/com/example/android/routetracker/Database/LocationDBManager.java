package com.example.android.routetracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.routetracker.Class.LocationEntry;
import com.example.android.routetracker.Database.Tables.*;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/14/2016.
 */
public class LocationDBManager {
    LocationEntry locationEntry;
    DatabaseHelper helper;
    SQLiteDatabase database;

    public LocationDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean addLocation(LocationEntry locationEntry) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationEntryTable.LATITUDE, locationEntry.getLat());
        contentValues.put(LocationEntryTable.LONGITUDE, locationEntry.getLon());
        contentValues.put(LocationEntryTable.ADDRESS, locationEntry.getAddress());
        contentValues.put(LocationEntryTable.TIME_IN_MILLIS, locationEntry.getTime());
        contentValues.put(LocationEntryTable.SESSION_ID, locationEntry.getSessionId());

        long inserted = database.insert(LocationEntryTable.TABLE_NAME, null, contentValues);
        this.close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public ArrayList<LocationEntry> getAllLocation() {
        this.open();

        ArrayList<LocationEntry> locationList = new ArrayList<>();

        Cursor cursor = database.query(LocationEntryTable.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(LocationEntryTable._ID));
                double latitude = cursor.getDouble(cursor.getColumnIndex(LocationEntryTable.LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(LocationEntryTable.LONGITUDE));
                String address = cursor.getString(cursor.getColumnIndex(LocationEntryTable.ADDRESS));
                long time = cursor.getLong(cursor.getColumnIndex(LocationEntryTable.TIME_IN_MILLIS));
                int sessionId = cursor.getInt(cursor.getColumnIndex(LocationEntryTable.SESSION_ID));

                locationEntry = new LocationEntry(id, latitude, longitude, time, address, sessionId);
                locationList.add(locationEntry);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return locationList;
    }

    public ArrayList<LocationEntry> getAllLocationsBySessionId(int sessionId) {
        this.open();

        ArrayList<LocationEntry> locationList = new ArrayList<>();

        Cursor cursor = database.query(LocationEntryTable.TABLE_NAME,
                null, LocationEntryTable.SESSION_ID+" = "+sessionId, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(LocationEntryTable._ID));
                double latitude = cursor.getDouble(cursor.getColumnIndex(LocationEntryTable.LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(LocationEntryTable.LONGITUDE));
                String address = cursor.getString(cursor.getColumnIndex(LocationEntryTable.ADDRESS));
                long time = cursor.getLong(cursor.getColumnIndex(LocationEntryTable.TIME_IN_MILLIS));
                int msessionId = cursor.getInt(cursor.getColumnIndex(LocationEntryTable.SESSION_ID));

                locationEntry = new LocationEntry(id, latitude, longitude, time, address, msessionId);
                locationList.add(locationEntry);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return locationList;
    }

    public boolean updateLocation(long id, LocationEntry locationEntry) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationEntryTable.LATITUDE, locationEntry.getLat());
        contentValues.put(LocationEntryTable.LONGITUDE, locationEntry.getLon());
        contentValues.put(LocationEntryTable.ADDRESS, locationEntry.getAddress());
        contentValues.put(LocationEntryTable.TIME_IN_MILLIS, locationEntry.getTime());

        int updated = database.update(LocationEntryTable.TABLE_NAME, contentValues, LocationEntryTable._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteLocation(int id) {

        this.open();
        int deleted = database.delete(LocationEntryTable.TABLE_NAME, LocationEntryTable._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }
}
