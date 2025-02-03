package com.example.myapplication.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieRepository {

    private final ApiResponseCallback callback;
    private final TokenDao tokenDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MovieRepository(ApiResponseCallback callback) {
        this.callback = callback;
        this.tokenDao = AppDatabase.getInstance().tokenDao();
    }

    private void getToken(Callback<String> tokenCallback) {
        executor.execute(() -> {
            String token = tokenDao.getTokenString();
            new Handler(Looper.getMainLooper()).post(() -> {
                if (token != null && !token.isEmpty()) {
                    tokenCallback.onSuccess(token);
                } else {
                    tokenCallback.onError("No token found. User not logged in.");
                }
            });
        });
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
        // Use getToken to fetch the token from the database
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/";
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("token", token); // Use the token fetched from the database

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

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }


    public void fetchMovieData(String  movieId, ApiResponseCallback callback) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/" + movieId;
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);

                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.get(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        if (response instanceof LinkedTreeMap) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response);
                            Movie movie = gson.fromJson(json, Movie.class);
                            callback.onSuccess(movie);
                        } else {
                            callback.onError("Unexpected response format");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
            }

            @Override
            public void onError(String error) {
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
    public void updateMovie(String movieId, String title, String logline, String imageHex , String categories) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/"+ movieId;
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("token" , token);

                Map<String, String> jsonBody = new HashMap<>();
                jsonBody.put("title", title);
                jsonBody.put("logline", logline);
                jsonBody.put("image", imageHex);

                String categoriesJsonString = new JSONArray(processCategories(categories)).toString();
                jsonBody.put("categories", categoriesJsonString);

                APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
                apiRequest.put(callback);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });

    }

    // Delete User

    public void deleteMovie(String movieId) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/" + movieId;
                Map<String, String> headers = new HashMap<>();
                headers.put("token" , token);

                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.delete(callback);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public void userRecommends(ApiResponseCallback callback) {
        // Use getToken to fetch the token from the database
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/";  // Endpoint for recommendations
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token); // Use the token fetched from the database
                headers.put("token", token); // Add token as another header if needed

                // Create APIRequest for GET method (or whatever HTTP method you need)
                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.get(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        callback.onSuccess(response);  // Pass the response back to the caller
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);  // Pass error back to the caller
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Handle error when the token can't be fetched
            }
        });
    }
    public void getRecommendedMovies(String movieId) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/" + movieId + "/recommend";  // Endpoint for recommended movies
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);  // Include token in request headers

                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.get(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        // The response is expected to be a list of recommended movies
                        if (response instanceof List<?>) {
                            List<Movie> recommendedMovies = (List<Movie>) response;
                            callback.onSuccess(recommendedMovies);  // Pass the recommended movies back to the caller
                        } else {
                            callback.onError("Unexpected response format");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);  // Pass the error back to the caller
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Pass error if token retrieval fails
            }
        });
    }
    public void postRecommendedMovies(String movieId) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "movies/" + movieId + "/recommend";  // Endpoint for recommended movies
                Map<String, String> headers = new HashMap<>();
                headers.put("token", token);
                Map<String, String> jsonBody = new HashMap<>();
                jsonBody.put("id", movieId);
                APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
                apiRequest.post(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        callback.onSuccess("ok");
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);  // Pass the error back to the caller
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Pass error if token retrieval fails
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
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

}
