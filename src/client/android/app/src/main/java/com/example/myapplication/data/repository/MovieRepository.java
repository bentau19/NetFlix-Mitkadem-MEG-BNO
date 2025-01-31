package com.example.myapplication.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {

    private final ApiResponseCallback callback;

    public MovieRepository(ApiResponseCallback callback) {
        this.callback = callback;
    }

    // Signup User
    public void createMovie(String title, String logline, String image, String categories) {
        String endpoint = "movies/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjQsInVzZXJOYW1lIjoiaGgiLCJhZG1pbiI6dHJ1ZSwiaWF0IjoxNzM3NjcxNzQwLCJleHAiOjE3MzgyNzY1NDB9.lrAoaumgyCMFm472E0LoXpxMuImnTCmJsEqqVSR7Njk");

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("title", title);
        jsonBody.put("logline", logline);
        jsonBody.put("image", image);

        JSONArray categoriesArray = new JSONArray();
        if (categories != null && !categories.isEmpty()) {
            String[] categoryIds = categories.split(",");
            for (String id : categoryIds) {
                id = id.trim();
                if (!id.isEmpty()) {
                    categoriesArray.put(id);
                }
            }
        }
        jsonBody.put("categories", categoriesArray);

        APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
        apiRequest.post(callback);
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

    public void userRecommends(ApiResponseCallback callback) {
        String endpoint = "movies/";  // Example endpoint for deleting user
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer your_token_here");
        headers.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOjMsInVzZXJOYW1lIjoiYiIsImFkbWluIjpmYWxzZSwiaWF0IjoxNzM4MTc5NDk5LCJleHAiOjE3Mzg3ODQyOTl9.zk363hoPQTc4wrGmuRtSUtKCaLiK4aLIL9Rgd1qykr4");

        // Create APIRequest for DELETE method
        APIRequest apiRequest = new APIRequest(endpoint, headers, null);
        apiRequest.get(new ApiResponseCallback() {
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
