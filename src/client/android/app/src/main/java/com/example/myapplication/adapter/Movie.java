package com.example.myapplication.adapter;

import java.util.List;

public class Movie {
    private int _id;
    private String title;
    private String logline;
    private String image;
    private List<Integer> categories; // Assuming category IDs are integers

    // Getters and Setters
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogline() {
        return logline;
    }

    public void setLogline(String logline) {
        this.logline = logline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
