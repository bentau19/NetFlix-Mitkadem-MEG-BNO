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
        ThemeManager.loadTheme(this);

        setContentView(R.layout.themes);

        themeToggle = findViewById(R.id.theme_toggle);
        themeStatus = findViewById(R.id.theme_status);


        // Set toggle listener
        themeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newTheme = isChecked ? "dark" : "light";
            ThemeManager.saveThemeToPreferences(this, newTheme);  // or "light" / "system"
            recreate();  // Refresh activity to apply new theme

        });
    }


}
