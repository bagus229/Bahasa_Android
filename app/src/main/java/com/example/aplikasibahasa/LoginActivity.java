package com.example.aplikasibahasa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signInButton = findViewById(R.id.button_sign_in);
        createAccountButton = findViewById(R.id.button_create_account);

        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignActivity.class);
            startActivity(intent);
        });

        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CreateActivity.class);
            startActivity(intent);
        });
    }
}