package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Rider_Page extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_page);

        mapView = findViewById(R.id.maps);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        // Navigation buttons
        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> startActivity(new Intent(this, login_page.class)));

        ImageButton rideButton = findViewById(R.id.ride);
        rideButton.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        ImageButton chatButton = findViewById(R.id.chat);
        chatButton.setOnClickListener(v -> startActivity(new Intent(this, chat.class)));

        ImageButton mapButton = findViewById(R.id.maps);
        mapButton.setOnClickListener(v -> startActivity(new Intent(this, Maps.class)));

        ImageButton profileButton = findViewById(R.id.account);
        profileButton.setOnClickListener(v -> startActivity(new Intent(this, register.class)));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Configure your map here
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    // Required lifecycle methods
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
