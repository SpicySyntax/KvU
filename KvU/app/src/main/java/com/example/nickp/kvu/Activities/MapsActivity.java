package com.example.nickp.kvu.Activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.nickp.kvu.CustomClasses.EntryPointMarker;
import com.example.nickp.kvu.R;
import com.example.nickp.kvu.Helpers.UserLvlNameResolver;
import com.example.nickp.kvu.Helpers.LocationResolver;
import com.example.nickp.kvu.ServerObjects.Point;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationResolver locRes;
    private HashMap<String,EntryPointMarker> entryPointMarkers;
    private boolean fromExplore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Remove, only for db activity on main thread
        entryPointMarkers = new HashMap<String, EntryPointMarker>();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            fromExplore = intent.getBooleanExtra("fromExplore",fromExplore);
            //System.out.println(fromExplore);
            String message = intent.getStringExtra("inputJSON");
            JSONObject obj  = null;
            //Get Input data from intent
            double lat = 0.0;
            double lon = 0.0;
            try {
                obj = new JSONObject(message);
                lat = obj.getDouble("lat");
                lon = obj.getDouble("lon");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //If both are valid the startPt is what we received
            locRes = new LocationResolver();
            ArrayList<Point> entryPoints =null;
            if(lat*lon != 0.0){
                entryPoints = locRes.getClosestEntryPoints(lat,lon);
            }
            String markerTitlePrepend = "Closest to your search: ";
            if(fromExplore){
                markerTitlePrepend = "Closest to you: ";
            }
            boolean isFirst = true;
            UserLvlNameResolver nameResolver = new UserLvlNameResolver();
            if(entryPoints!= null){
                int i = 0;
                for(Point point: entryPoints){
                    String userLvlPointName;
                    EntryPointMarker newMarker;
                    if(isFirst){
                        isFirst = false;
                        userLvlPointName = markerTitlePrepend
                                + nameResolver.resolveUserLvlString(point.getPtName());
                    }else{
                        userLvlPointName = nameResolver.resolveUserLvlString(point.getPtName());
                    }
                    newMarker = new EntryPointMarker(userLvlPointName,point);
                    entryPointMarkers.put(userLvlPointName, newMarker);
                }
            }
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
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
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng latLng = new LatLng(38.957219, -95.252761);
        googleMap.setOnMarkerClickListener(this);
        int i = 0;
        for(String key : entryPointMarkers.keySet()){
            EntryPointMarker marker = entryPointMarkers.get(key);
            if( i == 0 ){
                //closest point is at 0th index
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getMarkerOptions().getPosition()));
                //mMap.animateCamera( CameraUpdateFactory.zoomTo(17.6f));
                mMap.addMarker(marker.getMarkerOptions());
            }
            else{
                mMap.addMarker(marker.getMarkerOptions());
            }
            i++;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera( CameraUpdateFactory.zoomTo(17.6f));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //TODO: change activity to gvrview
        if(marker != null){
            EntryPointMarker tmp = entryPointMarkers.get(marker.getTitle());
            String inputJSON = tmp.getPoint().getIntentJsonString(true);
            Intent intent = new Intent(this, ExploreActivity.class);
            intent.putExtra("inputJSON", inputJSON);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
