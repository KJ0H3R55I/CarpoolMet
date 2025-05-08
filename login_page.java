package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class login_page extends AppCompatActivity {

    MaterialButton driverButton, studentButton, visitorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page); // Replace with your actual layout file name

        driverButton = findViewById(R.id.DriverButton);
        studentButton = findViewById(R.id.studentButton);
        visitorButton = findViewById(R.id.VisitorButton);

        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Replace with your actual activity
                Intent intent = new Intent(login_page.this, Maps.class);
                startActivity(intent);
            }
        });

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, Maps.class);
                startActivity(intent);
            }
        });

        visitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, Maps.class);
                startActivity(intent);
            }
        });
        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, login_page.class);
                startActivity(intent);
            }
        });
        ImageButton rideButton = findViewById(R.id.ride);
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton chatButton = findViewById(R.id.chat);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, chat.class);
                startActivity(intent);
            }
        }); ImageButton mapButton = findViewById(R.id.maps);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, Maps.class);
                startActivity(intent);
            }
        });
        ImageButton profileButton = findViewById(R.id.account);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, register.class);
                startActivity(intent);
            }
        });

    }
}

