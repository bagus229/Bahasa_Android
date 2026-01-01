package com.example.aplikasibahasa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";


    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonContinue;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonContinue = findViewById(R.id.button_continue);
        buttonBack = findViewById(R.id.button_back);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreateAccount();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void attemptCreateAccount() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        editTextName.setError(null);
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

        if (name.isEmpty()) {
            editTextName.setError("Nama wajib diisi");
            focusView = editTextName;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            Log.d(TAG, "New Account Validated: Name=" + name + ", Email=" + email);

            Toast.makeText(this, "Pembuatan Akun berhasil! Menyambut pengguna baru.", Toast.LENGTH_LONG).show();

            navigateToHomeActivity();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(CreateActivity.this, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}