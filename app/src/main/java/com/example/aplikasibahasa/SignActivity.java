package com.example.aplikasibahasa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button buttonContinue;
    private Button buttonBack;
    private TextView textForgotPassword;

    private static final String STATIC_EMAIL = "bagus@example.com";
    private static final String STATIC_PASSWORD = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonContinue = findViewById(R.id.button_continue);
        buttonBack = findViewById(R.id.button_back);
        textForgotPassword = findViewById(R.id.text_forgot_password);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignIn();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignActivity.this, "Membuka halaman reset password...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptSignIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        editTextEmail.setError(null);
        editTextPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (password.isEmpty() || password.length() < 6) {
            editTextPassword.setError("Password minimal 6 karakter");
            focusView = editTextPassword;
            cancel = true;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email wajib diisi");
            focusView = editTextEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextEmail.setError("Format email tidak valid");
            focusView = editTextEmail;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            authenticateManual(email, password);
        }
    }

    private void authenticateManual(String email, String password) {

        if (email.equals(STATIC_EMAIL) && password.equals(STATIC_PASSWORD)) {

            Log.d(TAG, "Sign In sukses untuk: " + email);
            Toast.makeText(this, "Sign In berhasil! Selamat datang.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SignActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {
            Log.w(TAG, "Sign In gagal. Email atau password salah.");
            Toast.makeText(this, "Gagal masuk: Email atau password salah.", Toast.LENGTH_LONG).show();
            editTextEmail.requestFocus();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}