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
    private boolean checkpass(String password, String repeat){
        if(!password.equals(repeat)){
            Toast.makeText(SignupActivity.this, "Incorrect Repeat!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(SignupActivity.this, "Password must be at least 8 characters long!", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            Toast.makeText(SignupActivity.this, "Password must contain both letters and numbers!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        EditText nameEditText = findViewById(R.id.name);
        EditText passwordEditText = findViewById(R.id.password);
        EditText usernameEditText = findViewById(R.id.UserName);
        EditText repeatpassEdit = findViewById(R.id.Reppassword);
        Button signupButton = findViewById(R.id.login);


        signupViewModel.getSignupStatus().observe(this, status -> {
            Toast.makeText(SignupActivity.this, status, Toast.LENGTH_SHORT).show();
            if (status.equals("Signup successful!")) {
                Intent intent = new Intent(SignupActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Hand le the signup button click
        signupButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String Rep = repeatpassEdit.getText().toString();
            if(checkpass(password,Rep)){
                signupViewModel.signup(name, password, username);
            }

        });
    }
}
