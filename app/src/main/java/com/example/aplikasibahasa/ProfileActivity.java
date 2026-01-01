package com.example.aplikasibahasa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView backButton;
    private TextView textUserName;
    private Button buttonLogOut;
    private ImageView profileIcon;

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final String CURRENT_USER_NAME = "Bagus aditya hermawan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        initializeViews();

        displayUserData();

        setupClickListeners();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.back_button);
        textUserName = findViewById(R.id.text_user_name);
        buttonLogOut = findViewById(R.id.button_log_out);
        profileIcon = findViewById(R.id.profile_icon);
    }

    private void displayUserData() {
        textUserName.setText(CURRENT_USER_NAME);
    }

    private void setupClickListeners() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogOut();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }

    private void handleLogOut() {
        Toast.makeText(this, "Anda telah berhasil Log Out.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ProfileActivity.this, SignActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto Profil"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            profileIcon.setImageURI(imageUri);
        }
    }
}
