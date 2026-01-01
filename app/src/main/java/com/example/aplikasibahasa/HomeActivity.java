package com.example.aplikasibahasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    ImageButton settingsButton;
    CardView btnEnglish, btnIndonesia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        settingsButton = findViewById(R.id.settings_button);
        btnEnglish = findViewById(R.id.btn_english);
        btnIndonesia = findViewById(R.id.btn_indonesia);

        settingsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Menu pengaturan dibuka", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        btnEnglish.setOnClickListener(v -> {
            Toast.makeText(this, "Bahasa Inggris dipilih", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, HalamansatuActivity.class);
            startActivity(intent);
        });

        btnIndonesia.setOnClickListener(v -> {
            Toast.makeText(this, "Bahasa Indonesia dipilih", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, IndonesiaActivity.class);
            startActivity(intent);
        });
    }
}
