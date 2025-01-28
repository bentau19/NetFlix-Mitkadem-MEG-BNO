package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ToggleButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.myapplication.R;
import com.example.myapplication.data.ThemeManager;

public class ThemesActivity extends AppCompatActivity {

    private ToggleButton themeToggle;
    private TextView themeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the theme from SharedPreferences
        loadTheme();

        setContentView(R.layout.themes);

        themeToggle = findViewById(R.id.theme_toggle);
        themeStatus = findViewById(R.id.theme_status);

        // Set initial theme status
        updateThemeStatusText();

        // Set toggle listener
        themeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newTheme = isChecked ? "dark" : "light";

            // Save theme in SharedPreferences
            saveThemeToPreferences(newTheme);

            // Apply the theme
            applyTheme(newTheme);

            // Update the status text
            updateThemeStatusText();
        });
    }

    private void loadTheme() {
        // Get saved theme from SharedPreferences
        String savedTheme = ThemeManager.getTheme(this);
        Log.d("ThemesActivity", "Saved Theme: " + savedTheme);  // Log the saved theme
        if (savedTheme != null) {
            applyTheme(savedTheme);
        }
    }

    private void saveThemeToPreferences(String theme) {
        Log.d("ThemesActivity", "Saving Theme: " + theme);  // Log the new theme being saved
        ThemeManager.saveTheme(this, theme);
    }

    private void applyTheme(String theme) {
        Log.d("ThemesActivity", "Applying Theme: " + theme);  // Log the theme being applied
        int nightMode = theme.equals("dark") ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private void updateThemeStatusText() {
        new Handler(Looper.getMainLooper()).post(() -> {
            String currentTheme = themeToggle.isChecked() ? "Dark" : "Light";
            themeStatus.setText("Current Theme: " + currentTheme);
        });
    }
}
