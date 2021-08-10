package com.weiliang79.tweetskeeper.database.color;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ColorViewModel extends AndroidViewModel {

    private ColorRepository repository;
    private LiveData<List<BookmarkColor>> colorList;

    public ColorViewModel (Application application) {
        super(application);
        repository = new ColorRepository(application);
        colorList = repository.getAllColors();
    }

    public LiveData<List<BookmarkColor>> getColorList() {
        return colorList;
    }

    public void insertColor(BookmarkColor bookmarkColor) {
        repository.insert(bookmarkColor);
    }

    public void updateColor(BookmarkColor bookmarkColor) {
        repository.update(bookmarkColor);
    }

    public void deleteColor(BookmarkColor bookmarkColor) {
        repository.delete(bookmarkColor);
    }

}