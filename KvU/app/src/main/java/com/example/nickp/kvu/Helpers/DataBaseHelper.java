package com.example.nickp.kvu.Helpers;

/**
 * Created by nickp on 4/19/2017.
 */
import android.util.Log;

import com.example.nickp.kvu.ServerObjects.Point;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseHelper {
    private Connection con;
    public DataBaseHelper(){
        con = null;
    }
    public boolean connectToDb(){
        boolean ret;
        //TODO: try to find alternative to hardcoding these
        String serverName = "52.88.153.136";
        String portNumber = "1433";
        String db = "KvU";
        // source name
        String user = "remoteSQL"; // username of oracle database
        String pwd = "<5s4q2l>"; // password of oracle database
        con = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://"+serverName+":"+portNumber+"/"
                    +db,user,pwd);
            Log.i("DB Helper","----Connection to SQL SVR established----");
            ret = true;
        }catch(Exception e){
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }
    public Point getPoint(String pointQuery){
        Log.i("Db Helper","----Attempting to fetch point by name---");
        Point ret = null;
        if(this.con == null){
            if(!connectToDb()){
                return ret;
            }
        }
        try{
            Statement stmt = con.createStatement();
            //TODO: change to outside entry points one video and panos are obtained
            ResultSet res  = stmt.executeQuery(pointQuery);
            Log.i("Db Helper","----Executed Point Query---");
            if(res.next()){
                ret = new Point(res.getInt("PK_INT"),res.getString("PT_NAME"),
                        res.getDouble("LAT"),res.getDouble("LONG"),res.getString("NEXT_PT"),
                        res.getBoolean("ENTRY"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        closeCon();
        return ret;
    }
    public ArrayList<Point> getPoints(String pointsQuery){
        Log.i("Db Helper","----Attempting to fetch points---");
        ArrayList<Point> ret = null;
        if(this.con == null){
            if(!connectToDb()){
                return ret;
            }
        }
        try{
            Statement stmt = con.createStatement();
            //TODO: change to outside entry points one video and panos are obtained
            ResultSet res  = stmt.executeQuery(pointsQuery);
            Log.i("Db Helper","----Executed Points Query---");
            ret = new ArrayList<Point>();
            while(res.next()){
                Log.d("Db Helper","----recieved Points---");
                Point newPoint = new Point(res.getInt("PK_INT"),res.getString("PT_NAME"),
                        res.getDouble("LAT"),res.getDouble("LONG"),res.getString("NEXT_PT"),
                        res.getBoolean("ENTRY"));
                ret.add(newPoint);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        closeCon();
        return ret;
    }
    public void closeCon(){
        try{
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        con = null;
    }


}
