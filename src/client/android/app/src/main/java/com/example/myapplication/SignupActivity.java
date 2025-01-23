package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ui.viewmodel.SignupViewModel;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        EditText usernameEditText = findViewById(R.id.name);
        EditText passwordEditText = findViewById(R.id.Password);
        EditText emailEditText = findViewById(R.id.email);
        Button signupButton = findViewById(R.id.login);


        signupViewModel.getSignupStatus().observe(this, status -> {
            // Show toast message based on signup status
            Toast.makeText(SignupActivity.this, status, Toast.LENGTH_SHORT).show();
        });

        // Hand le the signup button click
        signupButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();

            // Call the ViewModel to perform the signup
            signupViewModel.signup(username, password, email);
        });
    }
}
