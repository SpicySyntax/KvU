package com.example.nickp.kvu.Helpers;

/**
 * Created by nickp on 4/5/2017.
 */
import java.util.*;

public class UserLvlNameResolver {
    private HashMap<String, String> dbNameToUserLvlNameMap;
    private HashMap<String, String> userLvlNameToDbNameMap;
    public UserLvlNameResolver(){
        dbNameToUserLvlNameMap = new HashMap<String, String>();
        userLvlNameToDbNameMap = new HashMap<String, String>();
        initMap();
    }
    private void initMap(){
        //TODO: keep below updated as more locations are added

        userLvlNameToDbNameMap.put("Eaton","Eat");
        userLvlNameToDbNameMap.put("Outside","Out");
        userLvlNameToDbNameMap.put("Ground-Floor","fg");
        userLvlNameToDbNameMap.put("1st-Floor","f1");
        userLvlNameToDbNameMap.put("2nd-Floor", "f2");
        userLvlNameToDbNameMap.put("3rd-Floor", "f3");
        userLvlNameToDbNameMap.put("4th-Floor", "f4");
        userLvlNameToDbNameMap.put("5th-Floor", "f5");
        userLvlNameToDbNameMap.put("#1", "p1");
        userLvlNameToDbNameMap.put("#2","p2");
        userLvlNameToDbNameMap.put("#3",  "p3");
        userLvlNameToDbNameMap.put("#4","p4");
        userLvlNameToDbNameMap.put("#5", "p5");
        userLvlNameToDbNameMap.put("#6","p6");
        userLvlNameToDbNameMap.put("#7", "p7");
        userLvlNameToDbNameMap.put("#8","p8");
        userLvlNameToDbNameMap.put("#9", "p9");
        userLvlNameToDbNameMap.put("#10","p10");
        userLvlNameToDbNameMap.put( "#11","p11");
        userLvlNameToDbNameMap.put("#12","p12");
                //
        dbNameToUserLvlNameMap.put("Eat","Eaton");
        dbNameToUserLvlNameMap.put("Out","Outside");
        dbNameToUserLvlNameMap.put("fg"," Ground-Floor");
        dbNameToUserLvlNameMap.put("f1"," 1st-Floor");
        dbNameToUserLvlNameMap.put("f2", " 2nd-Floor");
        dbNameToUserLvlNameMap.put("f3", " 3rd-Floor");
        dbNameToUserLvlNameMap.put("f4", " 4th-Floor");
        dbNameToUserLvlNameMap.put("f5", " 5th-Floor");
        dbNameToUserLvlNameMap.put("p1", " #1");
        dbNameToUserLvlNameMap.put("p2"," #2");
        dbNameToUserLvlNameMap.put("p3",  " #3");
        dbNameToUserLvlNameMap.put("p4"," #4");
        dbNameToUserLvlNameMap.put("p5", " #5");
        dbNameToUserLvlNameMap.put("p6"," #6");
        dbNameToUserLvlNameMap.put("p7", " #7");
        dbNameToUserLvlNameMap.put("p8"," #8");
        dbNameToUserLvlNameMap.put("p9", " #9");
        dbNameToUserLvlNameMap.put("p10"," #10");
        dbNameToUserLvlNameMap.put("p11", " #11");
        dbNameToUserLvlNameMap.put("p12"," #12");
        //Note: May have to accommodate for more than 12 points upon further expansion
    }
    public String resolveUserLvlString(String dbString){
        //in the following format: "Building_Floor_PointID"
        String[] tokens = dbString.split("_");
        String ret = "";
        for(String string : tokens){
            ret += dbNameToUserLvlNameMap.get(string);
        }
        return ret;
    }
    public String resolvePtName(String usrName){
        String ret = "";
        String[] tokens = usrName.split(" ");
        int len = tokens.length;
        for(int i = 0; i < len; i++){
            ret += userLvlNameToDbNameMap.get(tokens[i]);
            if(i < len-1){
                ret += "_";
            }
        }
        return ret;
    }
    public String resolveSingleEntry(String entryString){
        String ret = "";
        if(dbNameToUserLvlNameMap.containsKey(entryString)){
            ret = dbNameToUserLvlNameMap.get(entryString);
        }
        return ret;
    }
}
