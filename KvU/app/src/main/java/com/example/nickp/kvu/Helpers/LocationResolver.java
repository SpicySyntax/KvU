package com.example.nickp.kvu.Helpers;

import com.example.nickp.kvu.ServerObjects.Point;

import java.util.ArrayList;

/**
 * Created by nickp on 3/13/2017.
 */

public class LocationResolver {
    private DataBaseHelper db;
    public LocationResolver(){
        db = new DataBaseHelper();
    }
    private final String eatonPointsQuery = "Select * from POINTS_EATON;";
    private final String eatonEntryPointsQuery = "Select * from POINTS_EATON where ENTRY = 1;";
    private final String outsideEntryPointsQuery = "Select * from POINTS_OUTSIDE where ENTRY = 1;";
    public ArrayList<Point> getClosestEntryPoints(double lat, double lon){
        ArrayList<Point> ret;
        //Find closest entry point to this location
        ret = getEntryPoints();
        //put closest point at 0th index
        if(ret != null){
            int i = getIndexOfClosetPoint(lat, lon, ret);
            if( i > 0 ){
                Point tmp = ret.get(0);
                ret.set(0, ret.get(i));
                ret.set(i, tmp);
            }
        }
        //place all entry points within a close area to the point yielded above
        return ret;
    }
    private int getIndexOfClosetPoint(double lat, double lon, ArrayList<Point> entryPoints){
        double minDistance=0;
        int minIndex = -1;
        int i = 0;
        for(Point point:  entryPoints){
            double tmpDistance = getDistanceFromLatLongInKm(point.getLat(), point.getLon(), lat, lon);
            if(i == 0) {
                minDistance = tmpDistance;
                minIndex = 0;
            }
            else if(tmpDistance < minDistance){
                minDistance = tmpDistance;
                minIndex = i;
            }
            i++;
        }
        return minIndex;
    }
    private ArrayList<Point> getEntryPoints(){
        ArrayList<Point> ret = null;
        ret = db.getPoints(eatonEntryPointsQuery);
        for(Point point : db.getPoints(outsideEntryPointsQuery)){
            ret.add(point);
        }
        return ret;
    }


    public double getDistanceFromLatLongInKm(double lat1, double lon1,double lat2, double lon2){
        //radius of the earth
        double R = 6371;
        double dLat = degreeToRadians(lat2-lat1);
        double dLon = degreeToRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(degreeToRadians(lat1))*Math.cos(degreeToRadians(lat2))
                * Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d = R*c;
        return d;
    }
    private double degreeToRadians(double deg){
        return (double) (deg*(Math.PI/180));
    }

}
