package com.example.finalproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    SensorManager sensorManager;
    ZoomControls zoomControls;
    TextView resultText;

    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap.OnMapClickListener onMapClickListener;
    private GoogleMap.OnMarkerClickListener onMarkerClickListener;
    private GoogleMap.OnGroundOverlayClickListener onGroundOverlayClickListener;
    private GroundOverlay overlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        resultText = (TextView) findViewById(R.id.textView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        // initialize the map system and the view
        mapFragment.getMapAsync(this);


        zoomControls = (ZoomControls) findViewById(R.id.mapZoomControls);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.zoomOut());
            }
        });

        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = googleMap.getCameraPosition().target;
                String result = "Lat: " + latLng.latitude + "\nLng: " + latLng.longitude;
                resultText.setText(result);
            }
        };
        onMapClickListener = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                String result = "Lat: " + arg0.latitude + "\nLng: " + arg0.longitude;
                resultText.setText(result);

            }
        };
        onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                LatLng position = arg0.getPosition();
                String result = "Latitude: " + position.latitude + "\nLongitude: " + position.longitude;
                resultText.setText(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                return true;
            }
        };


        onGroundOverlayClickListener = new GoogleMap.OnGroundOverlayClickListener() {
            @Override
            public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                String result = "Ground overlay clicked";
                String overlayTag = (String) groundOverlay.getTag();
                if (overlayTag == "Love") {
                    Toast.makeText(getApplicationContext(), overlayTag, Toast.LENGTH_LONG).show();
                    resultText.setText(result);
                }
            }
        };

    }

    // To manipulate map once available
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;

        // touch gestures
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        // listeners for click
        googleMap.setOnCameraIdleListener(onCameraIdleListener);
        googleMap.setOnMapClickListener(onMapClickListener);
        googleMap.setOnMarkerClickListener(onMarkerClickListener);
        googleMap.setOnGroundOverlayClickListener(onGroundOverlayClickListener);

        //Location
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Check proper permissions and enable setMyLocation
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // to customize map
        /*
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.design));

            if (!success) {
                Log.e("styleCheck", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("styleCheck", "Can't find style. Error: ", e);
        }

         */
        //Satellite Mode
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add LatLng objects
        LatLng sydney = new LatLng(-34, 151);

        // To animate:

        CameraPosition newCamPos = new CameraPosition(sydney, 14f,
                googleMap.getCameraPosition().tilt, //use old tilt
                googleMap.getCameraPosition().bearing); //use old bearing
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 4000, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settingsButton) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.homeButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}
