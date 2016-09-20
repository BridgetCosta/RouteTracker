package com.example.android.routetracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.routetracker.Database.Tables.LocationEntryTable;
import com.example.android.routetracker.Database.Tables.TravelSessionTable;

/**
 * Created by Bridget on 8/14/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "route_tracker.db";
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";


    public static final String CREATE_LOCATION_ENTRY_TABLE = " CREATE TABLE "
            + LocationEntryTable.TABLE_NAME +
            "( " + LocationEntryTable._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
            + LocationEntryTable.LATITUDE + REAL_TYPE + COMMA_SEP
            + LocationEntryTable.LONGITUDE + REAL_TYPE + COMMA_SEP
            + LocationEntryTable.ADDRESS + TEXT_TYPE + COMMA_SEP
            + LocationEntryTable.TIME_IN_MILLIS + INTEGER_TYPE + COMMA_SEP
            + LocationEntryTable.SESSION_ID + INTEGER_TYPE + " )";

    public static final String CREATE_SESSION_ENTRY_TABLE = "CREATE TABLE "
            + TravelSessionTable.TABLE_NAME +
            "( " + TravelSessionTable._ID + " INTEGER PRIMARY KEY, "
            + TravelSessionTable.START_TIME_IN_MILLIS + INTEGER_TYPE + COMMA_SEP
            + TravelSessionTable.STOP_TIME_IN_MILLIS + INTEGER_TYPE + " )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOCATION_ENTRY_TABLE);
        sqLiteDatabase.execSQL(CREATE_SESSION_ENTRY_TABLE);

        //default data
        //Session
        sqLiteDatabase.execSQL("INSERT INTO " + TravelSessionTable.TABLE_NAME + "(" + TravelSessionTable.START_TIME_IN_MILLIS
                + COMMA_SEP + TravelSessionTable.STOP_TIME_IN_MILLIS
                + ") VALUES (1471235400000, 1471253400000)");

        sqLiteDatabase.execSQL("INSERT INTO " + TravelSessionTable.TABLE_NAME + "(" + TravelSessionTable.START_TIME_IN_MILLIS
                + COMMA_SEP + TravelSessionTable.STOP_TIME_IN_MILLIS
                + ") VALUES (1471425306000, 1471437603000)");

        sqLiteDatabase.execSQL("INSERT INTO " + TravelSessionTable.TABLE_NAME + "(" + TravelSessionTable.START_TIME_IN_MILLIS
                + COMMA_SEP + TravelSessionTable.STOP_TIME_IN_MILLIS
                + ") VALUES (1471493930000, 1471520260000)");

        //Location Entry
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.760518, 90.386473, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.758317, 90.388166, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.757186, 90.388781, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.755373, 90.389175, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.754057, 90.388551, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.753036, 90.387006, '', 1471235400000, 1)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.752329, 90.385075, '', 1471253400000, 1)");

        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.782024, 90.414231, '', 1471425306000, 2)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.781975, 90.416119, '', 1471425306000, 2)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.780748, 90.416677, '', 1471425306000, 2)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.777950, 90.416613, '', 1471425306000, 2)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.774975, 90.416291, '', 1471425306000, 2)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.773394, 90.416000, '', 1471437603000, 2)");

        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.751416, 90.367424, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.751585, 90.367657, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.752066, 90.368231, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.752451, 90.368920, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.752694, 90.369360, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.752890, 90.372855, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.755696, 90.374054, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.756347, 90.375132, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.757991, 90.374242, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.758388, 90.375018, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.758679, 90.383856, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.759990, 90.383738, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.760147, 90.386345, '', 1471493930000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO " + LocationEntryTable.TABLE_NAME + "(" + LocationEntryTable.LATITUDE
                + COMMA_SEP + LocationEntryTable.LONGITUDE + COMMA_SEP + LocationEntryTable.ADDRESS
                + COMMA_SEP + LocationEntryTable.TIME_IN_MILLIS + COMMA_SEP + LocationEntryTable.SESSION_ID
                + ") VALUES (23.760442, 90.386468, '', 1471520260000, 3)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
