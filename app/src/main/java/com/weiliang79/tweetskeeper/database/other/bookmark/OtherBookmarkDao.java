package com.weiliang79.tweetskeeper.database.other.bookmark;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OtherBookmarkDao {

    @Insert
    void insert(OtherBookmark... otherBookmark);

    @Query("SELECT * FROM other_bookmarks_table")
    LiveData<List<OtherBookmark>> getAllBookmark();

    @Query("SELECT * FROM other_bookmarks_table")
    List<OtherBookmark> getAllBookmarkList();

    @Query("SELECT color FROM other_bookmarks_table")
    List<Integer> getUsedColorIds();

    @Update
    void updateBookmark(OtherBookmark otherBookmark);

    @Delete
    void deleteBookmark(OtherBookmark otherBookmark);
}
