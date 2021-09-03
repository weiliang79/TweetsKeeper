package com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage;

import com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.other.OtherBookmarkManageFragment;
import com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.twitter.TwitterBookmarkManageFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BookmarkManageViewPagerAdapter extends FragmentStateAdapter {

    public BookmarkManageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public BookmarkManageViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0: return new TwitterBookmarkManageFragment();
            case 1: return new OtherBookmarkManageFragment();
            default: return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
