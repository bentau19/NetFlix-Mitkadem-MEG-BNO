package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.viewmodel.SignupViewModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;
    private ImageView profileImageView;
    private Button selectImageButton;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        EditText nameEditText = findViewById(R.id.name);
        EditText passwordEditText = findViewById(R.id.password);
        EditText usernameEditText = findViewById(R.id.UserName);
        EditText repeatpassEdit = findViewById(R.id.Reppassword);
        Button signupButton = findViewById(R.id.login);

        // Initialize ImageView and Button
        selectImageButton = findViewById(R.id.selectImageButton);
        profileImageView = findViewById(R.id.profileImageView);
        // Handle image selection
        selectImageButton.setOnClickListener(v -> openImagePicker());

        signupViewModel.getSignupStatus().observe(this, status -> {
            Toast.makeText(SignupActivity.this, status, Toast.LENGTH_SHORT).show();
            if (status.equals("Signup successful!")) {
                Intent intent = new Intent(SignupActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle the signup button click
        signupButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String Rep = repeatpassEdit.getText().toString();

            if (checkpass(password, Rep)) {
                // Convert image to Base64 if available
                String imageBase64 = null;
                if (imageUri != null) {
                    try {
                        imageBase64 = encodeImageToBase64(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Pass the image to the ViewModel
                signupViewModel.signup(name, password, username, imageBase64);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            // Display the selected image in the ImageView
            profileImageView.setImageURI(imageUri);
        }
    }

    private String encodeImageToBase64(Uri imageUri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private boolean checkpass(String password, String repeat) {
        if (!password.equals(repeat)) {
            Toast.makeText(SignupActivity.this, "Incorrect Repeat!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(SignupActivity.this, "Password must be at least 8 characters long!", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            Toast.makeText(SignupActivity.this, "Password must contain both letters and numbers!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}