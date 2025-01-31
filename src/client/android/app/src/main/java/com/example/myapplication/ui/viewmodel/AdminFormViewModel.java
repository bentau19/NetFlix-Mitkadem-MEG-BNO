package com.example.myapplication.ui.viewmodel;
import android.util.Log;

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
    private final MutableLiveData<Movie> movieLiveData;
    private final MutableLiveData<Category> categoryLiveData;

    private final MutableLiveData<String> reqStatus;
    public AdminFormViewModel() {

        // Initialize the UserRepository and Signup status LiveData
        reqStatus = new MutableLiveData<>();
         movieLiveData = new MutableLiveData<>();
        categoryLiveData = new MutableLiveData<>();
    // Add this

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
    public void createMovie(String title, String logline, String imageBase64, String categories) {
        // Create or update movie logic
        MovieRepository movieRepository = new MovieRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // On success, update reqStatus to "success"
                reqStatus.setValue("success");
            }

            @Override
            public void onError(String error) {
                // On error, update reqStatus to "failure"
                reqStatus.setValue("failure: " + error);
            }
        });
        movieRepository.createMovie(title, logline, imageBase64, categories);
    }
    public void updateMovie(String movieId, String title, String logline, String imageBase64, String categories) {
        // Update movie logic
        MovieRepository movieRepository = new MovieRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // On success, update reqStatus to "success"
                reqStatus.setValue("success");
            }

            @Override
            public void onError(String error) {
                // On error, update reqStatus to "failure"
                reqStatus.setValue("failure: " + error);
            }
        });
        movieRepository.updateMovie(movieId, title, logline, imageBase64, categories);
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
        Log.d("AdminFormViewModel", "Fetching category with ID: " + categoryId);

        categoryRep.fetchCategoryData(categoryId, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d("AdminFormViewModel", "Category fetch success: " + response);

                // Assuming response is Category
                categoryLiveData.setValue((Category) response); // Update LiveData
            }

            @Override
            public void onError(String error) {
                Log.e("AdminFormViewModel", "Category fetch error: " + error);

                // Handle error by posting an empty value or error state
                categoryLiveData.setValue(null); // You can update with an error state if needed
            }

        });
        Log.d("AdminFormViewModel", "getCategory() called with ID: " + categoryId);

        return categoryLiveData;
    }

    // Method to fetch movie by id and return LiveData
// In AdminFormViewModel
    public LiveData<Movie> getMovie(String movieId) {
        Log.d("AdminFormViewModel", "Fetching movie with ID: " + movieId);
        movieRep.fetchMovieData(movieId, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d("AdminFormViewModel", "Movie fetch success: " + response);
                movieLiveData.setValue((Movie) response);
            }

            @Override
            public void onError(String error) {
                Log.e("AdminFormViewModel", "Movie fetch error: " + error);
                movieLiveData.setValue(null);
            }
        });
        Log.d("AdminFormViewModel", "getMovie() called with ID: " + movieId);

        return movieLiveData;
    }

}
