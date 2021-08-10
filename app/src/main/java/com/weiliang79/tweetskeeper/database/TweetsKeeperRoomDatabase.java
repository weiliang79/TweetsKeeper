package com.weiliang79.tweetskeeper.database;

import android.content.Context;

import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorDao;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmarkDao;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmarkDao;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterMedia;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweet;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {
                BookmarkColor.class,
                TwitterBookmark.class,
                TwitterTweet.class,
                TwitterMedia.class,
                OtherBookmark.class
        },
        version = 1
)
@TypeConverters({
        DateConverter.class
})
public abstract class TweetsKeeperRoomDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tweets_keeper_database";
    public static final String DATABASE_BACKUP_NAME = "database.txt";

    public abstract ColorDao colorDao();
    public abstract TwitterBookmarkDao twitterBookmarkDao();
    public abstract TwitterTweetDao twitterTweetDao();
    public abstract OtherBookmarkDao otherBookmarkDao();
    public static TweetsKeeperRoomDatabase INSTANCE;

    public static TweetsKeeperRoomDatabase getDbInstance (Context context) {
        if (INSTANCE == null) {
            synchronized (TweetsKeeperRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TweetsKeeperRoomDatabase.class,
                            DATABASE_NAME)
                            .createFromAsset("database/default.db")
                            .setJournalMode(JournalMode.TRUNCATE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
