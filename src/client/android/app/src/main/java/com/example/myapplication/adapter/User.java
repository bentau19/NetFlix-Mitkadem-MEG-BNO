package com.example.myapplication.adapter;

public class User {
    private String displayName=null;
    private String image=null;// Assuming category IDs are integers
    private boolean admin =false;
    public User(String displayName,String image) {
        this.displayName = displayName;
        this.image=image;
    }
    public User(String displayName,String image,boolean admin) {
        this.displayName = displayName;
        this.image=image;
        this.admin=admin;
    }
    public User(String displayName) {
        this.displayName = displayName;
    }
    public User(String displayName,boolean admin) {
        this.admin=admin;
        this.displayName = displayName;
    }
    // Getters and Setters

    public String getName() {
        return displayName;
    }

    public void setName(String displayName) {
        this.displayName = displayName;
    }

    public boolean getAdmin() {
        return admin;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
