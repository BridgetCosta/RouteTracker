package com.example.android.routetracker.Class;

import android.content.pm.PackageInstaller;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Bridget on 8/14/2016.
 */
public class LocationEntry {
    private int id;
    private double lat;
    private double lon;
    private long time;
    private String address;
    private int sessionId;
    private TravelSession session;
    private LatLng latLng;

    public LocationEntry(int id, double lat, double lon, long time, String address, int sessionId) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.address = address;
        this.sessionId = sessionId;
    }

    public LocationEntry(double lat, double lon, long time, String address, int sessionId) {
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.address = address;
        this.sessionId = sessionId;
    }

    public LocationEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public TravelSession getSession() {
        return session;
    }

    public void setSession(TravelSession session) {
        this.session = session;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
