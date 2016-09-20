package com.example.android.routetracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.android.routetracker.Activity.RouteActivity;
import com.example.android.routetracker.Class.LocationEntry;
import com.example.android.routetracker.Class.TravelSession;
import com.example.android.routetracker.Database.LocationDBManager;
import com.example.android.routetracker.R;
import com.example.android.routetracker.Util.AppUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Bridget on 8/15/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<TravelSession> sessionArrayList;
    Context context;
    //int sessionId;
    LocationDBManager locationDBManager;
    private final HashSet<MapView> mMaps = new HashSet<MapView>();
    private static final int BOUNDING_BOX_PADDING_PX = 20;

    public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView tvDate;
        TextView tvStartTime;
        TextView tvStopTime;
        MapView mapView;
        GoogleMap map;

        public ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RouteActivity.class);
                    int sessionId = sessionArrayList.get(getAdapterPosition()).getId();
                    intent.putExtra("sessionId", sessionId);
                    context.startActivity(intent);
                }
            });

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStarTime);
            tvStopTime = (TextView) itemView.findViewById(R.id.tvStopTime);
            mapView = (MapView) itemView.findViewById(R.id.listMap);

            locationDBManager = new LocationDBManager(context);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context);
            map = googleMap;

            //disable toolbar of lite map
            map.getUiSettings().setMapToolbarEnabled(false);

            ArrayList<LocationEntry> locationEntries = (ArrayList<LocationEntry>) mapView.getTag();
            if (locationEntries != null) {
                showRoute(map, mapView, locationEntries);
            }

        }

        public void initializeMapView() {
            if (mapView != null) {
                //initialize the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }
        }
    }

    public void showRoute(final GoogleMap map, final MapView mapView, ArrayList<LocationEntry> locationEntries) {
        List<LatLng> coordinates;

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

            map.addPolyline(new PolylineOptions().geodesic(true)
                    .addAll(coordinates));

            // Cannot zoom to bounds until the map has a size.

            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                                BOUNDING_BOX_PADDING_PX));
                    }
                });
            }
        }

        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public CustomAdapter(Context context, ArrayList<TravelSession> sessionArrayList) {
        this.sessionArrayList = sessionArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(rowView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int sessionId = sessionArrayList.get(position).getId();
        ArrayList<LocationEntry> locations = locationDBManager.getAllLocationsBySessionId(sessionId);

        long startTime = sessionArrayList.get(position).getStartTime();
        long stopTime = sessionArrayList.get(position).getStopTime();

        viewHolder.tvDate.setText(AppUtils.getFriendlyDayString(context, startTime));
        viewHolder.tvStartTime.setText(AppUtils.getFormattedTime(context, startTime) + " - " + AppUtils.getFormattedTime(context, stopTime));
        //viewHolder.tvStopTime.setText(AppUtils.getFormattedTime(context, stopTime));

        //Initialize the MapView
        viewHolder.initializeMapView();

        //Keep track of MapVew
        mMaps.add(viewHolder.mapView);

        // Get the LocationEntry list for this item and attach it to the MapView
        viewHolder.mapView.setTag(locations);

        //Stop starting Google Map app after clicking on the map
        viewHolder.mapView.setClickable(false);


        // Ensure the map has been initialised by the on map ready callback in ViewHolder.
        // If it is not ready yet, it will be initialised with the NamedLocation set as its tag
        // when the callback is received.
        if (viewHolder.map != null) {
            // The map is already ready to be used
            showRoute(viewHolder.map, viewHolder.mapView, locations);

            viewHolder.map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(context, RouteActivity.class);
                    intent.putExtra("sessionId", sessionId);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return sessionArrayList.size();
    }
}
