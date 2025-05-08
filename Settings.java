package com.example.carpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private float currentTextSize = 22f;

    private TextView tvChangeLanguage, textSizeLabel;
    private Button deleteButton;
    private ImageButton homeButton, mapsButton, locationsButton, accountButton;
    private Switch languageSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialise views
        tvChangeLanguage = findViewById(R.id.tvChangeBackground); // Using your XML ID
        textSizeLabel = findViewById(R.id.textSize);
        deleteButton = findViewById(R.id.button);

        homeButton = findViewById(R.id.home);
        mapsButton = findViewById(R.id.maps);
        locationsButton = findViewById(R.id.ride); // Assuming "ride" = locations
        accountButton = findViewById(R.id.account);

        languageSwitch = findViewById(R.id.bcswitch); // This is your switch in XML

        // Set initial text size
        setTextSize(currentTextSize);

        // Home button functionality
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
        });

        // Maps button functionality
        mapsButton.setOnClickListener(v -> {
            String location = "Western Avenue,Cardiff, UK";
            String geoUri = "geo:0,0?q=" + Uri.encode(location);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(Settings.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }
        });

        // Account button functionality
        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.this, register.class);
            startActivity(intent);
        });

        // Text size increase
        ImageButton buttonIncreaseTextSize = findViewById(R.id.btnisize);
        buttonIncreaseTextSize.setOnClickListener(v -> {
            currentTextSize += 4f;
            setTextSize(currentTextSize);
        });

        // Text size decrease
        ImageButton buttonDecreaseTextSize = findViewById(R.id.buttonDecreaseTextSize);
        buttonDecreaseTextSize.setOnClickListener(v -> {
            if (currentTextSize > 10f) {
                currentTextSize -= 4f;
                setTextSize(currentTextSize);
            }
        });
    }

    // Apply new text size to all relevant views
    private void setTextSize(float textSize) {
        tvChangeLanguage.setTextSize(textSize);
        textSizeLabel.setTextSize(textSize);
        deleteButton.setTextSize(textSize);
    }

    // Optional language change method (not triggered yet)
    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        createConfigurationContext(config);

        SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", langCode);
        editor.apply();

        getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
    }

    private String loadLanguage() {
        SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        return prefs.getString("Language", "en");
    }
}
