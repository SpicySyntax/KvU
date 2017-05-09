/**
 * Created by nickp on 4/15/2017.
 */
package com.example.nickp.kvu.AsyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPanoramaTask extends AsyncTask<String, Void, Boolean>{
    private String imgHostUrl = "http://media.kvuapp.com:5080/live/streams/";
    private VrPanoramaView vrPanoramaView;
    private Context context;
    private Bitmap img;
    public DownloadPanoramaTask(VrPanoramaView vrPanoramaView, Context context) {
        this.vrPanoramaView = vrPanoramaView;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... point) {
        Log.i("DownloadPanoramaTask","----Downloading Pano for:"+point[0]+"----");
        HttpURLConnection urlConnection = null;
        String fileName = point[0]+".jpg";
        InputStream in = null;
        img = null;
        try {
            URL panoUri = new java.net.URL(imgHostUrl+fileName);
            urlConnection = (HttpURLConnection) panoUri.openConnection();
            urlConnection.connect();
            in = urlConnection.getInputStream();
            img = BitmapFactory.decodeStream(in);
            return true;


        } catch (java.net.MalformedURLException mex) {
            String test = mex.toString();


        } catch (Exception ex) {
            String test = ex.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean suc) {

        if(suc && img !=null){
            try {

                VrPanoramaView.Options options = new VrPanoramaView.Options();
                //VrVideoView.Options options = new VrVideoView.Options();
                //Options dictate video format and type
                options.inputType = VrPanoramaView.Options.TYPE_MONO;
                Log.i("Download Pano Task", "----Attempting to load pano----");
                vrPanoramaView.loadImageFromBitmap(img, options);

            } catch (Exception ex) {
                String msg = ex.getMessage();
            }
        }else{
            return;
        }
    }
}