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
import com.example.myapplication.ui.viewmodel.LogInViewModel;

public class MainActivity extends AppCompatActivity {
    private LogInViewModel LogInViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database if not already done
        try {
            AppDatabase.getInstance();
        } catch (Exception e) {
            AppDatabase.init(this);
        }

        TextView hash = findViewById(R.id.hash);
        AppDatabase db = AppDatabase.getInstance();
        TokenDao tokenDao = db.tokenDao();

        // Asynchronously fetch the token
        tokenDao.getToken().observe(this, userToken -> {
            if (userToken != null && userToken.token != null && !userToken.token.isEmpty()) {
                hash.setText(userToken.token); // Update the TextView with the token
            } else {
                hash.setText("Log in first"); // Display fallback message
            }
        });
        LogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        LogInViewModel.getLogInStatus().observe(this, status -> {
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
            if (status.equals("LogIn successful!")) {
                Intent intent = new Intent(this, loggedMain.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(this, GuestMain.class);
                startActivity(intent);
                finish();
            }
        });
        LogInViewModel.islogged();

        Button btnGoToMain = findViewById(R.id.mainButton);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, loggedMain.class);
            startActivity(intent);
        });
        // Go to Signup Activity
        Button btnGoToSignup = findViewById(R.id.button);
        btnGoToSignup.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
        Button btnGoToAdmin = findViewById(R.id.adminButton);
        btnGoToAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });

        // Go to Login Activity
        Button btnGoTologin = findViewById(R.id.button2);
        btnGoTologin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
