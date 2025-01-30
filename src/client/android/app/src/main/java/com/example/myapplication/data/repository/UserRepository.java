package com.example.myapplication.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.Rooms.entity.UserToken;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

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
    public void signup(String username, String password, String userName) {
        String endpoint = "users/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("displayName", username);
        jsonBody.put("password", password);
        jsonBody.put("userName", userName);

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);

    }

    public void login(String password, String userName) {
        String endpoint = "categories";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("userName", userName);
        jsonBody.put("password", password);
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.get(new ApiResponseCallback() {
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

