package com.example.tweetskeeper.database.color;

import android.app.Application;

import com.example.tweetskeeper.database.TweetsKeeperRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ColorRepository {

    private ColorDao colorDao;
    private LiveData<List<Color>> colorList;

    ColorRepository (Application application) {
        TweetsKeeperRoomDatabase db = TweetsKeeperRoomDatabase.getDbInstance(application);
        colorDao = db.colorDao();
        colorList = colorDao.getAllColor();
    }

    public LiveData<List<Color>> getAllColors () {
        return colorList;
    }

    public void insert (Color color) {
        new InsertAsyncTask(colorDao).execute(color);
    }

    public void update (Color color) {
        new UpdateAsyncTask(colorDao).execute(color);
    }

    public void delete (Color color) {
        new DeleteAsyncTask(colorDao).execute(color);
    }

    private static class InsertAsyncTask extends android.os.AsyncTask<Color, Void, Void> {

        private ColorDao colorDao;

        InsertAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final Color... params) {
            for(int i = 0; i < params.length; i++){
                colorDao.insert(params[i]);
            }
            return null;
        }

    }

    public static class UpdateAsyncTask extends android.os.AsyncTask<Color, Void, Void> {

        private ColorDao colorDao;

        UpdateAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final Color... params) {
            for(int i = 0; i < params.length; i++){
                 colorDao.updateColor(params[i]);
            }
            return null;
        }

    }

    public static class DeleteAsyncTask extends android.os.AsyncTask<Color, Void, Void> {

        private ColorDao colorDao;

        DeleteAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final Color... params) {
            for(int i = 0; i < params.length; i++){
                colorDao.updateColor(params[i]);
            }
            return null;
        }

    }

}
