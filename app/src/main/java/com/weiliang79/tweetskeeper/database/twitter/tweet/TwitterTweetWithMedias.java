package com.weiliang79.tweetskeeper.database.twitter.tweet;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TwitterTweetWithMedias {

    @Embedded public TwitterTweet twitterTweet;

    @Relation(
            parentColumn = "id",
            entityColumn = "tweet_id"
    )
    public List<TwitterMedia> twitterMedia;

}
