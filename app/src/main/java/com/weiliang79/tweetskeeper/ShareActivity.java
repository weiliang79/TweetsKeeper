package com.weiliang79.tweetskeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorViewModel;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmarkViewModel;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmarkViewModel;
import com.weiliang79.tweetskeeper.database.twitter.tweet.SaveTweetsAsyncTask;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetViewModel;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetWithMedias;
import com.weiliang79.tweetskeeper.ui.twitter.TwitterBookmarkSpinnerAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends AppCompatActivity {

    TweetsKeeperRoomDatabase db;
    ColorViewModel colorViewModel;
    TwitterBookmarkViewModel twitterBookmarkViewModel;
    TwitterTweetViewModel twitterTweetViewModel;
    OtherBookmarkViewModel otherBookmarkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initViewModel();
        colorViewModel.getColorList();
        twitterBookmarkViewModel.getAllBookmark();
        twitterTweetViewModel.getAllTweetsWithMedias();
        otherBookmarkViewModel.getBookmarkList();

        Intent intent = getIntent();

        //Log.e("Intent", "" + intent.getType());
        //Log.e("Intent", "" + intent.getStringExtra("android.intent.extra.TEXT"));

        String text = intent.getStringExtra("android.intent.extra.TEXT");

        if("android.intent.action.SEND".equals(intent.getAction()) && "text/plain".equals(intent.getType())){

            try{

                URL url = new URL(text);

                if(verityURLHost(url.getHost(), R.array.url_twitter)){

                    if(url.getPath().contains(getResources().getString(R.string.twitter_status_check))){

                        new AddShareLinkAsyncTask(this, AddShareLinkAsyncTask.INSERT_TYPE_TWITTER, url).execute();

                    } else {

                        Toast.makeText(this, "The link cannot be stored.", Toast.LENGTH_SHORT).show();
                        ShareActivity.this.finish();
                    }

                } else {

                    Toast.makeText(this, "The current link are not supported.", Toast.LENGTH_SHORT).show();
                    ShareActivity.this.finish();
                }

            } catch (MalformedURLException e){
                Toast.makeText(this, "The sharing content are not an URL type.", Toast.LENGTH_SHORT).show();
                ShareActivity.this.finish();
            }

        } else {
            Toast.makeText(this, "The sharing is not accepted", Toast.LENGTH_SHORT).show();
            ShareActivity.this.finish();
        }

    }

    private void initViewModel(){

        db = TweetsKeeperRoomDatabase.getDbInstance(this);

        colorViewModel = new ViewModelProvider(this).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                this,
                new Observer<List<BookmarkColor>>() {
                    @Override
                    public void onChanged(List<BookmarkColor> bookmarkColors) {

                    }
                }
        );

        twitterBookmarkViewModel = new ViewModelProvider(this).get(TwitterBookmarkViewModel.class);

        twitterBookmarkViewModel.getAllBookmark().observe(
                this,
                new Observer<List<TwitterBookmark>>() {
                    @Override
                    public void onChanged(List<TwitterBookmark> twitterBookmarks) {

                    }
                }
        );

        twitterTweetViewModel = new ViewModelProvider(this).get(TwitterTweetViewModel.class);

        twitterTweetViewModel.getAllTweetsWithMedias().observe(
                this,
                new Observer<List<TwitterTweetWithMedias>>() {
                    @Override
                    public void onChanged(List<TwitterTweetWithMedias> twitterTweetWithMedias) {

                    }
                }
        );

        otherBookmarkViewModel = new ViewModelProvider(this).get(OtherBookmarkViewModel.class);

        otherBookmarkViewModel.getBookmarkList().observe(
                this,
                new Observer<List<OtherBookmark>>() {
                    @Override
                    public void onChanged(List<OtherBookmark> otherBookmarks) {

                    }
                }
        );
    }

    public boolean verityURLHost(String host, int resId){
        String[] names = getResources().getStringArray(resId);
        for(String name : names){
            if(host.toLowerCase().contains(name)){
                return true;
            }
        }

        return false;
    }

    private class AddShareLinkAsyncTask extends AsyncTask<Void, Void, ArrayList<List>> {

        public final static int INSERT_TYPE_TWITTER = 1;

        Context context;
        int insertType;
        URL url;
        AlertDialog dialogBuilder;

        public AddShareLinkAsyncTask(Context context, int insertType, URL url){
            this.context = context;
            this.insertType = insertType;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(context).create();
        }

        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            ArrayList<List> result = new ArrayList<>();

            switch (insertType){
                case 1:
                    result.add(db.colorDao().getAllColorList());
                    result.add(db.twitterBookmarkDao().getAllBookmarkList());
                    break;
                default:
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);

            switch (insertType) {
                case 1:

                    List<BookmarkColor> colorList = result.get(0);
                    List<TwitterBookmark> bookmarkList = result.get(1);

                    if(bookmarkList.size() == 0){
                        Toast.makeText(context, "Database error: no bookmark in record", Toast.LENGTH_SHORT).show();
                    } else if(bookmarkList.size() == 1){

                        new SaveTweetsAsyncTask(context, bookmarkList.get(0).getId(), twitterTweetViewModel).execute(url);
                        Toast.makeText(context, "Tweet link saved.", Toast.LENGTH_SHORT).show();

                    } else {
                        View addShareLinkView = getLayoutInflater().inflate(R.layout.add_share_link_dialog, null);

                        TextView tvShareLink = addShareLinkView.findViewById(R.id.tvAddShareLink_url);
                        TextView tvBookmarkLocation = addShareLinkView.findViewById(R.id.tvAddShareLink_BookmarkLocation);
                        Spinner spBookmark = addShareLinkView.findViewById(R.id.addShareLink_addLinkBookmarkSpinner);
                        Button btnSave = addShareLinkView.findViewById(R.id.btn_save_add_share_link);
                        Button btnCancel = addShareLinkView.findViewById(R.id.btn_cancel_add_share_link);

                        tvShareLink.setText("Link: " + url.toString());
                        tvBookmarkLocation.setText(getResources().getText(R.string.default_bookmark_location) + " - " + getResources().getText(R.string.menu_twitter));

                        TwitterBookmarkSpinnerAdapter tbSpinnerAdapter = new TwitterBookmarkSpinnerAdapter(context);
                        tbSpinnerAdapter.setBothList(colorList, bookmarkList);
                        spBookmark.setAdapter(tbSpinnerAdapter);

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                new SaveTweetsAsyncTask(context, bookmarkList.get(spBookmark.getSelectedItemPosition()).getId(), twitterTweetViewModel).execute(url);
                                Toast.makeText(context, "Tweet link saved.", Toast.LENGTH_SHORT).show();
                                dialogBuilder.dismiss();
                                ShareActivity.this.finish();
                            }
                        });

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.dismiss();
                                ShareActivity.this.finish();
                            }
                        });

                        dialogBuilder.setView(addShareLinkView);
                        dialogBuilder.show();

                    }

                    break;
                default:
                    break;
            }

        }

    }

}