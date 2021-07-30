package com.example.tweetskeeper.database.color;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ColorDao {

    @Insert
    void insert(Color... colors);

    @Query("SELECT * FROM color_table")
    LiveData<List<Color>> getAllColor();

    @Query("SELECT * FROM color_table")
    List<Color> getAllColorList();

    @Query("SELECT * FROM color_table WHERE id NOT IN (:colorIds)")
    List<Color> getUnusedColor(List<Integer> colorIds);

    @Update
    void updateColor(Color color);

    @Delete
    void deleteColor(Color color);

}
