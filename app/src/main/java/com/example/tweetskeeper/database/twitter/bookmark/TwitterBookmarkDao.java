package com.example.tweetskeeper.database.twitter.bookmark;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TwitterBookmarkDao {

    @Insert
    void insert(TwitterBookmark... twitterBookmark);

    @Query("SELECT * FROM twitter_bookmarks_table")
    LiveData<List<TwitterBookmark>> getAllBookmark();

    @Query("SELECT * FROM twitter_bookmarks_table")
    List<TwitterBookmark> getAllBookmarkList();

    @Query("SELECT color FROM twitter_bookmarks_table")
    List<Integer> getUsedColorIds();

    @Update
    void updateBookmark(TwitterBookmark twitterBookmark);

    @Delete
    void deleteBookmark(TwitterBookmark twitterBookmark);

}
