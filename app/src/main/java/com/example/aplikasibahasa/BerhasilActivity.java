package com.example.aplikasibahasa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BerhasilActivity extends AppCompatActivity {

    Button buttonKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.berhasil);

        buttonKembali = findViewById(R.id.button_kembali);

        buttonKembali.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        Intent intent = new Intent(BerhasilActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
