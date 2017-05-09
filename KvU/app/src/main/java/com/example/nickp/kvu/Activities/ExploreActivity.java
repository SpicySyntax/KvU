package com.example.nickp.kvu.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.nickp.kvu.AsyncTasks.DownloadPanoramaTask;
import com.example.nickp.kvu.AsyncTasks.DownloadVideoTask;
import com.example.nickp.kvu.AsyncTasks.SwitchToVideoViewTask;
import com.example.nickp.kvu.Helpers.OverlayNameResolver;
import com.example.nickp.kvu.R;
import com.example.nickp.kvu.ServerObjects.Path;
import com.example.nickp.kvu.ServerObjects.Point;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import org.json.JSONException;
import org.json.JSONObject;
import com.microsoft.bing.speech.SpeechClientStatus;
import com.microsoft.cognitiveservices.speechrecognition.ISpeechRecognitionServerEvents;
import com.microsoft.cognitiveservices.speechrecognition.MicrophoneRecognitionClient;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionResult;
import com.microsoft.cognitiveservices.speechrecognition.RecognitionStatus;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionMode;
import com.microsoft.cognitiveservices.speechrecognition.SpeechRecognitionServiceFactory;
import java.util.ArrayList;


public class ExploreActivity extends
        AppCompatActivity implements SeekBar.OnSeekBarChangeListener, ISpeechRecognitionServerEvents {

    private static final String STATE_PROGRESS = "state_progress";
    private static final String STATE_DURATION = "state_duration";
    private ArrayList<Point.NextPoint> nextPoints;
    private VrVideoView mVrVideoView;
    private VrPanoramaView mVrPanoramaView;
    private AppCompatActivity activity;
    private SeekBar mSeekBar;
    private boolean mIsPaused;
    private boolean mIsMuted;
    private DownloadVideoTask mBackgroundVideoLoaderTask;
    private DownloadPanoramaTask mBackgroudPanoLoaderTask;
    private boolean isImg;// either video or image
    private Point mPoint;
    private Path mPath;
    private String inputJSON;
    private Context context;
    private boolean isFirstTime;
    private TextView firstTimeTextView;
    MicrophoneRecognitionClient micClient = null;
    FinalResponseStatus isReceivedResponse = FinalResponseStatus.NotReceived;
    public enum FinalResponseStatus { NotReceived, OK, Timeout }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        initActivity();
        initViews();
        initTasks();

    }

    private void initActivity() {
        activity = this;
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.RECORD_AUDIO},
                    0);
        }
        context = getApplicationContext();
        Intent intent = getIntent();
        inputJSON = intent.getStringExtra("inputJSON");
        JSONObject obj  = null;
        try{
            obj = new JSONObject(inputJSON);
            isImg = obj.getBoolean("isPoint");
            isFirstTime = obj.getBoolean("isFirstTime");
            String inputData = obj.getString("data");
            if(isImg){
                mPoint = new Point(inputData);
                nextPoints = mPoint.getNextPointsArrayList();
                mPath = null;
            }else{
                mPath = new Path(inputData);
                mPoint = null;
                nextPoints = null;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        //loadImgSuccess = false;
        //loadVidSuccess = false;
    }

    private void initViews() {
        firstTimeTextView = (TextView) findViewById(R.id.first_time);
        if(isFirstTime){

            firstTimeTextView.bringToFront();
        }else{
            firstTimeTextView.setVisibility(View.GONE);
        }
        mVrVideoView = (VrVideoView) findViewById(R.id.video_view);

        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        if(isImg){
            mVrVideoView.setVisibility(View.GONE);
            mSeekBar.setVisibility(View.GONE);
            mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
            mVrPanoramaView.setEventListener(new PanoActivityEventListenter(this));

        }else{
            mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
            mVrPanoramaView.setVisibility(View.GONE);
            mVrVideoView.setEventListener(new VidActivityEventListener());


        }

    }
    private void initTasks(){
        if(isImg){
            mBackgroudPanoLoaderTask =
                    new DownloadPanoramaTask(this.mVrPanoramaView,context);
            mBackgroudPanoLoaderTask.execute(mPoint.getPtName());

            //startListening();


        }else{
            micClient = null;
            mBackgroundVideoLoaderTask = new
                    DownloadVideoTask(this.mVrVideoView, context);
            mBackgroundVideoLoaderTask.execute(mPath.getPathName());
        }
    }

    /**
     * Gets the primary subscription key
     */
    public String getPrimaryKey() {
        return this.getString(R.string.primaryKey);
    }

    /**
     * Gets the LUIS application identifier.
     * @return The LUIS application identifier.
     */

    @NonNull
    private String getLuisAppId() {
        return this.getString(R.string.luisAppID);
    }

    /**
     * Gets the LUIS subscription identifier.
     * @return The LUIS subscription identifier.
     */
    @NonNull
    private String getLuisSubscriptionID() {
        return this.getString(R.string.luisSubscriptionID);
    }
    private String getDefaultLocale() {
        return "en-us";
    }

    private String getAuthenticationUri() {
        return this.getString(R.string.authenticationUri);
    }
    public void playPause() {
        if( mIsPaused ) {
            mVrVideoView.playVideo();
        } else {
            mVrVideoView.pauseVideo();
        }
        mIsPaused = !mIsPaused;
    }
    public void onVolumeToggleClicked() {
        mIsMuted = !mIsMuted;
        mVrVideoView.setVolume(mIsMuted ? 0.0f : 1.0f);

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if( fromUser ) {
            mVrVideoView.seekTo(progress);
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //no op
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //no op
    }
    @Override
    protected void onPause() {
        super.onPause();
        mVrVideoView.pauseRendering();
        mIsPaused = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mVrVideoView.resumeRendering();
        mIsPaused = false;
    }
    @Override
    protected void onDestroy() {
        if(mVrVideoView!= null){
            mVrVideoView.shutdown();
        }




        try{
            if(micClient!=null){
                micClient.endMicAndRecognition();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_PROGRESS, mVrVideoView.getCurrentPosition());
        outState.putLong(STATE_DURATION, mVrVideoView.getDuration());
        //outState.putString("inputJSON",inputJSON);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        long progress = savedInstanceState.getLong(STATE_PROGRESS);
        mVrVideoView.seekTo(progress);
        mSeekBar.setMax((int) savedInstanceState.getLong(STATE_DURATION));
        mSeekBar.setProgress((int) progress);
        //inputJSON = savedInstanceState.getString("inputJSON");
        initViews();
        initActivity();
        initTasks();

    }
    private class PanoActivityEventListenter extends VrPanoramaEventListener{
        private AppCompatActivity activity;
        public PanoActivityEventListenter(AppCompatActivity activity){
            this.activity = activity;
        }
        @Override
        public void onLoadSuccess(){

            Log.i("Explore","---SuccessfullyLoaded Pano----");
            startListening();
        }
        @Override
        public void onLoadError(String errorMessage) {
            Log.e("Explore","----Error Loading Pano----");
            //Oh no.
        }
        @Override
        public void onClick() {
            Log.i("Explore", "----Pano Clicked---");
            //TODO: modify to handle dynamic result we just get first next points for now
        }
    }
    private class VidActivityEventListener extends VrVideoEventListener {
        @Override
        public void onLoadSuccess() {
            super.onLoadSuccess();
            Log.i("Explore","----Loaded Vid----");
            mSeekBar.setMax((int) mVrVideoView.getDuration());
            mIsPaused = false;
        }
        @Override
        public void onLoadError(String errorMessage) {
            super.onLoadError(errorMessage);
            Log.e("Explore","----Error Loading Vid----");
            Intent intent = new Intent(activity, ExploreActivity.class);
            inputJSON = mPath.getEndPt().getIntentJsonString(false);
            intent.putExtra("inputJSON", inputJSON);
            //Log.i("Explore",inputJSON);
            activity.startActivity(intent);
            //onDestroy();
            //Oh no.
        }
        @Override
        public void onClick() {
            //playPause();
        }
        @Override
        public void onNewFrame() {
            super.onNewFrame();
            mSeekBar.setProgress((int) mVrVideoView.getCurrentPosition());
        }
        @Override
        public void onCompletion() {
            //TODO: after video completes dictate what to do
            Intent intent = new Intent(activity, ExploreActivity.class);
            inputJSON = mPath.getEndPt().getIntentJsonString(false);
            intent.putExtra("inputJSON", inputJSON);
            //Log.i("Explore",inputJSON);
            activity.startActivity(intent);
            //onDestroy();
        }
    }
    private SpeechRecognitionMode getMode(){
        return SpeechRecognitionMode.ShortPhrase;
    }
    private boolean getUseMicrophone(){
        return true;
    }

    public void onFinalResponseReceived(final RecognitionResult response) {
        //Log.d("VOICE RECO","---"+response.Results[0].DisplayText+"---");
        WriteLine("Final Response reveived");
        boolean isFinalDicationMessage = this.getMode() == SpeechRecognitionMode.LongDictation &&
                (response.RecognitionStatus == RecognitionStatus.EndOfDictation ||
                        response.RecognitionStatus == RecognitionStatus.DictationEndSilenceTimeout);
        if (null != this.micClient && this.getUseMicrophone() && ((this.getMode() == SpeechRecognitionMode.ShortPhrase) || isFinalDicationMessage)) {
            // we got the final result, so it we can end the mic reco.  No need to do this
            // for dataReco, since we already called endAudio() on it as soon as we were done
            // sending all the data.
            this.micClient.endMicAndRecognition();
        }
        if (isFinalDicationMessage) {
            //this._startButton.setEnabled(true);
            this.isReceivedResponse = FinalResponseStatus.OK;
        }
        if (!isFinalDicationMessage) {
            //this.WriteLine("********* Final n-BEST Results *********");
            for (int i = 0; i < response.Results.length; i++) {
                this.WriteLine("[" + i + "]" + " Confidence=" + response.Results[i].Confidence +
                        " Text=\"" + response.Results[i].DisplayText + "\"");
                String result = response.Results[i].DisplayText;
                if(checkAndHandleResult(result)) {
                    break;
                }

            }
            //this.WriteLine();
        }
        //startListening();
    }
    private boolean checkAndHandleResult(String result){
        Log.i("--RESULT",result);
        //Log.i("--RESULT","..");
        if(result.contains("Go to")){
            //Valid command
            String[] tokens = result.split("Go to");
            if(tokens.length >1 ){
                //remove period
                if(tokens[1].length()<2){
                    return false;
                }
                String attemptedOverlayName = tokens[1].substring(1,tokens[1].length()-1);
                //this.WriteLine("----SUCCESS:"+attemptedOverlayName+"-----");
                attemptedOverlayName = attemptedOverlayName.toLowerCase();
                OverlayNameResolver nameResolver = new OverlayNameResolver();
                int i = nameResolver.getIndexOfCorrespondingNextPoint(nextPoints,attemptedOverlayName);
                if( i != -1){
                    Log.d("YOYOYO","GREAT SUCCESS");
                    SwitchToVideoViewTask switchViewTask =
                            new SwitchToVideoViewTask(activity.getApplicationContext(),activity,mPoint);
                    switchViewTask.execute(nextPoints.get(i).pointName);
                    this.micClient.endMicAndRecognition();
                    return true;
                }
            }
        }else if(result.contains("go to")){
            String[] tokens = result.split("go to");
            if(tokens.length >1 ){
                //remove period
                if(tokens[1].length()<2){
                    return false;
                }
                String attemptedOverlayName = tokens[1].substring(1,tokens[1].length()-1);
                OverlayNameResolver nameResolver = new OverlayNameResolver();
                //this.WriteLine("----SUCCESS:"+attemptedOverlayName+"-----");
                attemptedOverlayName = attemptedOverlayName.toLowerCase();
                int i = nameResolver.getIndexOfCorrespondingNextPoint(nextPoints,attemptedOverlayName);
                if( i != -1){
                    Log.d("YOYOYO","GREAT SUCCESS");
                    SwitchToVideoViewTask switchViewTask =
                            new SwitchToVideoViewTask(activity.getApplicationContext(),activity,mPoint);
                    switchViewTask.execute(nextPoints.get(i).pointName);
                    this.micClient.endMicAndRecognition();
                    return true;

                }
            }
        }
        //startListening();
        return false;
    }
    private void startListening(){
        this.micClient = SpeechRecognitionServiceFactory.createMicrophoneClientWithIntent(
                this,
                this.getDefaultLocale(),
                this,
                this.getPrimaryKey(),
                this.getLuisAppId(),
                this.getLuisSubscriptionID());
        //this.micClient.setAuthenticationUri(this.getAuthenticationUri());
        this.micClient.startMicAndRecognition();
    }
    /**
     * Called when a final response is received and its intent is parsed
     */
    public void onIntentReceived(final String payload) {
        //this.WriteLine("--- Intent received by onIntentReceived() ---");
        //this.WriteLine(payload);
        //this.WriteLine();
    }

    public void onPartialResponseReceived(final String response) {
        Log.d("VOICE REC","---partial response: "+response+"---");
        //this.WriteLine("--- Partial result received by onPartialResponseReceived() ---");
        //this.WriteLine(response);
        //this.WriteLine();
    }

    public void onError(final int errorCode, final String response) {
        //this._startButton.setEnabled(true);
        Log.d("VOICE REC","---error: "+errorCode+",response:"+response+"---");
        //micClient.endMicAndRecognition();
        this.WriteLine("--- Error received by onError() ---");
        this.WriteLine("Error code: " + SpeechClientStatus.fromInt(errorCode) + " " + errorCode);
        this.WriteLine("Error text: " + response);
        this.WriteLine();
        //startListening();
    }

    /**
     * Called when the microphone status has changed.
     * @param recording The current recording state
     */
    public void onAudioEvent(boolean recording) {

        this.WriteLine("--- Microphone status change received by onAudioEvent() ---");
        this.WriteLine("********* Microphone status: " + recording + " *********");
        if (recording) {
            this.WriteLine("Please start speaking.");
        }

        WriteLine();
        if (!recording) {
            this.micClient.endMicAndRecognition();
            //this._startButton.setEnabled(true);
        }
    }

    /**
     * Writes the line.
     */
    private void WriteLine() {
        this.WriteLine("");
    }

    /**
     * Writes the line.
     * @param text The line to write.
     */
    private void WriteLine(String text) {
        Log.d("Speech Recog",text);
    }


}
