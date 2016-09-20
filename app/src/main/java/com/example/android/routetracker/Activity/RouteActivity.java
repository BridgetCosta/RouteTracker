package com.example.android.routetracker.Activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.android.routetracker.Class.LocationEntry;
import com.example.android.routetracker.Database.LocationDBManager;
import com.example.android.routetracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RouteActivity extends FragmentActivity implements
        OnMapReadyCallback {

    int sessionId;
    private GoogleMap mMap;
    LocationDBManager manager;
    SupportMapFragment mapFragment;
    private static final int BOUNDING_BOX_PADDING_PX = 50;
    ArrayList<LocationEntry> locationEntries;
    Toolbar toolbar;
    AppCompatActivity compatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        /*compatActivity=new AppCompatActivity();
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = new LocationDBManager(this);

        sessionId = getIntent().getIntExtra("sessionId", 0);

        locationEntries = manager.getAllLocationsBySessionId(sessionId);

    }

    public void showRoute() {
        List<LatLng> coordinates;
        //LatLngBounds bounds;

        if (locationEntries != null && !locationEntries.isEmpty()) {
            coordinates = new ArrayList<LatLng>();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (LocationEntry entry : locationEntries) {
                LatLng latLng = new LatLng(entry.getLat(), entry.getLon());
                builder.include(latLng);
                coordinates.add(latLng);
            }
            final LatLngBounds bounds = builder.build();

            //mMap.clear();

            mMap.addPolyline(new PolylineOptions().geodesic(true)
                    .addAll(coordinates));

            // Cannot zoom to bounds until the map has a size.
            final View mapView = mapFragment.getView();
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                                BOUNDING_BOX_PADDING_PX));
                    }
                });
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        showRoute();
        addMarker();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void addMarker() {
        if (locationEntries != null && !locationEntries.isEmpty()) {
            int i = 0;
            for (LocationEntry entry : locationEntries) {
                LatLng latLng = new LatLng(entry.getLat(), entry.getLon());

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                String addressLine = "";
                String snippet = "Lat: " + entry.getLat() + ", Lng: " + entry.getLon();
                try {
                    List<Address> addressList = geocoder.getFromLocation(entry.getLat(), entry.getLon(), 1);
                    Address address = addressList.get(0);
                    addressLine = address.getAddressLine(0) + ", " + address.getAddressLine(1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(addressLine)
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / locationEntries.size())));

                i++;
            }

        }
    }
}
