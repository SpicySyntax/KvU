package com.example.nickp.kvu.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nickp.kvu.Activities.ExploreActivity;
import com.example.nickp.kvu.Helpers.DataBaseHelper;
import com.example.nickp.kvu.Helpers.UserLvlNameResolver;
import com.example.nickp.kvu.ServerObjects.Path;
import com.example.nickp.kvu.ServerObjects.Point;

/**
 * Created by nickp on 4/19/2017.
 */

public class SwitchToVideoViewTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private AppCompatActivity activity;
    private DataBaseHelper db;
    private Point currentPoint;
    private Point nextPoint;
    public SwitchToVideoViewTask(Context context, AppCompatActivity activity,Point currentPoint) {
        this.context = context;
        this.activity = activity;
        this.currentPoint = currentPoint;
        db = new DataBaseHelper();
        nextPoint = null;
    }
    @Override
    protected Boolean doInBackground(String... strings) {
        Log.i("Fetch Point Task","----Attempting point fetch----");
        boolean ret = false;
        String nxtPtName = strings[0];
        UserLvlNameResolver resolver = new UserLvlNameResolver();
        String tableName = resolver.resolveSingleEntry(
                nxtPtName.split("_")[0]).toUpperCase();

        //TODO:change to for dynamic queries
        nextPoint = db.getPoint("Select * from POINTS_"+tableName
                +" where convert(VARCHAR,PT_NAME)='"+nxtPtName+"';");
        if(nextPoint != null){
            ret = true;
        }
        return ret;
    }
    @Override
    protected void onPostExecute(Boolean suc){
        if(suc){
            Log.i("Fetch Point Task","---- Point fetched ----");
            Log.i("swtiching",currentPoint+":"+nextPoint);
            Path path = new Path(currentPoint,nextPoint);
            String inputJSON = path.getIntentJsonString();
            Intent intent = new Intent(activity, ExploreActivity.class);
            intent.putExtra("inputJSON", inputJSON);
            Log.i("Swtch2VidActiv",inputJSON);
            activity.startActivity(intent);
        }
    }
}
