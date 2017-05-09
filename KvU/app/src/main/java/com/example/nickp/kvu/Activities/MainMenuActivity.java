package com.example.nickp.kvu.Activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;


import com.example.nickp.kvu.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MainMenuActivity extends FragmentActivity implements LocationListener{

    //Global Vars
    public enum NextActity{EXPLORE, EXPLORE_POST_SEARCH};
    private ImageView logo;
    private Button exploreButton;
    private Button searchButton;
    protected LocationManager locationManager;
    protected InputMethodManager imm;
    private Location lastLocation;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private LatLng searchResult = null;
    PlaceAutocompleteFragment autocompleteFragment;
    //Entry Point
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //Initialize components
        initGPS();
        initView();
        initSearch();


    }

    @Override
    public void onLocationChanged(Location location) {
        this.lastLocation = location;
    }
    private void initView(){
        logo = (ImageView) this.findViewById(R.id.kvu_logo);
        logo.setAlpha(250);
        initExploreButton();
    }
    private void initExploreButton(){
        exploreButton = (Button) this.findViewById(R.id.explore_btn);
        exploreButton.setAlpha((float).9);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResult = new LatLng(38.957219, -95.252761);
                switchActivity(NextActity.EXPLORE_POST_SEARCH);
            }
        });
    }

    private void initSearch(){
        //Create Filter
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("US")
                .build();
        //Create Fragment for autocomplete
        autocompleteFragment= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //TODO: fix below to have better filter
        autocompleteFragment.setFilter(typeFilter);
        //Setting Search Bias to KU Campus
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(38.942041,-95.26844),
                new LatLng(38.967440,-95.239603)));

        //Add Place selection handler to autocomplete fragment
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                searchResult = place.getLatLng();
                Log.d("SEARCH RESULT",searchResult.toString());
                switchActivity(NextActity.EXPLORE_POST_SEARCH);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("AutocompleteFrag", "An error occurred: " + status);

            }
        });
    }

    public void switchActivity(NextActity nextActity){
        if(nextActity==NextActity.EXPLORE){
            //build intent and send location to explore activity
            Intent intent = new Intent(this, MapsActivity.class);
            String inputJson = getLocationJSONString();
            intent.putExtra("inputJSON", inputJson);
            intent.putExtra("fromExplore", true);
            startActivity(intent);


        }else if( nextActity == NextActity.EXPLORE_POST_SEARCH){
            //build intent and send location from search to explore activity
            if(searchResult == null){
                return;
            }
            String inputJSON = "{ \"lat\" :"+searchResult.latitude+", \"lon\" : "
                    +searchResult.longitude+"}";
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("inputJSON", inputJSON);
            intent.putExtra("fromExplore", false);
            startActivity(intent);
        }
    }
    private void initGPS(){
        //request system service
        lastLocation = null;
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //Check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermissions();
        } else {
            updateLocation();
        }
    }

    /***********************************
     * Helper Functions
     * @return
     */
    public String getLocationJSONString(){
        String ret = "";
        Location location = null;
        if(this.lastLocation == null){
            location = getLastLocation();
        }else{
            location = this.lastLocation;
        }
        if(location != null){
            ret = "{ \"lat\" : "+location.getLatitude()+", \"lon\" : "+location.getLongitude()+"}";
        }
        return ret;
    }
    private void checkPermissions(){
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        ActivityCompat.requestPermissions(this, permissions, 0);
    }
    private void updateLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        }else{
            checkPermissions();
        }
    }
    private Location getLastLocation(){
        Location tmp;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            return locationManager.getLastKnownLocation("gps");
        }
        return null;
    }

    //******************************************8
    // unused method stubs for location listener implementation
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}
    @Override
    public void onProviderEnabled(String s) {}
    @Override
    public void onProviderDisabled(String s) {}



}
