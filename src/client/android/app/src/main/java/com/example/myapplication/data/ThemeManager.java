package com.example.myapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String PREFS_NAME = "theme_preferences";
    private static final String KEY_THEME = "theme_key";

    // Get the saved theme
    public static String getTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_THEME, "light");  // Default theme is light
    }

    // Save the theme to SharedPreferences
    public static void saveTheme(Context context, String theme) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_THEME, theme);
        editor.apply();
    }


    private void applyTheme(String theme) {
        int nightMode = theme.equals("dark") ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }


}
