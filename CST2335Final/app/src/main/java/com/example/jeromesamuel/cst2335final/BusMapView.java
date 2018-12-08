package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity Name: BusMapView
 *
 * This activity is used for displaying the location of the bus that the user is querying on Google Maps
 * @author Jerome Samuel
 * @version 1.2
 *
 */

public class BusMapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
Float lat;
Float lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker on the current bus selected
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String lati = getIntent().getStringExtra("latitude");
        String longi = getIntent().getStringExtra("longitude");
        mMap.setMinZoomPreference(17f);

        try {
            Log.i("latVal", lati);
            Log.i("longval", longi);
            lat = Float.parseFloat(lati);
            lon = Float.parseFloat(longi);

            LatLng bus = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(bus).title("Bus Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bus));

        }
        catch(NumberFormatException e){
            Log.i("Error:", "NumberFormatException");
            LatLng bus = new LatLng(0,0);
            mMap.addMarker(new MarkerOptions().position(bus).title("Bus Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bus));
            Toast.makeText(this, "Bus could not be located", Toast.LENGTH_SHORT).show();

        }


    }
}
