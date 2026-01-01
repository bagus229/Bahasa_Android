package com.example.aplikasibahasa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class HalamansatuActivity extends AppCompatActivity {

    Button btnSoal, btnA, btnB;
    TextView tvNomor;
    ImageView iconHome;

    String[] soals = {"Run", "Eat", "Jump"};
    String[] jawabanA = {"Lari", "Makan", "Lompat"};
    String[] jawabanB = {"Tidur", "Minum", "Duduk"};
    int[] kunci = {1, 1, 1};

    int indexSoal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halamansatu);

        btnSoal = findViewById(R.id.btnSoal);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        tvNomor = findViewById(R.id.tvNomor);
        iconHome = findViewById(R.id.iconHome); // <<< FIX: pakai ImageView

        iconHome.setOnClickListener(v -> {
            Intent home = new Intent(HalamansatuActivity.this, HomeActivity.class);
            startActivity(home);
        });

        btnSoal.setOnClickListener(v -> {
            indexSoal = new Random().nextInt(soals.length);

            btnSoal.setText(soals[indexSoal]);
            btnA.setText(jawabanA[indexSoal]);
            btnB.setText(jawabanB[indexSoal]);

            tvNomor.setText((indexSoal + 1) + "/3");
        });

        btnA.setOnClickListener(v -> cekJawaban(1));
        btnB.setOnClickListener(v -> cekJawaban(2));
    }

    void cekJawaban(int pilihan) {
        if (pilihan == kunci[indexSoal]) {
            Intent i = new Intent(HalamansatuActivity.this, SatuActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Salah! Coba lagi", Toast.LENGTH_SHORT).show();
        }
    }
}
