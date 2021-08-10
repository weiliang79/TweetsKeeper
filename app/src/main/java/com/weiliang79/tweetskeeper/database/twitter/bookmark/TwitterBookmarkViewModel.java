package com.weiliang79.tweetskeeper.database.twitter.bookmark;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TwitterBookmarkViewModel extends AndroidViewModel {

    private TwitterBookmarkRepository repository;
    private LiveData<List<TwitterBookmark>> bookmarkList;

    public TwitterBookmarkViewModel (Application application) {
        super(application);
        repository = new TwitterBookmarkRepository(application);
        bookmarkList = repository.getAllBookmarks();
    }

    public LiveData<List<TwitterBookmark>> getAllBookmark () {
        return bookmarkList;
    }

    public void insertBookmark (TwitterBookmark twitterBookmark) {
        repository.insert(twitterBookmark);
    }

    public void updateBookmark (TwitterBookmark twitterBookmark) {
        repository.update(twitterBookmark);
    }

    public void deleteBookmark (TwitterBookmark twitterBookmark) {
        repository.delete(twitterBookmark);
    }
}
