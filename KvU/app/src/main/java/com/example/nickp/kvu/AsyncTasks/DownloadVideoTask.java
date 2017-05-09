package com.example.nickp.kvu.AsyncTasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

/**
 * Created by nickp on 4/19/2017.
 */

public class DownloadVideoTask extends AsyncTask<String, Void, Boolean> {
    String videoHostUrl = "http://media.kvuapp.com:5080/vod/streams/";
    private VrVideoView vrVideoView;
    private Context context;
    public DownloadVideoTask(VrVideoView vrVideoView, Context context) {
        this.vrVideoView = vrVideoView;
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(String... path) {
        try {
            VrVideoView.Options options = new VrVideoView.Options();
            //Options dictate video format and type
            options.inputType = VrVideoView.Options.TYPE_MONO;
            options.inputFormat = VrVideoView.Options.FORMAT_HLS;
            String uriString = videoHostUrl+path[0]+".m3u8";
            Log.d("Download Vid Task","----"+uriString+"----");
            Uri uri = Uri.parse(uriString);
            vrVideoView.loadVideo(uri, options);

        } catch( IOException e ) {
            //Handle exception
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
