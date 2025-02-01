package com.example.myapplication.adapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("_id")  // Ensure it maps correctly
    private String _id;

    @SerializedName("name")  // Ensures Gson maps "name" correctly
    private String name;

    @SerializedName("promoted")
    private boolean promoted;
    private List<Movie> movies;

    public Category(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public boolean isPromoted() {
        return promoted;
    }
}


