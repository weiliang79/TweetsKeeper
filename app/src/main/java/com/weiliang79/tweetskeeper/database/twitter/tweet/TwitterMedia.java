package com.weiliang79.tweetskeeper.database.twitter.tweet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twitter_tweet_medias_table")
public class TwitterMedia {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    //not belong to twitter tweet id with type long
    @ColumnInfo (name = "tweet_id")
    private int tweet_id;

    @ColumnInfo (name = "url")
    private String url;

    @ColumnInfo (name = "type")
    private String type;

    public int getId() {
        return id;
    }

    public int getTweet_id() {
        return tweet_id;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTweet_id(int tweet_id) {
        this.tweet_id = tweet_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

}
