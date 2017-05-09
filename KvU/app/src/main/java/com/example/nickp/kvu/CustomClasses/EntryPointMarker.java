package com.example.nickp.kvu.CustomClasses;

import com.example.nickp.kvu.ServerObjects.Point;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nickp on 4/5/2017.
 */

public class EntryPointMarker {
    private MarkerOptions markerOptions;
    private Point point;
    public EntryPointMarker(String markerTitle, Point point){
        this.point = point;
        markerOptions = new MarkerOptions().position(new LatLng(point.getLat(), point.getLon()))
                .title(markerTitle);
    }
    public MarkerOptions getMarkerOptions(){
        return this.markerOptions;
    }
    public Point getPoint(){ return this.point; }
}
