package com.example.myapplication.data.repository;

import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.adapter.User;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.Rooms.entity.UserToken;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class UserRepository {
    private final ApiResponseCallback callback;
    private TokenDao Token;
    private AppDatabase db;
    public UserRepository(ApiResponseCallback callback) {
        this.callback = callback;
        db = AppDatabase.getInstance();
        Token = db.tokenDao();

    }



    // Signup User
    public void signup(String username, String password, String userName,String imageBase64) {
        String endpoint = "users/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("displayName", username);
        jsonBody.put("password", password);
        jsonBody.put("userName", userName);
        jsonBody.put("image",imageBase64);
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);

    }


    public void getUser() {
        AppDatabase db = AppDatabase.getInstance();
        TokenDao tokenDao = db.tokenDao();

        // Use ExecutorService to run the database operation on a background thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String userToken = tokenDao.getTokenString(); // Run in background thread

            // Now we can check if the token is null or empty
            if (userToken == null || userToken.isEmpty()) {
                // Make sure to call callback.onError() on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("No token found. User not logged in.");
                });
                return;
            }

            // Prepare API request on background thread
            String endpoint = "tokens";
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("token", userToken);

            APIRequest apiRequest = new APIRequest(endpoint, headers, null);
            apiRequest.get(new ApiResponseCallback() {
                @Override
                public void onSuccess(Object response) {
                    if (response instanceof LinkedTreeMap) {
                        // Convert LinkedTreeMap to JSON string
                        Gson gson = new Gson();
                        String json = gson.toJson(response);

                        // Deserialize JSON into Movie object
                        User user = gson.fromJson(json, User.class);

                        // Pass the Movie object to the callback
                        callback.onSuccess(user);
                    } else if (response instanceof User) {
                        // Already a Movie object, just return it
                        callback.onSuccess(response);
                    } else {
                        Log.e("MovieRepository", "Unexpected response type: " + response.getClass().getName());
                        callback.onError("Unexpected response format");
                    }
                }

                @Override
                public void onError(String error) {
                    // Handle error on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onError("Invalid or expired token.");
                    });
                }
            });
        });
    }

    public void isLogged() {
        AppDatabase db = AppDatabase.getInstance();
        TokenDao tokenDao = db.tokenDao();

        // Use ExecutorService to run the database operation on a background thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String userToken = tokenDao.getTokenString(); // Run in background thread

            // Now we can check if the token is null or empty
            if (userToken == null || userToken.isEmpty()) {
                // Make sure to call callback.onError() on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("No token found. User not logged in.");
                });
                return;
            }

            // Prepare API request on background thread
            String endpoint = "tokens";
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("token", userToken);

            APIRequest apiRequest = new APIRequest(endpoint, headers, null);
            apiRequest.get(new ApiResponseCallback() {
                @Override
                public void onSuccess(Object response) {
                    // Handle success on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onSuccess("logged");
                    });
                }

                @Override
                public void onError(String error) {
                    // Handle error on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onError("Invalid or expired token.");
                    });
                }
            });
        });
    }

    public void login(String userName, String password) {
        String endpoint = "tokens";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("userName", userName);
        jsonBody.put("password", password);
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Assuming the response contains a token as a string
                String token = (String) response;  // Modify according to your response format
                UserToken t = new UserToken();
                t.token = token;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Token.insertToken(t);  // Insert token into the database
                        callback.onSuccess(response);  // Notify success on the main thread
                    }
                }).start();

            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });

    }

}

