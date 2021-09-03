package com.weiliang79.tweetskeeper.ui.twitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorViewModel;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmarkViewModel;

import java.util.ArrayList;
import java.util.List;

public class TweetsMainFragment extends Fragment implements TwitterBookmarkAdapter.RecyclerViewClickListener, TweetFragment.OptionMenuHandler {

    private ColorViewModel colorViewModel;
    private TwitterBookmarkViewModel twitterBookmarkViewModel;

    private RecyclerView scrollView;
    private TwitterBookmarkAdapter twitterBookmarkAdapter;

    private TweetViewPagerAdapter tweetViewPagerAdapter;

    private ViewPager2 tweetViewPager;

    public TweetsMainFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tweets, container, false);

        initViewModel();
        initViewPager(root);

        scrollView = root.findViewById(R.id.bookmark_scrollView_twitter);
        twitterBookmarkAdapter = new TwitterBookmarkAdapter(getContext(), this);
        scrollView.setAdapter(twitterBookmarkAdapter);

        colorViewModel.getColorList();
        twitterBookmarkViewModel.getAllBookmark();

        return root;
    }

    private void initViewModel(){

        //initialize color view model
        colorViewModel = new ViewModelProvider(getActivity()).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                getViewLifecycleOwner(),
                new Observer<List<BookmarkColor>>() {
                    @Override
                    public void onChanged(List<BookmarkColor> bookmarkColors) {
                        twitterBookmarkAdapter.setColorList(bookmarkColors);
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

                        List<Fragment> fragmentList = new ArrayList<>();
                        for(int i = 0; i < twitterBookmarks.size(); i++){
                            TweetFragment tweetFragment = TweetFragment.newInstance(twitterBookmarks.get(i).getId());
                            fragmentList.add(tweetFragment);
                        }
                        tweetViewPagerAdapter.setFragmentList(fragmentList);
                    }
                }
        );

    }

    private void initViewPager(View root){
        tweetViewPager = root.findViewById(R.id.pagerTweets);
        tweetViewPagerAdapter = new TweetViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        tweetViewPager.setAdapter(tweetViewPagerAdapter);

        tweetViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(twitterBookmarkAdapter.getSelectedPos() < position){
                    scrollPositionAndDirection((LinearLayoutManager) scrollView.getLayoutManager(), scrollView, position, true);
                } else if(twitterBookmarkAdapter.getSelectedPos() > position) {
                    scrollPositionAndDirection((LinearLayoutManager) scrollView.getLayoutManager(), scrollView, position, false);
                }

                twitterBookmarkAdapter.changeSelectedItem(position);
            }
        });
    }

    //if direction is true, which mean scroll to right, otherwise scroll to left
    public void scrollPositionAndDirection(LinearLayoutManager llm, RecyclerView rv, int position, boolean direction){
        if(direction){
            int lastItemVisible = llm.findLastCompletelyVisibleItemPosition();

            if(position > lastItemVisible){
                rv.smoothScrollToPosition(position);
            }
            return;
        } else {
            int firstItemVisible = llm.findFirstCompletelyVisibleItemPosition();

            if(position < firstItemVisible){
                rv.smoothScrollToPosition(position);
            }
            return;
        }
    }

    @Override
    public void recyclerViewClicked(View view, int position){
        tweetViewPager.setCurrentItem(position);
    }

    @Override
    public void setLockViewPager(boolean setLocked){
        tweetViewPager.setUserInputEnabled(setLocked);
        twitterBookmarkAdapter.setClickable(setLocked);
    }

}