package com.example.myapplication.ui.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.repository.CategoryRepository;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.server.api.ApiResponseCallback;

public class AdminFormViewModel extends ViewModel {
    private final MovieRepository movieRep;
    private final CategoryRepository categoryRep;
    private MutableLiveData<Category> categoryLiveData;
    private MutableLiveData<Movie> movieLiveData;

    private final MutableLiveData<String> reqStatus;
    public AdminFormViewModel() {

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

    // Expose the signup status LiveData to the UI
    public LiveData<String> getReqStatus() {
        return reqStatus;
    }

    // Method to initiate the signup process
    public void createMovie(String title, String logline, String image, String categories) {
        // Call the UserRepository to handle the signup operation
        movieRep.createMovie(title, logline, image, categories);
    }
    public void updateMovie(String movieId, String title, String logline, String image, String categories) {
        // Call the UserRepository to handle the signup operation
        movieRep.updateMovie(movieId, title, logline, image, categories);
    }
    public void createCategory(String name, Boolean promoted) {
        // Call the UserRepository to handle the signup operation
        categoryRep.createCategory(name, promoted);
    }
    public void updateCategory(String categoryId, String name, Boolean promoted) {
        // Call the UserRepository to handle the signup operation
        categoryRep.updateCategory(categoryId, name, promoted);
    }

    // Method to fetch category by id and return LiveData
    // Method to fetch Category data
    public LiveData<Category> getCategory(String categoryId) {
        categoryRep.fetchCategoryData(categoryId, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Assuming response is Category
                categoryLiveData.setValue((Category) response); // Update LiveData
            }

            @Override
            public void onError(String error) {
                // Handle error by posting an empty value or error state
                categoryLiveData.setValue(null); // You can update with an error state if needed
            }
        });
        return categoryLiveData;
    }

    // Method to fetch movie by id and return LiveData
    public LiveData<Movie> getMovie(String movieId) {
        movieRep.fetchMovieData(movieId, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Assuming response is Category
                movieLiveData.setValue((Movie) response); // Update LiveData
            }

            @Override
            public void onError(String error) {
                // Handle error by posting an empty value or error state
                movieLiveData.setValue(null); // You can update with an error state if needed
            }
        });
        return movieLiveData;
    }

}
