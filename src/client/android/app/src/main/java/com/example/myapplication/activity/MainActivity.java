package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AdminActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.Rooms.entity.UserToken;

public class MainActivity extends AppCompatActivity {

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

        // Go to Signup Activity
        Button btnGoToSignup = findViewById(R.id.button);
        btnGoToSignup.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
        Button btnGoToAdmin = findViewById(R.id.adminButton);
        btnGoToAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        });

        // Go to Login Activity
        Button btnGoTologin = findViewById(R.id.button2);
        btnGoTologin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
        });
    }
}
