package com.weiliang79.tweetskeeper.database.twitter.tweet;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "twitter_tweets_table")
public class TwitterTweet {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo (name = "url")
    private String url;

    @ColumnInfo (name = "path")
    private String path;

    @ColumnInfo (name = "tweet_id")
    private long tweet_id;

    @ColumnInfo (name = "user_name")
    private String user_name;

    @ColumnInfo (name = "user_screen_name")
    private String user_screen_name;

    @ColumnInfo (name = "user_profile_pic_url")
    private String user_profile_pic_url;

    @ColumnInfo (name = "status")
    private String status;

    @ColumnInfo (name = "tweet_created_date")
    private Date tweet_created_date;

    @ColumnInfo (name = "bookmark_id")
    private int bookmark_id;

    @ColumnInfo (name = "created_date")
    private Date created_date;

    @Ignore
    private boolean isChecked = false;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public long getTweet_id() {
        return tweet_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_screen_name() {
        return user_screen_name;
    }

    public String getUser_profile_pic_url() {
        return user_profile_pic_url;
    }

    public String getStatus() {
        return status;
    }

    public Date getTweet_created_date() {
        return tweet_created_date;
    }

    public int getBookmark_id() {
        return bookmark_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTweet_id(long tweet_id) {
        this.tweet_id = tweet_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_screen_name(String user_screen_name) {
        this.user_screen_name = user_screen_name;
    }

    public void setUser_profile_pic_url(String user_profile_pic_url) {
        this.user_profile_pic_url = user_profile_pic_url;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTweet_created_date(Date tweet_created_date) {
        this.tweet_created_date = tweet_created_date;
    }

    public void setBookmark_id(int bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public void setIsChecked(boolean isChecked){
        this.isChecked = isChecked;
    }
}
