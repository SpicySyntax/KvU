package com.example.nickp.kvu.ServerObjects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nickp on 4/19/2017.
 */
public class Path {
    private Point start;
    private Point end;
    public Path(Point start, Point end){
        this.start = start;
        this.end = end;
    }
    public Point getStartPt(){
        return start;
    }
    public Point getEndPt(){
        return end;
    }
    public Path(String jsonStr){
        try {
            JSONObject obj = new JSONObject(jsonStr);
            start = new Point(obj.getString("start"));
            end = new Point(obj.getString("end"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getPathName(){
        return start.getPtName()+"_"+end.getPtName();
    }
    public String getPathJSON(){
        return "{ \"start\" : "+start.getJsonString()
                +", \"end\" : "+end.getJsonString()+"}";
    }
    public String getIntentJsonString(){
        String ret = "";
        ret+="{ \"isFirstTime\" : " + false+",\"isPoint\" : "+false+", \"data\" : "+getPathJSON()+"}";
        return ret;
    }
}
