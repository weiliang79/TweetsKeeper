package com.weiliang79.tweetskeeper.database.twitter.bookmark;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "twitter_bookmarks_table")
public class TwitterBookmark {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo (name = "name")
    private String name;

    @NonNull
    @ColumnInfo (name = "color")
    private int color;

    @ColumnInfo (name = "created_date")
    private Date createdDate;

    public int getId() {
        return id;
    }

    @NonNull
    public String getName () {
        return name;
    }

    public int getColor () {
        return color;
    }

    public Date getCreatedDate () {
        return createdDate;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setName (@NonNull String name) {
        this.name = name;
    }

    public void setColor (int color) {
        this.color = color;
    }

    public void setCreatedDate (Date created_date) {
        this.createdDate = created_date;
    }
}
