package com.example.myapplication.activity.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HomeViewModel extends ViewModel {


    private final MovieRepository movieRep;
    private final MutableLiveData<String> reqStatus;
    private final MutableLiveData<List<Category>> movieListLiveData = new MutableLiveData<>();
    public HomeViewModel() {
        reqStatus = new MutableLiveData<>();
        movieRep = new MovieRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                reqStatus.setValue("Signup successful!");
            }
            @Override
            public void onError(String error) {
                reqStatus.setValue("Signup failed: " + error);
            }
        });
    }
    public LiveData<String> getReqStatus() {
        return reqStatus;
    }
    public LiveData<List<Category>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public void userRecommends() {
        movieRep.userRecommends( new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);

                Type listType = new TypeToken<List<Category>>() {}.getType();
                List<Category> categoryList = gson.fromJson(json, listType);
                movieListLiveData.setValue(categoryList);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }
}