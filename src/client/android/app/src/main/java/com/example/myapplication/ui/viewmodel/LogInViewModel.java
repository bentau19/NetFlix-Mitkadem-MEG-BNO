package com.example.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.server.api.ApiResponseCallback;
import com.example.myapplication.data.repository.UserRepository;

public class LogInViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<String> LogInStatus;
    private final MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
    public LogInViewModel() {

        // Initialize the UserRepository and LogIn status LiveData
        LogInStatus = new MutableLiveData<>();
        userRepository = new UserRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                LogInStatus.postValue("LogIn successful!");
            }
            @Override
            public void onError(String error) {
                LogInStatus.postValue("LogIn failed: " + error);
            }
        });
    }

    // Expose the LogIn status LiveData to the UI
    public LiveData<String> getLogInStatus() {
        return LogInStatus;
    }

    // Method to initiate the LogIn process
    public void LogIn(String username, String password) {
        // Call the UserRepository to handle the LogIn operation
        userRepository.login(username, password);
    }

}
