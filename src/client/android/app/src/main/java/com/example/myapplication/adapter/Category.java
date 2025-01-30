package com.example.myapplication.adapter;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")  // Ensure it maps correctly
    private String _id;

    @SerializedName("name")  // Ensures Gson maps "name" correctly
    private String name;

    @SerializedName("promoted")
    private boolean promoted;

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
