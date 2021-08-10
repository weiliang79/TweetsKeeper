package com.weiliang79.tweetskeeper.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import ir.androidexception.roomdatabasebackupandrestore.Backup;

public class ExportDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private TweetsKeeperRoomDatabase db;

    public ExportDatabaseAsyncTask(Context context){
        this.context = context;
        this.db = TweetsKeeperRoomDatabase.getDbInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        File dest = new File(Environment.getExternalStorageDirectory(), "Tweets Keeper");

        if(!dest.exists()){
            dest.mkdirs();
        }

        new Backup.Init()
                .database(db)
                .path(dest.getPath())
                .fileName(TweetsKeeperRoomDatabase.DATABASE_BACKUP_NAME)
                .execute();

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        Toast.makeText(context, "Export database successful", Toast.LENGTH_SHORT).show();
    }

}
