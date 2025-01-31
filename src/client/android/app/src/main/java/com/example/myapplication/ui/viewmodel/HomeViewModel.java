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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();
    private final CategoryRepository categoryRep;
    private final MovieRepository movieRep;
    private final MutableLiveData<String> reqStatus;
    private final MutableLiveData<List<Category>> movieListLiveData = new MutableLiveData<>();
    public HomeViewModel() {
        this.categoryRep = new CategoryRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                reqStatus.setValue("Get cat successs");
            }
            @Override
            public void onError(String error) {
                reqStatus.setValue("Get cat failed: " + error);
            }
        });
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
    public void fetchCategories(String query) {
        categoryRep.fetchCategories(query, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);
                List<Category> categories = gson.fromJson(json, new TypeToken<List<Category>>() {}.getType());
                movieListLiveData.setValue(categories);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }

    public void findCategoryByName(String name) {
        categoryRep.fetchCategories(name, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);
                List<Category> categories = gson.fromJson(json, new TypeToken<List<Category>>() {}.getType());
                List<Category> temp = new ArrayList<>();
                if (!categories.isEmpty())
                    temp.add(categories.get(0));
                movieListLiveData.setValue(temp);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }


    public void fetchMovies(String query) {
//        movieRep.fetchMovies(query, new ApiResponseCallback() {
//            @Override
//            public void onSuccess(Object response) {
//                Gson gson = new Gson();
//                String json = gson.toJson(response);
//                List<Movie> movies = gson.fromJson(json, new TypeToken<List<Movie>>() {}.getType());
//                List<Category> categories = new ArrayList<>(List.of(new Category(query, movies)));
//
//                movieListLiveData.setValue(categories);
//                reqStatus.setValue("fetch successful!");
//            }
//
//            @Override
//            public void onError(String error) {
//                reqStatus.setValue(error);
//            }
//        });
    }

    public void userRecommends() {
        movieRep.userRecommends( new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);

                Type listType = new TypeToken<List<Category>>() {}.getType();
                List<Category> categoryList = gson.fromJson(json, listType);
                movieListLiveData.setValue(new ArrayList<>());
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