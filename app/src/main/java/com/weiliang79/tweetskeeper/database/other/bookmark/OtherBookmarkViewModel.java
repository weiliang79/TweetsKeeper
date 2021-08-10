package com.weiliang79.tweetskeeper.database.other.bookmark;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OtherBookmarkViewModel extends AndroidViewModel {

    private OtherBookmarkRepository repository;
    private LiveData<List<OtherBookmark>> bookmarkList;

    public OtherBookmarkViewModel (Application application) {
        super(application);
        repository = new OtherBookmarkRepository(application);
        bookmarkList = repository.getAllBookmarks();
    }

    public LiveData<List<OtherBookmark>> getBookmarkList() {
        return bookmarkList;
    }

    public void insertBookmark(OtherBookmark otherBookmark) {
        repository.insert(otherBookmark);
    }

    public void updateBookmark(OtherBookmark otherBookmark) {
        repository.update(otherBookmark);
    }

    public void deleteBookmark(OtherBookmark otherBookmark) {
        repository.delete(otherBookmark);
    }

}