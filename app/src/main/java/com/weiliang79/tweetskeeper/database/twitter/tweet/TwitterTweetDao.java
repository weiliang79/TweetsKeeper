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
    abstract LiveData<List<TwitterTweet>> getAllTweet();

    @Query("SELECT * FROM twitter_tweet_medias_table WHERE tweet_id = :tweet_id")
    abstract List<TwitterMedia> getMediaList(int tweet_id);

    @Query("SELECT * FROM twitter_tweets_table WHERE id = :id")
    abstract TwitterTweetWithMedias getTweet(int id);

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table")
    abstract LiveData<List<TwitterTweetWithMedias>> getAllTweetsAndMedias();

    @Transaction
    @Query("SELECT * FROM twitter_tweets_table WHERE bookmark_id = :bookmark_id")
    abstract LiveData<List<TwitterTweetWithMedias>> getTweetsAndMediasWithBookmarkId(int bookmark_id);

    @Insert
    abstract void insertTweet(TwitterTweet... twitterTweets);

    @Insert
    abstract long insertTweetGetId(TwitterTweet twitterTweet);

    @Insert
    abstract void insertMedia(TwitterMedia... twitterMedia);

    @Insert
    abstract void insertMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    void insertTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {

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
    abstract void updateTweet(TwitterTweet... twitterTweets);

    @Update
    abstract void updateMedia(TwitterMedia... twitterMedia);

    @Update
    abstract void updateMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    void updateTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {
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
    abstract void deleteTweet(TwitterTweet... twitterTweets);

    @Delete
    abstract void deleteMedia(TwitterMedia... twitterMedia);

    @Delete
    abstract void deleteMediaList(List<TwitterMedia> twitterMediaList);

    @Transaction
    void deleteTweetWithMedias(TwitterTweetWithMedias... twitterTweetWithMedias) {
        for(int i = 0; i < twitterTweetWithMedias.length; i++){
            deleteMediaList(twitterTweetWithMedias[i].twitterMedia);
            deleteTweet(twitterTweetWithMedias[i].twitterTweet);
        }
    }

}
