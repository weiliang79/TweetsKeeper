package com.weiliang79.tweetskeeper.database.color;

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
    void insert(BookmarkColor... bookmarkColors);

    @Query("SELECT * FROM bookmark_color_table")
    LiveData<List<BookmarkColor>> getAllColor();

    @Query("SELECT * FROM bookmark_color_table")
    List<BookmarkColor> getAllColorList();

    @Query("SELECT * FROM bookmark_color_table WHERE id NOT IN (:colorIds)")
    List<BookmarkColor> getUnusedColor(List<Integer> colorIds);

    @Query("SELECT * FROM bookmark_color_table WHERE id = :id LIMIT 1")
    BookmarkColor getColorWithId(int id);

    @Update
    void updateColor(BookmarkColor bookmarkColor);

    @Delete
    void deleteColor(BookmarkColor bookmarkColor);

}
