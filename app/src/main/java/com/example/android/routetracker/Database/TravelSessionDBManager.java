package com.example.android.routetracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.routetracker.Class.TravelSession;
import com.example.android.routetracker.Database.Tables.TravelSessionTable;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/17/2016.
 */
public class TravelSessionDBManager {
    DatabaseHelper helper;
    SQLiteDatabase database;
    TravelSession travelSession;

    public TravelSessionDBManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public long addSession(TravelSession travelSession) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TravelSessionTable.START_TIME_IN_MILLIS, travelSession.getStartTime());
        contentValues.put(TravelSessionTable.STOP_TIME_IN_MILLIS, travelSession.getStopTime());

        long inserted = database.insert(TravelSessionTable.TABLE_NAME, null, contentValues);
        this.close();
        database.close();

        return inserted;
    }

    public TravelSession getTravelSessionById(int id) {
        this.open();

        Cursor cursor = database.query(TravelSessionTable.TABLE_NAME,
                null, TravelSessionTable._ID + "= " + id, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            long startTime = cursor.getLong(cursor.getColumnIndex(TravelSessionTable.START_TIME_IN_MILLIS));
            long stopTime = cursor.getLong(cursor.getColumnIndex(TravelSessionTable.STOP_TIME_IN_MILLIS));

            travelSession = new TravelSession(id, startTime, stopTime);
            this.close();
            database.close();

            return travelSession;
        }
        return null;
    }

    public ArrayList<TravelSession> getAllSessions() {
        this.open();

        ArrayList<TravelSession> sessionList = new ArrayList<>();

        Cursor cursor = database.query(TravelSessionTable.TABLE_NAME,
                null, null, null, null, null, TravelSessionTable._ID+" desc");

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(TravelSessionTable._ID));
                long startTime = cursor.getLong(cursor.getColumnIndex(TravelSessionTable.START_TIME_IN_MILLIS));
                long stopTime = cursor.getLong(cursor.getColumnIndex(TravelSessionTable.STOP_TIME_IN_MILLIS));

                travelSession = new TravelSession(id, startTime, stopTime);
                sessionList.add(travelSession);
                cursor.moveToNext();
            }
            this.close();
            database.close();
        }
        return sessionList;
    }

    public boolean updateSession(long id, TravelSession travelSession) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TravelSessionTable.START_TIME_IN_MILLIS, travelSession.getStartTime());
        contentValues.put(TravelSessionTable.STOP_TIME_IN_MILLIS, travelSession.getStopTime());

        int updated = database.update(TravelSessionTable.TABLE_NAME, contentValues, TravelSessionTable._ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteSession(int id) {

        this.open();
        int deleted = database.delete(TravelSessionTable.TABLE_NAME, TravelSessionTable._ID + " = " + id, null);

        this.close();
        if (deleted > 0) {
            return true;
        } else
            return false;
    }
}
