package com.weiliang79.tweetskeeper.ui.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetViewModel;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetWithMedias;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ViewTweetActivity extends AppCompatActivity {

    private TwitterTweetViewModel twitterTweetViewModel;

    private Toolbar toolbar;
    private int tweetId;
    private ShapeableImageView ivUserProfile;
    private TextView tvUserName;
    private TextView tvUserScreenName;
    private TextView tvStatus;
    private TextView tvDateCreated;
    private Button btnToTwitter;
    private LinearLayout llMedia;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tweet);

        toolbar = findViewById(R.id.toolbar_view_tweet);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tweet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        tweetId = intent.getIntExtra("id", 0);

        ivUserProfile = findViewById(R.id.ivTweetView_UserProfilePic);
        tvUserName = findViewById(R.id.tvTweetView_userName);
        tvUserScreenName = findViewById(R.id.tvTweetView_userScreenName);
        tvStatus = findViewById(R.id.tvTweetView_status);
        tvDateCreated = findViewById(R.id.tvTweetView_dateCreated);
        btnToTwitter = findViewById(R.id.btn_tweet_to_twitter_tweet_view);
        llMedia = findViewById(R.id.llTweetView_mediaList);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getApplicationContext().getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getApplicationContext().getResources().getDisplayMetrics()));

        initModel();
        twitterTweetViewModel.getTweetAndMediasWithTweetId(tweetId);
    }

    private void initModel(){

        twitterTweetViewModel = new ViewModelProvider(this).get(TwitterTweetViewModel.class);

        twitterTweetViewModel.getTweetAndMediasWithTweetId(tweetId).observe(
                this,
                new Observer<TwitterTweetWithMedias>() {
                    @Override
                    public void onChanged(TwitterTweetWithMedias twitterTweetWithMedias) {

                        Objects.requireNonNull(getSupportActionBar()).setTitle("Tweet - " + twitterTweetWithMedias.twitterTweet.getUser_name());

                        Glide.with(getApplicationContext())
                                .load(twitterTweetWithMedias.twitterTweet.getUser_profile_pic_url())
                                .centerCrop()
                                .placeholder(R.drawable.ic_user)
                                .error(R.drawable.ic_user)
                                .fallback(R.drawable.ic_user)
                                .into(ivUserProfile);

                        tvUserName.setText(twitterTweetWithMedias.twitterTweet.getUser_name());
                        tvUserScreenName.setText(twitterTweetWithMedias.twitterTweet.getUser_screen_name());
                        tvStatus.setText(twitterTweetWithMedias.twitterTweet.getStatus());
                        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                        tvDateCreated.setText(dateFormat.format(twitterTweetWithMedias.twitterTweet.getCreated_date()));

                        btnToTwitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterTweetWithMedias.twitterTweet.getUrl()));
                                startActivity(intent);
                            }
                        });

                        if(twitterTweetWithMedias.twitterMedia.size() > 0){

                            View imageView;

                            for(int i = 0; i < twitterTweetWithMedias.twitterMedia.size(); i++){

                                imageView = getLayoutInflater().inflate(R.layout.image_item_one, null);
                                imageView.setLayoutParams(params);
                                ShapeableImageView ivMedia = imageView.findViewById(R.id.image_preview_one);
                                ShapeableImageView ivMediaType = imageView.findViewById(R.id.image_preview_one_type);
                                ivMedia.setAdjustViewBounds(true);

                                llMedia.addView(imageView, i);

                                Glide.with(getApplicationContext())
                                        .load(twitterTweetWithMedias.twitterMedia.get(i).getUrl())
                                        .placeholder(R.drawable.ic_image)
                                        .error(R.drawable.ic_image_broke)
                                        .fallback(R.drawable.ic_image)
                                        .centerInside()
                                        .into(ivMedia);

                                if(twitterTweetWithMedias.twitterMedia.get(i).getType().equals("animated_gif")){
                                    ivMediaType.bringToFront();

                                    Glide.with(getApplicationContext())
                                            .load(R.drawable.ic_gif)
                                            .into(ivMediaType);

                                } else if(twitterTweetWithMedias.twitterMedia.get(i).getType().equals("video")){
                                    ivMediaType.bringToFront();

                                    Glide.with(getApplicationContext())
                                            .load(R.drawable.ic_video)
                                            .into(ivMediaType);

                                } else {
                                    ivMediaType.setVisibility(View.GONE);
                                }

                            }
                        }

                    }
                }
        );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}