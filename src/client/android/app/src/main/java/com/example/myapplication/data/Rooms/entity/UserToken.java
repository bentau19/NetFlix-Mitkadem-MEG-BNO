package com.example.myapplication.data.Rooms.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class UserToken {
    @PrimaryKey
    public int id;
    public String token;
}
