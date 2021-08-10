package com.weiliang79.tweetskeeper.database.twitter.tweet;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TwitterTweetViewModel extends AndroidViewModel {

    private TwitterTweetRepository repository;
    private LiveData<List<TwitterTweetWithMedias>> tweetList;

    public TwitterTweetViewModel (Application application) {
        super(application);
        repository = new TwitterTweetRepository(application);
        tweetList = repository.getAllTweetsWithMedias();
    }

    public LiveData<List<TwitterTweetWithMedias>> getAllTweetsWithMedias(){
        return tweetList;
    }

    public LiveData<List<TwitterTweetWithMedias>> getTweetsAndMediasWithBookmarkId(int bookmark_id) {
        return repository.getTweetsAndMediasWithBookmarkId(bookmark_id);
    }

    public void insertTweet (TwitterTweetWithMedias twitterTweetWithMedias) {
        repository.insert(twitterTweetWithMedias);
    }

    public void insertTweetList (List<TwitterTweetWithMedias> twitterTweetWithMediasList) {
        repository.insertList(twitterTweetWithMediasList);
    }

    public void updateTweet (TwitterTweetWithMedias twitterTweetWithMedias) {
        repository.update(twitterTweetWithMedias);
    }

    public void updateTweetList (List<TwitterTweetWithMedias> twitterTweetWithMediasList) {
        repository.updateList(twitterTweetWithMediasList);
    }

    public void deleteTweet (TwitterTweet twitterTweet) {
        //repository.delete(twitterTweet);
    }

    public void deleteTweetList(List<TwitterTweetWithMedias> twitterTweetWithMediasList) {
        repository.deleteList(twitterTweetWithMediasList);
    }

}
