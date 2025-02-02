package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.AdminActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.ThemesActivity;
import com.example.myapplication.data.Rooms.entity.UserToken;
import com.example.myapplication.data.ThemeManager;
import com.example.myapplication.ui.viewmodel.LogInViewModel;

public class MainActivity extends AppCompatActivity {
    private LogInViewModel LogInViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThemeManager.loadTheme(this);
        // Initialize the database if not already done
        try {
            AppDatabase.getInstance();
        } catch (Exception e) {
            AppDatabase.init(this);
        }

        LogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        LogInViewModel.getLogInStatus().observe(this, status -> {
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            if (status.equals("LogIn successful!")) {
                Intent intent = new Intent(this, loggedMain.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, GuestMain.class);
                startActivity(intent);
            }
        });
        LogInViewModel.islogged();
    }
}
