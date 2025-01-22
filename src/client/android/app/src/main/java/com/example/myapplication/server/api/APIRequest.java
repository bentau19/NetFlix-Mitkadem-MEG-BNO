package com.example.myapplication.server.api;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;

public class APIRequest {
    private final String baseUrl;
    private final String endpoint;
    private final Map<String, String> headers;
    private final Object jsonBody;
    private final ApiService apiService;

    public APIRequest(String endpoint, Map<String, String> headers, Object jsonBody) {
        this.baseUrl = "http://10.0.2.2:5000/api/";
        this.endpoint = endpoint;
        this.headers = headers;
        this.jsonBody = jsonBody;

        // Initialize Retrofit and ApiService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void get(ApiResponseCallback callback) {
        Call<Object> call = apiService.get(endpoint, headers);
        executeRequest(call, callback);
    }

    public void post(ApiResponseCallback callback) {
        Call<Object> call = apiService.post(endpoint, headers, jsonBody);
        executeRequest(call, callback);
    }

    public void put(ApiResponseCallback callback) {
        Call<Object> call = apiService.put(endpoint, headers, jsonBody);
        executeRequest(call, callback);
    }

    public void delete(ApiResponseCallback callback) {
        Call<Object> call = apiService.delete(endpoint, headers);
        executeRequest(call, callback);
    }

    private void executeRequest(Call<Object> call, ApiResponseCallback callback) {
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        String errorMessage = "Error: " + response.code();
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            JSONObject errorJson = new JSONObject(errorBody);
                            if (errorJson.has("message")) {
                                errorMessage = errorJson.getString("message");
                            }
                        }
                        callback.onError(errorMessage);
                    } catch (Exception e) {
                        callback.onError("Error: Unable to parse error response");
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }
}
