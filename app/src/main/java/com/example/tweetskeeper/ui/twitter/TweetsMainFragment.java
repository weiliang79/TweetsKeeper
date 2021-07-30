package com.example.tweetskeeper.ui.twitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tweetskeeper.R;
import com.example.tweetskeeper.database.color.Color;
import com.example.tweetskeeper.database.color.ColorViewModel;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmarkViewModel;

import java.util.List;

public class TweetsMainFragment extends Fragment {

    private ColorViewModel colorViewModel;
    private TwitterBookmarkViewModel twitterBookmarkViewModel;

    private RecyclerView scrollView;
    private TwitterBookmarkAdapter twitterBookmarkAdapter;

    private ViewPager2 tweetViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tweets, container, false);

        initViewModel();

        scrollView = root.findViewById(R.id.bookmark_scrollView_twitter);
        twitterBookmarkAdapter = new TwitterBookmarkAdapter(getContext());
        scrollView.setAdapter(twitterBookmarkAdapter);

        colorViewModel.getColorList();
        twitterBookmarkViewModel.getAllBookmark();

        tweetViewPager = root.findViewById(R.id.pagerTweets);

        return root;
    }

    private void initViewModel(){

        //initialize color view model
        colorViewModel = new ViewModelProvider(getActivity()).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                getViewLifecycleOwner(),
                new Observer<List<Color>>() {
                    @Override
                    public void onChanged(List<Color> colors) {
                        twitterBookmarkAdapter.setColorList(colors);
                    }
                }
        );

        twitterBookmarkViewModel = new ViewModelProvider(getActivity()).get(TwitterBookmarkViewModel.class);

        twitterBookmarkViewModel.getAllBookmark().observe(
                getViewLifecycleOwner(),
                new Observer<List<TwitterBookmark>>() {
                    @Override
                    public void onChanged(List<TwitterBookmark> twitterBookmarks) {
                        twitterBookmarkAdapter.setTwitterBookmarkList(twitterBookmarks);
                    }
                }
        );

    }

}