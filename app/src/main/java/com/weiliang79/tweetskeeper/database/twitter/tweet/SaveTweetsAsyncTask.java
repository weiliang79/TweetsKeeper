package com.weiliang79.tweetskeeper.database.twitter.tweet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.weiliang79.tweetskeeper.BuildConfig;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SaveTweetsAsyncTask extends AsyncTask<URL, Void, List<TwitterTweetWithMedias>> {

    private Context context;
    private TwitterTweetViewModel twitterTweetViewModel;
    private int bookmarkId;

    private ConfigurationBuilder configurationBuilder;
    private Configuration configuration;
    private TwitterFactory twitterFactory;
    private Twitter twitter;
    private AccessToken accessToken;

    private TwitterException twitterException = null;

    public SaveTweetsAsyncTask(Context context, int bookmarkId, TwitterTweetViewModel twitterTweetViewModel) {
        this.context = context;
        this.bookmarkId = bookmarkId;
        this.twitterTweetViewModel = twitterTweetViewModel;

        configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(BuildConfig.TWITTER_ACCESS_TOKEN);
        configurationBuilder.setOAuthConsumerSecret(BuildConfig.TWITTER_SECRET);
        configuration = configurationBuilder.build();
        twitterFactory = new TwitterFactory(configuration);
        twitter = twitterFactory.getInstance();

        accessToken = new AccessToken("", "");
        twitter.setOAuthAccessToken(accessToken);
    }

    @Override
    protected List<TwitterTweetWithMedias> doInBackground(URL... urls) {
        List<TwitterTweetWithMedias> twitterTweetsList = new ArrayList<>();

        for(int i = 0; i < urls.length; i++){

            String[] arrOfPath = urls[i].getPath().toString().split("/");
            String tweetId = arrOfPath[arrOfPath.length - 1];

            try{
                twitter4j.Status status = twitter.showStatus(Long.parseLong(tweetId));

                if(status == null){

                    //Log.e("Twitter Exception", "Tweet not found");

                } else {

                    MediaEntity[] mediaEntity = status.getMediaEntities();

                    //Log.e("Twitter", "@" + status.getUser().getScreenName() + " - " + status.getText());

                    if(mediaEntity != null){

                        TwitterTweetWithMedias tweet = new TwitterTweetWithMedias();
                        tweet.twitterTweet = new TwitterTweet();
                        tweet.twitterMedia = new ArrayList<>();

                        //Log.e("Twitter Image", "Size: " + mediaEntity.length);

                        for(int j = 0; j < mediaEntity.length; j++){
                            TwitterMedia twitterMedia = new TwitterMedia();
                            twitterMedia.setUrl(mediaEntity[j].getMediaURLHttps());
                            twitterMedia.setType(mediaEntity[i].getType());  //"photo", "video", "animated_gif"
                            tweet.twitterMedia.add(twitterMedia);

                            //Log.e("Twitter Image", "" + mediaEntity[j].getMediaURLHttps() + " Type: " + mediaEntity[i].getType());
                        }

                        tweet.twitterTweet.setUrl(urls[i].toString());
                        tweet.twitterTweet.setPath(urls[i].getPath());
                        tweet.twitterTweet.setTweet_id(status.getId());
                        tweet.twitterTweet.setUser_name(status.getUser().getName());
                        tweet.twitterTweet.setUser_screen_name(status.getUser().getScreenName());
                        tweet.twitterTweet.setUser_profile_pic_url(status.getUser().getOriginalProfileImageURLHttps());
                        tweet.twitterTweet.setStatus(status.getText());
                        tweet.twitterTweet.setTweet_created_date(status.getCreatedAt());
                        tweet.twitterTweet.setBookmark_id(bookmarkId);
                        tweet.twitterTweet.setCreated_date(new Date());

                        twitterTweetsList.add(tweet);

                    }

                }

            } catch (TwitterException e) {
                //Log.e("Twitter", "Failed to search tweets: " + e.getMessage());
                Toast.makeText(context, "Failed to search tweet: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                this.twitterException = e;
            }

        }

        return twitterTweetsList;
    }

    @Override
    protected void onPostExecute(List<TwitterTweetWithMedias> twitterTweetsWithMediasList) {
        super.onPostExecute(twitterTweetsWithMediasList);

        if(twitterException == null){

            //Log.e("Twitter", "Start Insert Database");
            twitterTweetViewModel.insertTweetList(twitterTweetsWithMediasList);
            //Log.e("Twitter", "Finish Insert Database");
            Toast.makeText(context, "Twitter: Tweet info saved.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Twitter: Failed to get tweet info.", Toast.LENGTH_SHORT).show();
        }
    }

}
