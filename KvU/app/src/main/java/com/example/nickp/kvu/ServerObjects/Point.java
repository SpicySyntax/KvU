package com.example.nickp.kvu.ServerObjects;

import com.example.nickp.kvu.Helpers.UserLvlNameResolver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nickp on 4/3/2017.
 */

public class Point {
    private int pk;
    private String ptName;
    private double lat;
    private double lon;
    private String nextPts;
    private boolean entry;
    public class NextPoint{
        public String pointName;
        public String overlayName;
        public NextPoint(String pointName, String overlayName){
            this.pointName = pointName;
            this.overlayName = overlayName;
        }
        public void print(){
            System.out.println("pt_name:"+pointName+",overlay_name:"+overlayName);
        }
    }
    public Point(String JsonString){
        try {
            JSONObject obj = new JSONObject(JsonString);
            pk = obj.getInt("pk");
            ptName = obj.getString("ptName");
            lat = obj.getDouble("lat");
            lon = obj.getDouble("lon");
            nextPts = obj.getString("nextPts");
            entry = obj.getBoolean("entry");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Point(int pk, String ptName, double lat, double lon, String nextPts, boolean entry){
        this.pk = pk;
        this.ptName = ptName;
        this.lat = lat;
        this.lon = lon;
        this.nextPts = nextPts;
        this.entry = entry;
    }
    public String getJsonString(){
        String ret = "";
        ret+="{ \"pk\" : "+pk+", \"ptName\" : "+ptName+", \"lat\" : "+lat
                +", \"lon\" : "+lon+", \"nextPts\" : "+nextPts+", \"entry\" : "+entry+"}";
        return ret;
    }
    public String getUsrLvlString(){
        String ret = "";
        UserLvlNameResolver nameResolver = new UserLvlNameResolver();
        ret = nameResolver.resolveUserLvlString(this.ptName);
        return ret;
    }
    public ArrayList<NextPoint> getNextPointsArrayList(){
        ArrayList<NextPoint> ret = null;
        JSONObject obj = null;
        //System.out.println("YOYO:"+getNextPts());
        try{
            obj = new JSONObject(getNextPts());
            JSONArray arr = obj.getJSONArray("next_pts");
            if(arr != null){
                ret = new ArrayList<NextPoint>();
                int len = arr.length();
                String pt = null;
                for(int i = 0; i < len; i++){
                    pt = arr.get(i).toString();
                    //System.out.println(pt);
                    String[] tokens = pt.split(":");
                    tokens[0] = tokens[0].substring(1).replaceAll("^\"|\"$", "");
                    tokens[1] = tokens[1].substring(0, tokens[1].length()-1).replaceAll("^\"|\"$", "");
                    NextPoint nextPoint = new NextPoint(tokens[0],tokens[1]);
                    nextPoint.print();
                    ret.add(nextPoint);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return ret;
    }

    public String getIntentJsonString(boolean isFirstTime){
        String ret = "";
        ret+="{  \"isFirstTime\" : " + isFirstTime + ",\"isPoint\" : "+true+", \"data\" : "+getJsonString()+"}";
        return ret;
    }

    public boolean isEntry() {
        return entry;
    }
    public double getLon(){
        return lon;
    }
    public String getPtName(){
        return ptName;
    }

    public int getPk() {
        return pk;
    }

    public double getLat() {
        return lat;
    }

    public String getNextPts() {
        return nextPts;
    }
}
