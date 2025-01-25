package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.example.myapplication.activity.SignupActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToSignup = findViewById(R.id.button);
        Button btnGoToAdmin = findViewById(R.id.adminButton);
        Button btnGoToMain = findViewById(R.id.mainButton);

        btnGoToSignup.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        btnGoToAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        });

        btnGoToMain.setOnClickListener(view -> {
            Toast.makeText(this, "This is a test Toast", Toast.LENGTH_SHORT).show();
        });


    }
}