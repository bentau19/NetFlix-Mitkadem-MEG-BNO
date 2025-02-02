package com.example.myapplication.adapter;

public class User {
    private String displayName=null;
    private String image=null;// Assuming category IDs are integers

    public User(String displayName,String image) {
        this.displayName = displayName;
        this.image=image;
    }

    public User(String displayName) {
        this.displayName = displayName;
    }
    // Getters and Setters

    public String getName() {
        return displayName;
    }

    public void setName(String displayName) {
        this.displayName = displayName;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
