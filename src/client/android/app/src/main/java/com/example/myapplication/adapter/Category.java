package com.example.myapplication.adapter;

import java.util.List;

public class Category {
    private int _id;
    private String name;
    private boolean promoted;

    private List<Movie> movies;

    public Category(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    // Getters and Setters
    public int getId() {
        return _id;
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
}

