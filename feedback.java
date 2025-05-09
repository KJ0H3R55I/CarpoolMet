package com.example.carpool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class feedback extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton mapsButton = findViewById(R.id.mapZ);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] locations = {
                        "Beaumaris Castle, Beaumaris, UK",
                };
                StringBuilder geoUriBuilder = new StringBuilder("geo:0,0?q=");
                for (int i = 0; i < locations.length; i++) {
                    geoUriBuilder.append(Uri.encode(locations[i]));
                    if (i < locations.length - 1) {
                        geoUriBuilder.append("|");
                    }
                }
                String geoUri = geoUriBuilder.toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(feedback.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        ImageButton accountButton = findViewById(R.id.account);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback.this, register.class);
                startActivity(intent);
            }
        });

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
