package com.weiliang79.tweetskeeper.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import ir.androidexception.roomdatabasebackupandrestore.Restore;

public class ImportDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private TweetsKeeperRoomDatabase db;

    public ImportDatabaseAsyncTask(Context context){
        this.context = context;
        this.db = TweetsKeeperRoomDatabase.getDbInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        File dest = new File(Environment.getExternalStorageDirectory(), "Tweets Keeper");

        new Restore.Init()
                .database(db)
                .backupFilePath(dest.getPath() + File.separator + TweetsKeeperRoomDatabase.DATABASE_BACKUP_NAME)
                .execute();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Toast.makeText(context, "Import database successful", Toast.LENGTH_SHORT).show();

    }

}
