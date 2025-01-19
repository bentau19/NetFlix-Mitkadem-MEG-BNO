package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.MediaType;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button signupButton;

    // ExecutorService to manage background threads
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Initialize views
        usernameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.Password);
        emailEditText = findViewById(R.id.email);
        signupButton = findViewById(R.id.signup);

        // Set OnClickListener for signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Call method to send POST request
                sendSignupRequest(username, password, email);
            }
        });
    }

    private void sendSignupRequest(String username, String password, String email) {
        // Use ExecutorService to run the network request on a background thread
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Perform network operation on background thread
                String result = performSignupRequest(username, password, email);

                // Send result back to the main thread to update UI (Toast message)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String performSignupRequest(String username, String password, String email) {
        OkHttpClient client = new OkHttpClient();
        String result = "";

        // Create JSON body for the POST request
        String json = "{ \"name\": \"" + username + "\", \"password\": \"" + password + "\", \"email\": \"" + email + "\" }";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        // Create POST request
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/api/users")  // Replace with your server's URL
                .post(body)
                .build();

        try {
            // Send the request and get the response (this happens on background thread)
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                result = "Signup successful!";
            } else {
                result = "Signup failed. Please try again.";
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = "Network error. Please try again later.";
        }

        return result;
    }
}
