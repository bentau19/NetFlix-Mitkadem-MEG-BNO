package com.example.myapplication.ui.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.repository.CategoryRepository;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

public class AdminViewModel extends ViewModel {
    private final MovieRepository movieRep;
    private final CategoryRepository categoryRep;
    private final MutableLiveData<List<Movie>> movieListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();


    private final MutableLiveData<String> reqStatus;
    public AdminViewModel() {

        // Initialize the UserRepository and Signup status LiveData
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
        categoryRep = new CategoryRepository(new ApiResponseCallback() {
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
    public LiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public LiveData<List<Category>> getCategoryListLiveData() {
        return categoryListLiveData;
    }


    // Expose the signup status LiveData to the UI
    public LiveData<String> getReqStatus() {
        return reqStatus;
    }

    // Method to initiate the signup process
    public void getMovies() {
        // Call the UserRepository to handle the signup operation
        movieRep.getMovies();
    }
    public void deleteMovie(String movieId) {
        // Call the UserRepository to handle the signup operation
        movieRep.deleteMovie(movieId);
    }
    public void getCategory() {
        // Call the UserRepository to handle the signup operation
        categoryRep.getCategories();
    }
    public ApiResponseCallback getCategories() {
        return categoryRep.getCategories();
    }
    public LiveData<List<Category>> getAllCategories() {
        return categoryRep.getAllCategories();
    }
    public LiveData<List<Movie>> getAllMovies() {
        return movieRep.getAllMovies();
    }
    public void deleteCategory(String categoryId) {
        // Call the UserRepository to handle the signup operation
        categoryRep.deleteCategory(categoryId);
    }
    public void fetchMovies(String query) {
        movieRep.fetchMovies(query, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);
                List<Movie> movies = gson.fromJson(json, new TypeToken<List<Movie>>() {}.getType());
                movieListLiveData.setValue(movies);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }

    public void fetchCategories(String query) {
        categoryRep.fetchCategories(query, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);
                List<Category> categories = gson.fromJson(json, new TypeToken<List<Category>>() {}.getType());
                categoryListLiveData.setValue(categories);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }


}
