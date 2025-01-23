package com.example.myapplication.ui.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.repository.CategoryRepository;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.example.myapplication.data.repository.UserRepository;

public class AdminFormViewModel extends ViewModel {
    private final MovieRepository movieRep;
    private final CategoryRepository categorieRep;
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
        categorieRep = new CategoryRepository(new ApiResponseCallback() {
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
        categorieRep.createCategory(name, promoted);
    }
    public void updateCategory(String categoryId, String name, Boolean promoted) {
        // Call the UserRepository to handle the signup operation
        categorieRep.updateCategory(categoryId, name, promoted);
    }

    public void getCategory(String categoryId) {
        // Call the UserRepository to handle the signup operation
        categorieRep.fetchCategoryData(categoryId);
    }
    public void getMovie(String movieId) {
        // Call the UserRepository to handle the signup operation
        movieRep.fetchMovieData(movieId);
    }

}
