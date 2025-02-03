package com.example.myapplication.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryRepository {

    private final ApiResponseCallback callback;
    private final TokenDao tokenDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CategoryRepository(ApiResponseCallback callback) {
        this.callback = callback;
        this.tokenDao = AppDatabase.getInstance().tokenDao();

    }
    private void getToken(CategoryRepository.Callback<String> tokenCallback) {
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


    // Signup User
    public void createCategory(String name, Boolean promoted) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "categories/";
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("token", token);  // Use the retrieved token

                Map<String, String> jsonBody = new HashMap<>();
                jsonBody.put("name", name);
                jsonBody.put("promoted", String.valueOf(promoted));

                APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
                apiRequest.post(callback);  // Pass the callback to handle the response
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Handle error if token retrieval fails
            }
        });
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
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "categories/" + categoryId;
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);  // Use the retrieved token

                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.get(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        Gson gson = new Gson();
                        if (response instanceof LinkedTreeMap) {
                            String json = gson.toJson(response);
                            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

                            JsonObject messageObject = jsonObject.getAsJsonObject("message");
                            if (messageObject != null) {
                                Category category = gson.fromJson(messageObject, Category.class);
                                callback.onSuccess(category);
                            } else {
                                callback.onError("Missing 'message' field in the response");
                            }
                        } else if (response instanceof Category) {
                            callback.onSuccess((Category) response);
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
                callback.onError(error);  // Handle error if no token is found
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
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                if (token == null || token.isEmpty()) {
                    callback.onError("Token is null or empty");
                    return;
                }

                String endpoint = "categories/" + categoryId;
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("token" , token);  // Use the retrieved token

                Map<String, String> jsonBody = new HashMap<>();
                jsonBody.put("name", newName);
                jsonBody.put("promoted", String.valueOf(newPromoted));

                APIRequest apiRequest = new APIRequest(endpoint, headers, jsonBody);
                apiRequest.patch(new ApiResponseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        callback.onSuccess(response);  // Forward the response to the original callback
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);  // Forward the error to the original callback
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError("Failed to retrieve token: " + error);  // More descriptive error
            }
        });
    }


    // Delete User
    public void deleteCategory(String categoryId) {
        getToken(new Callback<String>() {
            @Override
            public void onSuccess(String token) {
                String endpoint = "categories/" + categoryId;
                Map<String, String> headers = new HashMap<>();
                headers.put("token" , token);  // Use the retrieved token

                APIRequest apiRequest = new APIRequest(endpoint, headers, null);
                apiRequest.delete(callback);  // Make the DELETE request
            }

            @Override
            public void onError(String error) {
                callback.onError(error);  // Handle error if no token is found
            }
        });
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
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

}
