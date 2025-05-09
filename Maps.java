package com.example.carpool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private final LatLng fakeUserLocation = new LatLng(51.4600, -3.1610); // Cardiff Bay
    private GoogleMap mMap;
    private MapView mapView;
    private Marker destinationMarker;
    private Circle userLocationCircle;
    private Polyline routeLine; // Variable for the polyline
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_page);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapView = findViewById(R.id.maps);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        ImageButton rideButton = findViewById(R.id.ride);
        rideButton.setOnClickListener(v -> startActivity(new Intent(Maps.this, MainActivity.class)));

        ImageButton chatButton = findViewById(R.id.chat);
        chatButton.setOnClickListener(v -> startActivity(new Intent(Maps.this, chat.class)));

        ImageButton mapButton = findViewById(R.id.mapZ);
        mapButton.setOnClickListener(v -> startActivity(new Intent(Maps.this, Maps.class)));

        ImageButton profileButton = findViewById(R.id.account);
        profileButton.setOnClickListener(v -> startActivity(new Intent(Maps.this, register.class)));

        EditText destinationInput = findViewById(R.id.destination);
        ImageButton searchButton = findViewById(R.id.search_button);
        LinearLayout etaContainer = findViewById(R.id.driver_eta_container);
        TextView etaMessage = findViewById(R.id.eta_message);
        ProgressBar progressBar = findViewById(R.id.eta_progress_bar);

        searchButton.setOnClickListener(v -> {
            String destination = destinationInput.getText().toString();
            if (!destination.isEmpty()) {
                Geocoder geocoder = new Geocoder(Maps.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocationName(destination, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        LatLng destLatLng = new LatLng(address.getLatitude(), address.getLongitude());

                        if (destinationMarker != null) {
                            destinationMarker.remove();
                        }
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(destLatLng).title(destination));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destLatLng, 17));

                        // Add a line (Polyline) from the fake user location to the destination
                        if (routeLine != null) {
                            routeLine.remove(); // Remove old polyline if exists
                        }
                        routeLine = mMap.addPolyline(new PolylineOptions()
                                .add(fakeUserLocation, destLatLng) // Add the start and end points
                                .width(5) // Line width
                                .color(0xFF2196F3)); // Blue color for the line

                        etaContainer.setVisibility(View.VISIBLE);
                        etaMessage.setText("Driver arriving in 5 mins...");
                        progressBar.setProgress(0);
                        new Thread(() -> {
                            for (int i = 0; i <= 100; i++) {
                                int progress = i;
                                runOnUiThread(() -> progressBar.setProgress(progress));
                                try {
                                    Thread.sleep(50); // Smooth fill
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } else {
                        Toast.makeText(Maps.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Maps.this, "Geocoding failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Maps.this, "Enter a destination", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(false); // Disable real-time blue dot

        // Draw fake user location circle
        userLocationCircle = mMap.addCircle(new CircleOptions()
                .center(fakeUserLocation)
                .radius(500) // 500 meters radius
                .strokeColor(0xFF2196F3)   // Blue stroke
                .fillColor(0x552196F3)     // Transparent blue fill
                .strokeWidth(10f));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fakeUserLocation, 15)); // Move camera to user location
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapView.getMapAsync(this);
        }
    }

    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { super.onPause(); mapView.onPause(); }
    @Override protected void onDestroy() { super.onDestroy(); mapView.onDestroy(); }
    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
