package com.example.myapplication.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;
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
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("promoted", String.valueOf(promoted));

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);
    }

    public MutableLiveData<List<Category>> getAllCategories() {
        MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();

        String endpoint = "categories/";  // Example endpoint for fetching categories
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Assuming the response is a List<Category>
                List<Category> categories = (List<Category>) response; // Cast based on your actual response
                categoriesLiveData.setValue(categories);
            }

            @Override
            public void onError(String error) {
                categoriesLiveData.setValue(null); // You can handle the error appropriately
            }
        });

        return categoriesLiveData;
    }

    public void fetchCategoryData(String categoryId, ApiResponseCallback callback) {
        String endpoint = "categories/" + categoryId;  // Example endpoint
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create the APIRequest for the GET method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(new ApiResponseCallback() {  // Accept Object here to handle LinkedTreeMap
            @Override
            public void onSuccess(Object response) {
                Log.d("CategoryRepository", "Full category response: " + response);  // Log the full response
                if (response instanceof LinkedTreeMap) {
                    Gson gson = new Gson();

                    // Convert response to JSON string
                    String json = gson.toJson(response);

                    // Convert JSON string to JsonObject
                    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

                    // Extract "message" object
                    JsonObject messageObject = jsonObject.getAsJsonObject("message");

                    if (messageObject != null) {
                        // Deserialize "message" into Category
                        Category category = gson.fromJson(messageObject, Category.class);

                        Log.d("CategoryRepository", "Extracted Category - ID: " + category.getId() +
                                ", Name: " + category.getName() + ", Promoted: " + category.isPromoted());

                        callback.onSuccess(category);
                    } else {
                        Log.e("CategoryRepository", "Missing 'message' field in JSON response.");
                        callback.onError("Invalid response format");
                    }
                }
                 else if (response instanceof Category) {
                    callback.onSuccess((Category) response);
                } else {
                    Log.e("CategoryRepository", "Unexpected response type: " + response.getClass().getName());
                    callback.onError("Unexpected response format");
                }
            }


            @Override
            public void onError(String error) {
                // Call the callback onError method
                callback.onError(error);
            }
        });
    }

    public ApiResponseCallback getCategories() {
        String endpoint = "categories/";  // Example endpoint for fetching user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        // Create APIRequest for GET method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(callback);
        return callback;
    }

    // Update User Data
    public void updateCategory(String categoryId, String newName, Boolean newPromoted) {
        String endpoint = "categories/" + categoryId;  // Example endpoint for updating user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("Content-Type", "application/json");
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk");

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
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk");

        // Create APIRequest for DELETE method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.delete(callback);
    }
    public void fetchCategories(String query, ApiResponseCallback callback) {
        String endpoint = query.isEmpty() ? "categories/search" : "categories/search/" + query;
        HashMap<String, String> headers = new HashMap<>(); // Add any required headers here
        APIRequest request = new APIRequest(endpoint, headers, null);
        request.get(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                callback.onSuccess(response);  // Pass the response back to the caller (e.g., ViewModel)
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Pass error back to the caller
            }
        });
    }

}
