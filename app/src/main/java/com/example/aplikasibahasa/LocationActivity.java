package com.example.aplikasibahasa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_DISPLAY_TIME_OUT = 6000;
    private static final int LOCATION_PERMISSION_REQUEST = 100;

    private CardView smsNotif;
    private TextView greetingNotif;
    private Handler handler = new Handler();

    private FusedLocationProviderClient fusedLocationClient;

    private TextView locationInfoTextView, welcomeTextView;
    private ImageView flagImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        // View
        locationInfoTextView = findViewById(R.id.location_info);
        welcomeTextView = findViewById(R.id.welcome_text);
        flagImageView = findViewById(R.id.flag_image);
        smsNotif = findViewById(R.id.smsNotif);
        greetingNotif = findViewById(R.id.greeting_notif);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Cek permission GPS
        if (checkLocationPermission()) {
            getLocationFromGPS();
        } else {
            requestLocationPermission();
        }

        // Pindah ke LoginActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(LocationActivity.this, LoginActivity.class));
            finish();
        }, LOCATION_DISPLAY_TIME_OUT);
    }

    // ===================== LOCATION =====================

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST
        );
    }

    private void getLocationFromGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        detectCountryFromGPS(location);
                    } else {
                        detectByLanguage();
                    }
                })
                .addOnFailureListener(e -> detectByLanguage());
    }

    private void detectCountryFromGPS(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1
            );

            if (addresses == null || addresses.isEmpty()) {
                detectByLanguage();
                return;
            }

            Address address = addresses.get(0);
            String countryCode = address.getCountryCode(); // ID / US

            if ("ID".equals(countryCode)) {
                setIndonesiaUI();
            } else if ("US".equals(countryCode)) {
                setUSAUI();
            } else {
                setDefaultUI(address.getCountryName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            detectByLanguage();
        }
    }

    // ===================== UI SETTER =====================

    private void setIndonesiaUI() {
        locationInfoTextView.setText("Lokasi Anda: Indonesia");
        welcomeTextView.setText("Selamat Datang!");
        flagImageView.setImageResource(R.drawable.indonesia_flag);
        showSmsNotification("id");
    }

    private void setUSAUI() {
        locationInfoTextView.setText("Your Location: USA");
        welcomeTextView.setText("Welcome!");
        flagImageView.setImageResource(R.drawable.flag_usa);
        showSmsNotification("en");
    }

    private void setDefaultUI(String countryName) {
        locationInfoTextView.setText("Your Location: " + countryName);
        welcomeTextView.setText("Welcome!");
        showSmsNotification(Locale.getDefault().getLanguage());
    }

    private void detectByLanguage() {
        String lang = Locale.getDefault().getLanguage();

        if (lang.equals("id") || lang.equals("in")) {
            setIndonesiaUI();
        } else {
            setUSAUI();
        }
    }

    // ===================== SMS NOTIFICATION =====================

    private void showSmsNotification(String lang) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 5 && hour < 11) {
            greeting = (lang.equals("id") || lang.equals("in"))
                    ? "Selamat Pagi 🌤️"
                    : "Good Morning 🌤️";
        } else if (hour >= 11 && hour < 15) {
            greeting = (lang.equals("id") || lang.equals("in"))
                    ? "Selamat Siang ☀️"
                    : "Good Afternoon ☀️";
        } else if (hour >= 15 && hour < 18) {
            greeting = (lang.equals("id") || lang.equals("in"))
                    ? "Selamat Sore 🌇"
                    : "Good Evening 🌇";
        } else {
            greeting = (lang.equals("id") || lang.equals("in"))
                    ? "Selamat Malam 🌙"
                    : "Good Night 🌙";
        }

        greetingNotif.setText(greeting);

        smsNotif.setVisibility(View.VISIBLE);
        smsNotif.setAlpha(1f);
        smsNotif.setTranslationY(-200);
        smsNotif.animate().translationY(0).setDuration(500);

        handler.postDelayed(() -> {
            smsNotif.animate()
                    .translationY(-200)
                    .alpha(0)
                    .setDuration(500)
                    .withEndAction(() -> smsNotif.setVisibility(View.GONE));
        }, 2000);
    }

    // ===================== PERMISSION RESULT =====================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationFromGPS();
            } else {
                detectByLanguage();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
