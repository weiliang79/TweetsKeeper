package com.weiliang79.tweetskeeper.database.twitter.tweet;

import android.app.Application;

import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TwitterTweetRepository {

    private TwitterTweetDao twitterTweetDao;
    private LiveData<List<TwitterTweetWithMedias>> tweetsWithMediasList;

    TwitterTweetRepository (Application application) {
        TweetsKeeperRoomDatabase db = TweetsKeeperRoomDatabase.getDbInstance(application);
        twitterTweetDao = db.twitterTweetDao();
        tweetsWithMediasList = twitterTweetDao.getAllTweetsAndMedias();
    }

    LiveData<List<TwitterTweetWithMedias>> getAllTweetsWithMedias () {
        return tweetsWithMediasList;
    }

    LiveData<List<TwitterTweetWithMedias>> getTweetsAndMediasWithBookmarkId(int bookmark_id) {
        return twitterTweetDao.getTweetsAndMediasWithBookmarkId(bookmark_id);
    }

    public void insert (TwitterTweetWithMedias twitterTweetWithMedias) {
        new InsertAsyncTask(twitterTweetDao).execute(twitterTweetWithMedias);
    }

    public void insertList (List<TwitterTweetWithMedias> twitterTweetWithMedias) {
        new InsertAsyncTask(twitterTweetDao).execute(twitterTweetWithMedias.toArray(new TwitterTweetWithMedias[0]));
    }

    public void update (TwitterTweetWithMedias twitterTweetWithMedias) {
        new UpdateAsyncTask(twitterTweetDao).execute(twitterTweetWithMedias);
    }

    public void updateList (List<TwitterTweetWithMedias> twitterTweetWithMediasList) {
        new UpdateAsyncTask(twitterTweetDao).execute(twitterTweetWithMediasList.toArray(new TwitterTweetWithMedias[0]));
    }

    public void delete (TwitterTweetWithMedias twitterTweetWithMedias) {
        new DeleteAsyncTask(twitterTweetDao).execute(twitterTweetWithMedias);
    }

    public void deleteList (List<TwitterTweetWithMedias> twitterTweetWithMedias) {
        new DeleteAsyncTask(twitterTweetDao).execute(twitterTweetWithMedias.toArray(new TwitterTweetWithMedias[0]));
    }

    private static class InsertAsyncTask extends android.os.AsyncTask<TwitterTweetWithMedias, Void, Void> {

        private TwitterTweetDao twitterTweetDao;

        InsertAsyncTask (TwitterTweetDao dao) {
            twitterTweetDao = dao;
        }

        @Override
        protected Void doInBackground(TwitterTweetWithMedias... params) {
            for(int i = 0; i < params.length; i++){
                twitterTweetDao.insertTweetWithMedias(params[i]);
            }
            return null;
        }

    }

    private static class UpdateAsyncTask extends android.os.AsyncTask<TwitterTweetWithMedias, Void, Void> {

        private TwitterTweetDao twitterTweetDao;

        UpdateAsyncTask (TwitterTweetDao dao) {
            twitterTweetDao = dao;
        }

        @Override
        protected Void doInBackground(TwitterTweetWithMedias... params) {
            for(int i = 0; i < params.length; i++){
                twitterTweetDao.updateTweetWithMedias(params[i]);
            }
            return null;
        }

    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<TwitterTweetWithMedias, Void, Void> {

        private TwitterTweetDao twitterTweetDao;

        DeleteAsyncTask (TwitterTweetDao dao) {
            twitterTweetDao = dao;
        }

        @Override
        protected Void doInBackground(TwitterTweetWithMedias... params) {
            for(int i = 0; i < params.length; i++){
                twitterTweetDao.deleteTweetWithMedias(params[i]);
            }
            return null;
        }
    }

}
