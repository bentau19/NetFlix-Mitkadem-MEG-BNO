package com.example.myapplication.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {

    private final ApiResponseCallback callback;

    public MovieRepository(ApiResponseCallback callback) {
        this.callback = callback;
    }
    private List<Integer> processCategories(String categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }

        // Split categories by commas, trim spaces, and remove empty values
        String[] categoriesArray = categories.split(",");
        List<Integer> processedCategories = new ArrayList<>();

        for (String category : categoriesArray) {
            String trimmedCategory = category.trim();
            if (!trimmedCategory.isEmpty()) {
                try {
                    processedCategories.add(Integer.parseInt(trimmedCategory)); // Convert to integer
                } catch (NumberFormatException e) {
                    // Handle invalid category ID
                  //  showToast("Invalid category ID: " + trimmedCategory);
                }
            }
        }

        return processedCategories;
    }
    // Signup User
    public void createMovie(String title, String logline, String imageHex, String categories) {
        String endpoint = "movies/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", "your-jwt-token-here");

        // Create a FormData object with the image hex and other form fields
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("title", title);
        jsonBody.put("logline", logline);
        jsonBody.put("image", imageHex);

        // Process categories into a List<Integer>
        List<Integer> processedCategories = processCategories(categories);

        // Convert the List<Integer> into a JSON array string
        String categoriesJsonString = new JSONArray(processedCategories).toString();

        // Put the categories as a string in the JSON body
        jsonBody.put("categories", categoriesJsonString);

        // Create and send the API request
        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);

        // Make the POST request
        apiRequest.post(new ApiResponseCallback() {
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


    public void fetchMovieData(String movieId, ApiResponseCallback callback) {
        String endpoint = "movies/" + movieId;  // Example endpoint for fetching user data
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
    public void getMovies() {
        String endpoint = "movies/query";  // Example endpoint for fetching user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        // Create APIRequest for GET method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(callback);
    }
    public MutableLiveData<List<Movie>> getAllMovies() {
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();

        String endpoint = "movies/";  // Example endpoint for fetching categories
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");

        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Assuming the response is a List<Category>
                List<Movie> movies = (List<Movie>) response; // Cast based on your actual response
                moviesLiveData.setValue(movies);
            }

            @Override
            public void onError(String error) {
                moviesLiveData.setValue(null); // You can handle the error appropriately
            }
        });

        return moviesLiveData;
    }

    // Update User Data
    public void updateMovie(String movieId, String newTitle, String newLogline, String newImage , String newCategories) {
        String endpoint = "movies/" + movieId;  // Example endpoint for updating user data
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("Content-Type", "application/json");

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("title", newTitle);
        jsonBody.put("logline", newLogline);
        jsonBody.put("image", newImage);
        JSONArray categoriesArray = new JSONArray();
        if (newCategories != null && !newCategories.isEmpty()) {
            String[] categoryIds = newCategories.split(",");
            for (String id : categoryIds) {
                id = id.trim(); // Remove whitespace
                if (!id.isEmpty()) {
                    categoriesArray.put(id);
                }
            }
        }
        jsonBody.put("categories", categoriesArray.toString()); // Include the categories array

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.put(callback);
    }

    // Delete User
    public void deleteMovie(String movieId) {
        String endpoint = "movies/" + movieId;  // Example endpoint for deleting user
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk");

        // Create APIRequest for DELETE method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.delete(callback);
    }

    public void fetchMovies(String query, ApiResponseCallback callback) {
        String endpoint = query.isEmpty() ? "movies/search" : "movies/search/" + query;
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
