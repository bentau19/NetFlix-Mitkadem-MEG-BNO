package com.example.myapplication.data.repository;

import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final ApiResponseCallback callback;

    public UserRepository(ApiResponseCallback callback) {
        this.callback = callback;
    }

    // Signup User
    public void signup(String username, String password, String email) {
        String endpoint = "users/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", username);
        jsonBody.put("password", password);
        jsonBody.put("email", email);

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);
    }

    public void login(String password, String email) {
        String endpoint = "users/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.get(callback);
    }

    public void fetchUserData(String userId) {
        String endpoint = "users/" + userId;  // Example endpoint for fetching user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create APIRequest for GET method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(callback);
    }

    // Update User Data
    public void updateUser(String userId, String newEmail) {
        String endpoint = "users/" + userId;  // Example endpoint for updating user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("Content-Type", "application/json");

        // Create JSON body for user update
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("email", newEmail);

        // Create APIRequest for PUT method
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.put(callback);
    }

    // Delete User
    public void deleteUser(String userId) {
        String endpoint = "users/" + userId;  // Example endpoint for deleting user
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create APIRequest for DELETE method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.delete(callback);
    }
}

