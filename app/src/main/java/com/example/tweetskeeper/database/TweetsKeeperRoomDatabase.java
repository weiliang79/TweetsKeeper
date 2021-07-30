package com.example.tweetskeeper.database;

import android.content.Context;

import com.example.tweetskeeper.database.color.Color;
import com.example.tweetskeeper.database.color.ColorDao;
import com.example.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.example.tweetskeeper.database.other.bookmark.OtherBookmarkDao;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmarkDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {
                Color.class,
                TwitterBookmark.class,
                OtherBookmark.class
        },
        version = 1
)
@TypeConverters({
        DateConverter.class
})
public abstract class TweetsKeeperRoomDatabase extends RoomDatabase {

    public abstract ColorDao colorDao();
    public abstract TwitterBookmarkDao twitterBookmarkDao();
    public abstract OtherBookmarkDao otherBookmarkDao();
    public static TweetsKeeperRoomDatabase INSTANCE;

    public static TweetsKeeperRoomDatabase getDbInstance (Context context) {
        if (INSTANCE == null) {
            synchronized (TweetsKeeperRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TweetsKeeperRoomDatabase.class,
                            "tweets_keeper_database")
                            .createFromAsset("database/default.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
