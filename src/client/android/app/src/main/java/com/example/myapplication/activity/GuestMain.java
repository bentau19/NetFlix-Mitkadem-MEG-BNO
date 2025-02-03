package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.R;
import com.example.myapplication.data.ThemeManager;

public class GuestMain extends AppCompatActivity {
  private ImageView heroImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_main);

        Button btnGoToSignup = findViewById(R.id.Signup);
        btnGoToSignup.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
        Button btnGoTologin = findViewById(R.id.loginmain);
        btnGoTologin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        });
        applyImageBasedOnTheme();

    }
    private void applyImageBasedOnTheme() {
        // Get the current theme from SharedPreferences
        String theme = ThemeManager.getTheme(this);

        if (theme.equals("dark")) {
            heroImage.setImageResource(R.drawable.meg_purple);
        } else if (theme.equals("light")) {
            heroImage.setImageResource(R.drawable.meg_red);
        } else {
            // Default to light mode if theme is not set properly
            heroImage.setImageResource(R.drawable.meg_red);
        }
    }
}