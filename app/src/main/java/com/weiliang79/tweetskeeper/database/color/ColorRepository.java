package com.weiliang79.tweetskeeper.database.color;

import android.app.Application;

import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ColorRepository {

    private ColorDao colorDao;
    private LiveData<List<BookmarkColor>> colorList;

    ColorRepository (Application application) {
        TweetsKeeperRoomDatabase db = TweetsKeeperRoomDatabase.getDbInstance(application);
        colorDao = db.colorDao();
        colorList = colorDao.getAllColor();
    }

    public LiveData<List<BookmarkColor>> getAllColors () {
        return colorList;
    }

    public void insert (BookmarkColor bookmarkColor) {
        new InsertAsyncTask(colorDao).execute(bookmarkColor);
    }

    public void update (BookmarkColor bookmarkColor) {
        new UpdateAsyncTask(colorDao).execute(bookmarkColor);
    }

    public void delete (BookmarkColor bookmarkColor) {
        new DeleteAsyncTask(colorDao).execute(bookmarkColor);
    }

    private static class InsertAsyncTask extends android.os.AsyncTask<BookmarkColor, Void, Void> {

        private ColorDao colorDao;

        InsertAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final BookmarkColor... params) {
            for(int i = 0; i < params.length; i++){
                colorDao.insert(params[i]);
            }
            return null;
        }

    }

    public static class UpdateAsyncTask extends android.os.AsyncTask<BookmarkColor, Void, Void> {

        private ColorDao colorDao;

        UpdateAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final BookmarkColor... params) {
            for(int i = 0; i < params.length; i++){
                 colorDao.updateColor(params[i]);
            }
            return null;
        }

    }

    public static class DeleteAsyncTask extends android.os.AsyncTask<BookmarkColor, Void, Void> {

        private ColorDao colorDao;

        DeleteAsyncTask (ColorDao dao) {
            colorDao = dao;
        }

        @Override
        protected Void doInBackground (final BookmarkColor... params) {
            for(int i = 0; i < params.length; i++){
                colorDao.updateColor(params[i]);
            }
            return null;
        }

    }

}
