package com.example.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.server.api.ApiResponseCallback;
import com.example.myapplication.data.repository.UserRepository;

public class SignupViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<String> signupStatus;

    public SignupViewModel() {

        // Initialize the UserRepository and Signup status LiveData
        signupStatus = new MutableLiveData<>();
        userRepository = new UserRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                // On success, update the LiveData with a success message
                signupStatus.setValue("Signup successful!");
            }

            @Override
            public void onError(String error) {
                // On error, update the LiveData with an error message
                signupStatus.setValue("Signup failed: " + error);
            }
        });
    }

    // Expose the signup status LiveData to the UI
    public LiveData<String> getSignupStatus() {
        return signupStatus;
    }

    // Method to initiate the signup process
    public void signup(String username, String password, String email) {
        // Call the UserRepository to handle the signup operation
        userRepository.signup(username, password, email);
    }
}
