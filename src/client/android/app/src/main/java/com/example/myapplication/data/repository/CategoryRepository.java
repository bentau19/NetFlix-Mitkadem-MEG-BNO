package com.example.myapplication.data.repository;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
                Log.d(TAG, "onSuccess: " + response);

                if (response instanceof List<?>) {
                    List<Category> categories = new ArrayList<>();

                    Gson gson = new Gson();
                    for (Object obj : (List<?>) response) {
                        if (obj instanceof LinkedTreeMap) {
                            // Convert LinkedTreeMap to JSON and then to Category
                            String json = gson.toJson(obj);
                            Category category = gson.fromJson(json, Category.class);
                            categories.add(category);
                        }
                    }

                    Log.d(TAG, "Parsed categories count: " + categories.size());
                    categoriesLiveData.setValue(categories);
                } else {
                    Log.e("API_ERROR", "Unexpected response type: " + response.getClass().getName());
                    categoriesLiveData.setValue(null);
                }
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
        apiRequest.get(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Call the callback onSuccess method
                callback.onSuccess(response);
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
