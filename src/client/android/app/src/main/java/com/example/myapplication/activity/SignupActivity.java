package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.viewmodel.SignupViewModel;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        EditText nameEditText = findViewById(R.id.name);
        EditText passwordEditText = findViewById(R.id.password);
        EditText usernameEditText = findViewById(R.id.username);
        Button signupButton = findViewById(R.id.login);


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

            // Call the ViewModel to perform the signup
            signupViewModel.signup(name, password, username);
        });
    }
}
