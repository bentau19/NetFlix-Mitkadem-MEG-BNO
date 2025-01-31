package com.example.myapplication.data.Rooms.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.Rooms.entity.UserToken;

@Database(entities = {UserToken.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract TokenDao tokenDao();

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Database not initialized. Call init() first.");
        }
        return instance;
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            "db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }
}