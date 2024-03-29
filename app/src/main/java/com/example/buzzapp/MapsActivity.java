package com.example.buzzapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, LocationListener, TaskLoadedCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    MarkerOptions place0,place1, place2;
    Polyline currentPolyline;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //code for the drop down menu of the bus stops and the side menu, it get's the choices from the xml file strings, also it launches the google maps interface
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bus_stops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner menu = findViewById(R.id.menu1);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.menu1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter1);
        menu.setOnItemSelectedListener(this);

        //here it get's the user's location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //place1 = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Current Location");
        place0 = new MarkerOptions().position(new LatLng(50.7903937,-1.0682483)).title("Destt");
        //place2 = changeMarker(50.7936502, -1.0978148);

        //code for when the user selects a choice from the side menu,
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem1 = parent.getItemAtPosition(position).toString();
                if(selectedItem1.equals("Maps")){

                }
                if(selectedItem1.equals("Settings")){

                    Intent i = new Intent(MapsActivity.this, SettingsActivity.class);
                    startActivity(i);
                }
                if(selectedItem1.equals("Help")){
                    Intent help = new Intent (MapsActivity.this , HelpActivity.class);
                    startActivity(help);

                }
                if(selectedItem1.equals("Logout")){
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent i = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(i);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //code for when user chooses different bus stop
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //antigrapse to function me to position je kamneto call dame gia ka8e epilogh stashs me case anti me if, nmzw enna en kltro
                if(selectedItem.equals("University of Portsmouth")) {
                    changeMarker(50.7936502,-1.0978148);
                }
                if(selectedItem.equals("Elm Grove, The Thicket")) {
                    changeMarker(50.789462, -1.084203);
                }
                if(selectedItem.equals("Kings Theatre")) {
                    changeMarker(50.787132,-1.080563);
                }
                if(selectedItem.equals("Festing Hotel")) {
                    changeMarker(50.7859917,-1.0790613);
                }
                if(selectedItem.equals("Eastney Health Centre")) {
                    changeMarker(50.7866247,-1.0684373);
                }
                if(selectedItem.equals("Bransbury Park")) {
                    changeMarker(50.7906797,-1.0638443);
                }
                if(selectedItem.equals("Prince Albert Road")) {
                    changeMarker(50.7903937,-1.0682483);
                }
                if(selectedItem.equals("Francis Avenue, Lidls")) {
                    changeMarker(50.7949677,-1.0782733);
                }
                if(selectedItem.equals("Fratton Bridge")) {
                    changeMarker(50.7972907,-1.0854783);
                }
                if(selectedItem.equals("Somers Road")) {
                    changeMarker(50.7944008,-1.0837426);
                }
                if(selectedItem.equals("Winston Churchill Avenue")) {
                    changeMarker(50.7950677,-1.0866836);
                }


            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }


        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //google maps api code
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);



    }

    //code for changing the marker's point and generating fastest walking route using google services
    public void changeMarker(double x, double y) {
        mMap.clear();
        LatLng lib = new LatLng(x,y);
        mMap.addMarker(new MarkerOptions().position(lib).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lib));
        place2 = new MarkerOptions().position(new LatLng(x,y)).title("Destination");
        place1 = new MarkerOptions().position(new LatLng(50.7926037,-1.0959872)).title("Origin");
        String url = getUrl(place1.getPosition(), place2.getPosition(), "walking");
        new FetchURL(MapsActivity.this).execute(url, "walking");

        mMap.addMarker(place1);
        mMap.addMarker(place2);



    }

    //method that generates the url that will be used from the google services to generate the fastest walking route using up899924's gooogle services account and api
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        //origin
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        //destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //mode
        String mode = "mode=" + directionMode;
        //full param
        String param = str_origin + "&" + str_dest + "&" + mode;
        //output format
        String output = "json";
        //url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key=" + getString(R.string.google_api_key);
        return url;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //this method has been commented because android studio has a problem when you get the virtual device's location. Some other lines have been commented too because of that and we manually adjusted the starting location by giving a fixed one. Although it has been tested on a real device and everything worked pefect.
    @Override
    public void onLocationChanged(Location location) {
        //double longtitude = location.getLongitude();
        //double latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
//this method returns to the user's screen the route coloured
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline !=null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
