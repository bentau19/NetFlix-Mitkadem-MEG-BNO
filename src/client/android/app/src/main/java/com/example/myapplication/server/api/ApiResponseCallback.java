package com.example.myapplication.server.api;

public interface ApiResponseCallback {
    void onSuccess(Object response);
    void onError(String error);
}
