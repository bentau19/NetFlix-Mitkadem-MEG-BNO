package com.example.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.adapter.Movie;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieInfoViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<Movie> movieLiveData;
    private MutableLiveData<List<Movie>> recommendedMoviesLiveData;
    private final Gson gson = new Gson();

    public MovieInfoViewModel() {
        movieRepository = new MovieRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                if (response instanceof Map) {
                    // Handle single movie response
                    Movie movie = convertMapToMovie((Map<String, Object>) response);
                    movieLiveData.postValue(movie);
                } else if (response instanceof List) {
                    // Handle movie list response
                    List<Movie> movies = convertListToMovies((List<?>) response);
                    recommendedMoviesLiveData.postValue(movies);
                }
            }

            @Override
            public void onError(String error) {
                // Handle error callback
            }
        });

        movieLiveData = new MutableLiveData<>();
        recommendedMoviesLiveData = new MutableLiveData<>();
    }

    private Movie convertMapToMovie(Map<String, Object> map) {
        // Convert map to JSON string and then to Movie object
        String json = gson.toJson(map);
        return gson.fromJson(json, Movie.class);
    }

    private List<Movie> convertListToMovies(List<?> list) {
        List<Movie> movies = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> movieMap = (Map<String, Object>) item;
                movies.add(convertMapToMovie(movieMap));
            }
        }
        return movies;
    }
    public void watched(String movieid){
        movieRepository.postRecommendedMovies(movieid);
    }
    // Fetch Recommended Movies based on movieId
    public void fetchRecommendedMovies(String movieId) {
        movieRepository.getRecommendedMovies(movieId);
    }

    // Fetch Movie Info based on movieId
    public void fetchMovieInfo(String movieId) {
        movieRepository.fetchMovieData(movieId, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                if (response instanceof Map) {
                    Movie movie = convertMapToMovie((Map<String, Object>) response);
                    movieLiveData.postValue(movie);
                }
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    // LiveData getters for the activity to observe
    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<List<Movie>> getRecommendedMoviesLiveData() {
        return recommendedMoviesLiveData;
    }
}