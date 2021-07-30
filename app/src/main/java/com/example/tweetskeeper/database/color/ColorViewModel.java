package com.example.tweetskeeper.database.color;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ColorViewModel extends AndroidViewModel {

    private ColorRepository repository;
    private LiveData<List<Color>> colorList;

    public ColorViewModel (Application application) {
        super(application);
        repository = new ColorRepository(application);
        colorList = repository.getAllColors();
    }

    public LiveData<List<Color>> getColorList() {
        return colorList;
    }

    public void insertColor(Color color) {
        repository.insert(color);
    }

    public void updateColor(Color color) {
        repository.update(color);
    }

    public void deleteColor(Color color) {
        repository.delete(color);
    }

}