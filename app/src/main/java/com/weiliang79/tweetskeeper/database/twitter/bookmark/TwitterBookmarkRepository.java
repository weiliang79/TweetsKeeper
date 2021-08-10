package com.weiliang79.tweetskeeper.database.twitter.bookmark;

import android.app.Application;

import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TwitterBookmarkRepository {

    private TwitterBookmarkDao twitterBookmarkDao;
    private LiveData<List<TwitterBookmark>> bookmarkList;

    TwitterBookmarkRepository (Application application) {
        TweetsKeeperRoomDatabase db = TweetsKeeperRoomDatabase.getDbInstance(application);
        twitterBookmarkDao = db.twitterBookmarkDao();
        bookmarkList = twitterBookmarkDao.getAllBookmark();
    }

    LiveData<List<TwitterBookmark>> getAllBookmarks () {
        return bookmarkList;
    }

    public void insert (TwitterBookmark twitterBookmark) {
        new InsertAsyncTask(twitterBookmarkDao).execute(twitterBookmark);
    }

    public void update (TwitterBookmark twitterBookmark) {
        new UpdateAsyncTask(twitterBookmarkDao).execute(twitterBookmark);
    }

    public void delete (TwitterBookmark twitterBookmark) {
        new DeleteAsyncTask(twitterBookmarkDao).execute(twitterBookmark);
    }

    private static class InsertAsyncTask extends android.os.AsyncTask<TwitterBookmark, Void, Void> {

        private TwitterBookmarkDao twitterBookmarkDao;

        InsertAsyncTask (TwitterBookmarkDao dao) {
            twitterBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final TwitterBookmark... params) {
            for(int i = 0; i < params.length; i++){
                twitterBookmarkDao.insert(params[i]);
            }
            return null;
        }

    }

    private static class UpdateAsyncTask extends android.os.AsyncTask<TwitterBookmark, Void, Void> {

        private TwitterBookmarkDao twitterBookmarkDao;

        UpdateAsyncTask (TwitterBookmarkDao dao) {
            twitterBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final TwitterBookmark... params) {
            for (int i = 0; i < params.length; i++) {
                twitterBookmarkDao.updateBookmark(params[0]);
            }
            return null;
        }

    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<TwitterBookmark, Void, Void> {

        private TwitterBookmarkDao twitterBookmarkDao;

        DeleteAsyncTask (TwitterBookmarkDao dao) {
            twitterBookmarkDao = dao;
        }

        @Override
        protected Void doInBackground (final TwitterBookmark... params) {
            for (int i = 0; i < params.length; i++) {
                twitterBookmarkDao.deleteBookmark(params[i]);
            }
            return null;
        }
    }

}
