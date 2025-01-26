package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ui.viewmodel.LogInViewModel;

public class LogInActivity extends AppCompatActivity {
    private LogInViewModel LogInViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LogInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        LogInViewModel.getLogInStatus().observe(this, status -> {
            Toast.makeText(LogInActivity.this, status, Toast.LENGTH_SHORT).show();
            if (status.equals("LogIn successful!")) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.login).setOnClickListener(v -> {
            TextView tusername = findViewById(R.id.username);
            TextView tpassword = findViewById(R.id.password);
            String username = tusername.getText().toString();
            String password = tpassword.getText().toString();

            LogInViewModel.LogIn(username, password);


        });
    }
}
