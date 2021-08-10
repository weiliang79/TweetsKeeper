package com.weiliang79.tweetskeeper.database.other.bookmark;

import android.app.Application;

import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class OtherBookmarkRepository {

    private OtherBookmarkDao otherBookmarkDao;
    private LiveData<List<OtherBookmark>> bookmarkList;

    OtherBookmarkRepository (Application application) {
        TweetsKeeperRoomDatabase db = TweetsKeeperRoomDatabase.getDbInstance(application);
        otherBookmarkDao = db.otherBookmarkDao();
        bookmarkList = otherBookmarkDao.getAllBookmark();
    }

    public LiveData<List<OtherBookmark>> getAllBookmarks () {
        return bookmarkList;
    }

    public void insert (OtherBookmark otherBookmark) {
        new InsertAsyncTask(otherBookmarkDao).execute(otherBookmark);
    }

    public void update (OtherBookmark otherBookmark) {
        new UpdateAsyncTask(otherBookmarkDao).execute(otherBookmark);
    }

    public void delete (OtherBookmark otherBookmark) {
        new DeleteAsyncTask(otherBookmarkDao).execute(otherBookmark);
    }

    private static class InsertAsyncTask extends android.os.AsyncTask<OtherBookmark, Void, Void> {

        private OtherBookmarkDao otherBookmarkDao;

        InsertAsyncTask (OtherBookmarkDao dao) {
            otherBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final OtherBookmark... params) {
            for(int i = 0; i < params.length; i++){
                otherBookmarkDao.insert(params[i]);
            }
            return null;
        }
    }

    private static class UpdateAsyncTask extends android.os.AsyncTask<OtherBookmark, Void, Void> {

        private OtherBookmarkDao otherBookmarkDao;

        UpdateAsyncTask (OtherBookmarkDao dao) {
            otherBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final OtherBookmark... params) {
            for(int i = 0; i < params.length; i++){
                otherBookmarkDao.updateBookmark(params[i]);
            }
            return null;
        }
    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<OtherBookmark, Void, Void> {

        private OtherBookmarkDao otherBookmarkDao;

        DeleteAsyncTask (OtherBookmarkDao dao) {
            otherBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final OtherBookmark... params) {
            for(int i = 0; i < params.length; i++){
                otherBookmarkDao.deleteBookmark(params[i]);
            }
            return null;
        }
    }
}
