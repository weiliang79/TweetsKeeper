package com.weiliang79.tweetskeeper.database.twitter.tweet;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class TwitterTweetDao {

    @Query("SELECT * FROM twitter_tweets_table")
    public abstract LiveData<List<TwitterTweet>> getAllTweet();

    @Query("SELECT * FROM twitter_tweet_medias_table WHERE tweet_id = :tweet_id")
    public abstract List<TwitterMedia> getMediaList(int tweet_id);

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table WHERE id = :id")
    public abstract TwitterTweetWithMedias getTweet(int id);

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table WHERE id = :id")
    public abstract LiveData<TwitterTweetWithMedias> getTweetLiveData(int id);

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table")
    public abstract LiveData<List<TwitterTweetWithMedias>> getAllTweetsAndMedias();

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table WHERE bookmark_id = :bookmark_id")
    public abstract LiveData<List<TwitterTweetWithMedias>> getTweetsAndMediasWithBookmarkId(int bookmark_id);

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table WHERE bookmark_id = :bookmark_id")
    public abstract List<TwitterTweetWithMedias> getTweetsAndMediasListWithBookmarkId(int bookmark_id);

    @Insert
    public abstract void insertTweet(TwitterTweet... twitterTweets);

    @Insert
    public abstract long insertTweetGetId(TwitterTweet twitterTweet);

    @Insert
    public abstract void insertMedia(TwitterMedia... twitterMedia);

    @Insert
    public abstract void insertMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    public void insertTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {

        for(int i = 0; i < twitterTweetWithMedias.length; i++){

            int tweet_id = (int) insertTweetGetId(twitterTweetWithMedias[i].twitterTweet);

            for(int j = 0; j < twitterTweetWithMedias[i].twitterMedia.size(); j++){
                if(twitterTweetWithMedias[i].twitterMedia.get(j).getTweet_id() != tweet_id){
                    twitterTweetWithMedias[i].twitterMedia.get(j).setTweet_id(tweet_id);
                }
            }

            insertMediaList(twitterTweetWithMedias[i].twitterMedia);

        }

    }

    @Update
    public abstract void updateTweet(TwitterTweet... twitterTweets);

    @Update
    public abstract void updateMedia(TwitterMedia... twitterMedia);

    @Update
    public abstract void updateMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    public void updateTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {
        for(int i = 0; i < twitterTweetWithMedias.length; i++){

            updateTweet(twitterTweetWithMedias[i].twitterTweet);

            for(int j = 0; j < twitterTweetWithMedias[i].twitterMedia.size(); j++){
                if(twitterTweetWithMedias[i].twitterMedia.get(j).getTweet_id() != twitterTweetWithMedias[i].twitterTweet.getId()){
                    twitterTweetWithMedias[i].twitterMedia.get(j).setTweet_id(twitterTweetWithMedias[i].twitterTweet.getId());
                }
            }

            updateMediaList(twitterTweetWithMedias[i].twitterMedia);
        }
    }

    @Delete
    public abstract void deleteTweet(TwitterTweet... twitterTweets);

    @Delete
    public abstract void deleteMedia(TwitterMedia... twitterMedia);

    @Delete
    public abstract void deleteMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    public void deleteTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {
        for(int i = 0; i < twitterTweetWithMedias.length; i++){
            deleteMediaList(twitterTweetWithMedias[i].twitterMedia);
            deleteTweet(twitterTweetWithMedias[i].twitterTweet);
        }
    }

}
