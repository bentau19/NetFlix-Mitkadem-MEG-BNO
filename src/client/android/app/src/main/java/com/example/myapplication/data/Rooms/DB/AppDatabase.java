package com.example.myapplication.data.Rooms.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.Rooms.entity.UserToken;


@Database(entities = {UserToken.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TokenDao tokenDao();
}
