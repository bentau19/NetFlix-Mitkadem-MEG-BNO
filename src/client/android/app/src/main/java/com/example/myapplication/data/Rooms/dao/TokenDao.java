package com.example.myapplication.data.Rooms.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.data.Rooms.entity.UserToken;

@Dao
public interface TokenDao {
    // Inserts a token into the database, replacing the existing one if there's a conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToken(UserToken token);

    // Queries the token from the database with id = 1
    @Query("SELECT * FROM UserToken LIMIT 1")
    LiveData<UserToken> getToken();
    @Query("SELECT token FROM UserToken LIMIT 1")
    String getTokenString();

    @Query("DELETE FROM UserToken")
    void deleteAllTokens();


}
