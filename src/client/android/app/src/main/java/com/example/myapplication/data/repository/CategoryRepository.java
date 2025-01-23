package com.example.myapplication.data.repository;

import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CategoryRepository {

    private final ApiResponseCallback callback;

    public CategoryRepository(ApiResponseCallback callback) {
        this.callback = callback;
    }

    // Signup User
    public void createCategory(String name, Boolean promoted) {
        String endpoint = "categories/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("promoted", String.valueOf(promoted));

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);
    }


    public void fetchCategoryData(String categoryId) {
        String endpoint = "categories/" + categoryId;  // Example endpoint for fetching user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create APIRequest for GET method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(callback);
    }

    // Update User Data
    public void updateCategory(String categoryId, String newName, Boolean newPromoted) {
        String endpoint = "categories/" + categoryId;  // Example endpoint for updating user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", newName);
        jsonBody.put("promoted", String.valueOf(newPromoted));

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.patch(callback);
    }

    // Delete User
    public void deleteCategory(String categoryId) {
        String endpoint = "categories/" + categoryId;  // Example endpoint for deleting user
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create APIRequest for DELETE method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.delete(callback);
    }

}
