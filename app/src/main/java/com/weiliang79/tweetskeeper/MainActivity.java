package com.weiliang79.tweetskeeper;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.weiliang79.tweetskeeper.ui.AddAnimation;
import com.weiliang79.tweetskeeper.ui.adapter.ColorSpinnerAdapter;
import com.weiliang79.tweetskeeper.ui.other.OtherBookmarkSpinnerAdapter;
import com.weiliang79.tweetskeeper.ui.twitter.TweetsMainFragment;
import com.weiliang79.tweetskeeper.ui.twitter.TwitterBookmarkSpinnerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;

    private TweetsKeeperRoomDatabase db;
    private ColorViewModel colorViewModel;
    private TwitterBookmarkViewModel twitterBookmarkViewModel;
    private OtherBookmarkViewModel otherBookmarkViewModel;
    private TwitterTweetViewModel twitterTweetViewModel;

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private Fragment navHostFragment;

    private FloatingActionButton fab;
    private FloatingActionButton fabAddLink;
    private FloatingActionButton fabAddBookmark;
    private boolean addIsRotate = false;
    private int lastLocation = 0;

    /*public void testTwitter(){

        //1402663209903874055
        String apiAccessToken = "hMTJbVrBt2Z6dOU8BmY1id3nB",
                    apiAccessSecret = "zL6DmHBrXQCuPkHQebAotfrZaMDqZal409eRpfG09h3zwqo3Uh",
                    consumerAccessToken = "",
                    consumerAccessSecret = "",
                    Tweet_ID = "1393084962547453956";

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(apiAccessToken);
        builder.setOAuthConsumerSecret(apiAccessSecret);
        Configuration configuration = builder.build();
        TwitterFactory factory = new TwitterFactory(configuration);
        final Twitter twitter = factory.getInstance();

        //twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken accessToken = new AccessToken(consumerAccessToken, consumerAccessSecret);
        twitter.setOAuthAccessToken(accessToken);
        try {
            Status status = twitter.showStatus(Long.parseLong(Tweet_ID));
            if (status == null) { //
                // don't know if needed - T4J docs are very bad
            } else {

                MediaEntity[] mediaEntity = status.getMediaEntities();

                Log.e("Twitter", "@" + status.getUser().getScreenName()
                        + " - " + status.getText());

                for(int i = 0; i < mediaEntity.length; i++){
                    Log.e("Twitter Image", "" + mediaEntity[i].getMediaURLHttps());
                }
            }
        } catch (
                TwitterException e) {
            Log.e("Twitter", "Failed to search tweets: " + e.getMessage());
            // e.printStackTrace();
            // DON'T KNOW IF THIS IS THROWN WHEN ID IS INVALID
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("Twitter", "Start run TwitterTest function");
                testTwitter();
            }
        });
        thread.start();*/



        setContentView(R.layout.activity_main);

        //database initialize
        db = TweetsKeeperRoomDatabase.getDbInstance(this);
        initViewModel();

        //check internet connectivity
        /*boolean status = NetworkUtil.getConnectivityStatus(this);
        if(!status) {
            Toast.makeText(this, "Cannot connect to Internet", Toast.LENGTH_SHORT).show();
        }*/

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Button
        initFab();

        //drawer layout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings, R.id.nav_tweets, R.id.nav_others)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        lastLocation = navController.getCurrentDestination().getId();

        navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if(destination.getId() == R.id.nav_settings){
                    AddAnimation.showOut(fab);
                    if(addIsRotate){
                        addIsRotate = AddAnimation.rotateFab(findViewById(R.id.fabAdd), !addIsRotate);
                        AddAnimation.showOut(fabAddLink);
                        AddAnimation.showOut(fabAddBookmark);
                    }


                } else {
                    if(lastLocation == R.id.nav_settings){
                        AddAnimation.showIn(fab);
                    }
                }
                lastLocation = navController.getCurrentDestination().getId();
            }
        });

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e("test", item.toString());
                return false;
            }
        });*/

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(navController.getCurrentDestination().getId() == R.id.nav_tweets){
            boolean status = ((TweetsMainFragment) navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment()).onKeyDown();

            if(status){
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    public void setDrawerLock(boolean setLock){
        if(setLock){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void initViewModel(){

        colorViewModel = new ViewModelProvider(this).get(ColorViewModel.class);
        twitterBookmarkViewModel = new ViewModelProvider(this).get(TwitterBookmarkViewModel.class);
        otherBookmarkViewModel = new ViewModelProvider(this).get(OtherBookmarkViewModel.class);
        twitterTweetViewModel = new ViewModelProvider(this).get(TwitterTweetViewModel.class);

    }

    private void initFab(){
        fabAddLink = findViewById(R.id.fabAddLink);
        fabAddBookmark = findViewById(R.id.fabAddBookmark);
        AddAnimation.init(fabAddLink);
        AddAnimation.init(fabAddBookmark);

        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIsRotate = AddAnimation.rotateFab(view, !addIsRotate);
                if(addIsRotate){
                    AddAnimation.showIn(fabAddLink);
                    AddAnimation.showIn(fabAddBookmark);
                }else{
                    AddAnimation.showOut(fabAddLink);
                    AddAnimation.showOut(fabAddBookmark);
                }
            }
        });

        fabAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddLinkAsyncTask().execute();

            }
        });

        fabAddBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new addBookmarkAsyncTask().execute();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private class addBookmarkAsyncTask extends AsyncTask<Void, Void, ArrayList<List>> {

        AlertDialog dialogBuilder;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
        }

        @Override
        protected ArrayList<List> doInBackground(Void... params){

            ArrayList<List> result = new ArrayList<>();
            result.add(db.colorDao().getUnusedColor(db.twitterBookmarkDao().getUsedColorIds()));
            result.add(db.colorDao().getUnusedColor(db.otherBookmarkDao().getUsedColorIds()));

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<List> result){

            super.onPostExecute(result);

            View addBookmarkView = getLayoutInflater().inflate(R.layout.add_bookmark_dialog, null);
            RadioGroup rgBookmarkType = addBookmarkView.findViewById(R.id.rgBookmarkType);
            EditText etAddBookmarkName = addBookmarkView.findViewById(R.id.addBookmarkDialogName);
            Spinner colorSpinner = addBookmarkView.findViewById(R.id.addBookmarkColorSpinner);
            Button btnCancel = addBookmarkView.findViewById(R.id.btn_cancel_add_bookmark);
            Button btnSave = addBookmarkView.findViewById(R.id.btn_save_add_bookmark);

            ColorSpinnerAdapter mTwitterBookmarkColorAdapter = new ColorSpinnerAdapter(getApplicationContext());
            ColorSpinnerAdapter mOtherBookmarkColorAdapter = new ColorSpinnerAdapter(getApplicationContext());

            List<BookmarkColor> tbUnusedBookmarkColorList = result.get(0);
            List<BookmarkColor> obUnusedBookmarkColorList = result.get(1);

            mTwitterBookmarkColorAdapter.setColorList(tbUnusedBookmarkColorList);
            mOtherBookmarkColorAdapter.setColorList(obUnusedBookmarkColorList);

            rgBookmarkType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.rb_add_bookmark_twitter:
                            colorSpinner.setAdapter(mTwitterBookmarkColorAdapter);
                            break;
                        case R.id.rb_add_bookmark_other:
                            colorSpinner.setAdapter(mOtherBookmarkColorAdapter);
                            break;
                    }
                }
            });

            colorSpinner.setAdapter(mTwitterBookmarkColorAdapter);

            btnSave.setEnabled(false);
            btnSave.setAlpha(.5f);
            etAddBookmarkName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                    if(charSequence.toString().length()==0){
                        btnSave.setEnabled(false);
                        btnSave.setAlpha(.5f);
                    } else {
                        btnSave.setEnabled(true);
                        btnSave.setAlpha(1f);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (rgBookmarkType.getCheckedRadioButtonId()){
                        case R.id.rb_add_bookmark_twitter:

                            TwitterBookmark twitterBookmark = new TwitterBookmark();
                            twitterBookmark.setName(etAddBookmarkName.getText().toString());
                            twitterBookmark.setColor(tbUnusedBookmarkColorList.get(colorSpinner.getSelectedItemPosition()).getId());
                            twitterBookmark.setCreatedDate(new Date());

                            twitterBookmarkViewModel.insertBookmark(twitterBookmark);

                            break;
                        case R.id.rb_add_bookmark_other:

                            OtherBookmark otherBookmark = new OtherBookmark();
                            otherBookmark.setName(etAddBookmarkName.getText().toString());
                            otherBookmark.setColor(obUnusedBookmarkColorList.get(colorSpinner.getSelectedItemPosition()).getId());
                            otherBookmark.setCreatedDate(new Date());

                            otherBookmarkViewModel.insertBookmark(otherBookmark);

                            break;
                    }
                    Toast.makeText(getBaseContext(), "New Bookmark Added", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(addBookmarkView);
            dialogBuilder.show();

        }

    }

    private class AddLinkAsyncTask extends AsyncTask<Void, Void, ArrayList<List>> {

        AlertDialog dialogBuilder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
        }

        @Override
        protected ArrayList<List> doInBackground(Void... params) {
            ArrayList<List> result = new ArrayList<>();
            result.add(db.colorDao().getAllColorList());
            result.add(db.twitterBookmarkDao().getAllBookmarkList());
            result.add(db.otherBookmarkDao().getAllBookmarkList());
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);
            View addLinkView = getLayoutInflater().inflate(R.layout.add_link_dialog, null);

            ImageView ivIcon = addLinkView.findViewById(R.id.ivAddLinkDialogIcon);
            EditText etAddLinkURL = addLinkView.findViewById(R.id.addLinkDialogURL);
            TextView tvBookmarkLocation = addLinkView.findViewById(R.id.tvBookmarkLocation);
            Spinner bookmarkSpinner = addLinkView.findViewById(R.id.addLinkBookmarkSpinner);
            Button btnCancel = addLinkView.findViewById(R.id.btn_cancel_add_link);
            Button btnSave = addLinkView.findViewById(R.id.btn_save_add_link);

            List<BookmarkColor> bookmarkColorList = result.get(0);
            List<TwitterBookmark> twitterBookmarkList = result.get(1);
            List<OtherBookmark> otherBookmarkList = result.get(2);

            TwitterBookmarkSpinnerAdapter tbSpinnerAdapter = new TwitterBookmarkSpinnerAdapter(getApplicationContext());
            OtherBookmarkSpinnerAdapter obSpinnerAdapter = new OtherBookmarkSpinnerAdapter(getApplicationContext());
            TwitterBookmarkSpinnerAdapter noneSpinnerAdapter = new TwitterBookmarkSpinnerAdapter(getApplicationContext());

            TwitterBookmark noneBookmark = new TwitterBookmark();
            noneBookmark.setName("None");
            noneBookmark.setColor(1);
            List<TwitterBookmark> noneBookmarkList = new ArrayList<TwitterBookmark>(Arrays.asList(noneBookmark));

            tbSpinnerAdapter.setBothList(result.get(0), result.get(1));
            obSpinnerAdapter.setBothList(result.get(0), result.get(2));

            noneSpinnerAdapter.setBothList(result.get(0), noneBookmarkList);
            bookmarkSpinner.setAdapter(noneSpinnerAdapter);
            bookmarkSpinner.setEnabled(false);
            bookmarkSpinner.setClickable(false);

            ivIcon.setImageResource(R.drawable.ic_icon_link_broken);
            ivIcon.setColorFilter(getResources().getColor(R.color.color_primary));
            tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " - ");
            btnSave.setEnabled(false);
            btnSave.setAlpha(.5f);

            etAddLinkURL.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    try{
                        btnSave.setEnabled(true);
                        btnSave.setAlpha(1f);

                        URL url = new URL(charSequence.toString());
                        String host = url.getHost();

                        if(verityURLHost(host, R.array.url_twitter)){
                            ivIcon.setImageResource(R.drawable.ic_icon_twitter);
                            tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " Twitter");
                            bookmarkSpinner.setAdapter(tbSpinnerAdapter);
                        } else if(verityURLHost(host, R.array.url_pixiv)) {
                        } else if(verityURLHost(host, R.array.url_facebook)){
                            ivIcon.setImageResource(R.drawable.ic_icon_facebook);
                            tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " Others");
                            bookmarkSpinner.setAdapter(obSpinnerAdapter);
                        } else if(verityURLHost(host, R.array.url_youtube)){
                            ivIcon.setImageResource(R.drawable.ic_icon_youtube);
                            tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " Others");
                            bookmarkSpinner.setAdapter(obSpinnerAdapter);
                        } else {
                            ivIcon.setImageResource(R.drawable.ic_baseline_link);
                            tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " Others");
                            bookmarkSpinner.setAdapter(obSpinnerAdapter);
                        }

                        bookmarkSpinner.setEnabled(true);
                        bookmarkSpinner.setClickable(true);

                    } catch (MalformedURLException e){
                        btnSave.setEnabled(false);
                        btnSave.setAlpha(.5f);
                        ivIcon.setImageResource(R.drawable.ic_icon_link_broken);
                        tvBookmarkLocation.setText(getResources().getString(R.string.default_bookmark_location) + " - ");
                        bookmarkSpinner.setAdapter(noneSpinnerAdapter);
                        bookmarkSpinner.setEnabled(false);
                        bookmarkSpinner.setClickable(false);
                    }

                    ivIcon.setColorFilter(getResources().getColor(R.color.color_primary));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlString = etAddLinkURL.getText().toString();
                    try {
                        URL url = new URL(urlString);
                        String host = url.getAuthority();
                        String path = url.getPath();

                        if(verityURLHost(host, R.array.url_twitter)){

                            //Toast.makeText(getApplicationContext(), "Twitter Link", Toast.LENGTH_SHORT).show();

                            if(!path.contains(getResources().getString(R.string.twitter_status_check))){
                                Toast.makeText(MainActivity.this, "The link cannot be stored.", Toast.LENGTH_SHORT).show();
                                return ;
                            }

                            int bookmarkId = twitterBookmarkList.get(bookmarkSpinner.getSelectedItemPosition()).getId();

                            new SaveTweetsAsyncTask(getApplicationContext(), bookmarkId, twitterTweetViewModel).execute(url);

                            //Log.e("Twitter", "" + twitterBookmarkList.get(bookmarkSpinner.getSelectedItemPosition()).getId());
                            //Log.e("Twitter", "Start run TwitterTest function");
                            //SaveTweetTask saveTweetTask = new SaveTweetTask(getApplicationContext(), mDb, twitterBookmarkList.get(bookmarkSpinner.getSelectedItemPosition()).getId());
                            //saveTweetTask.execute(url);

                        } else if(verityURLHost(host, R.array.url_pixiv)) {
                            Toast.makeText(getApplicationContext(), "Pixiv Link", Toast.LENGTH_SHORT).show();
                        } else {
                            if(verityURLHost(host, R.array.url_facebook)){
                                Toast.makeText(getApplicationContext(), "Facebook Link", Toast.LENGTH_SHORT).show();
                            } else if(verityURLHost(host, R.array.url_youtube)){
                                Toast.makeText(getApplicationContext(), "Youtube Link", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (MalformedURLException e) {
                        //Log.e("URL Error", e.getMessage());
                        Toast.makeText(MainActivity.this, "The link are broken", Toast.LENGTH_SHORT).show();
                    }

                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(addLinkView);
            dialogBuilder.show();

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
    }

}