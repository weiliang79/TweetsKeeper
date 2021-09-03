package com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.weiliang79.tweetskeeper.R;

import java.util.Objects;

public class BookmarkManageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager2 vpBookmarkManage;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_manage);

        toolbar = findViewById(R.id.toolbar_bookmark_manage);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Manage Bookmark");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        vpBookmarkManage = findViewById(R.id.pagerBookmarkManage);
        bottomNavigationView = findViewById(R.id.bookmark_manage_bottom_nav);

        BookmarkManageViewPagerAdapter viewPagerAdapter = new BookmarkManageViewPagerAdapter(this);
        vpBookmarkManage.setAdapter(viewPagerAdapter);
        vpBookmarkManage.setUserInputEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bottom_nav_twitter:
                        vpBookmarkManage.setCurrentItem(0);
                        return true;
                    case R.id.bottom_nav_other:
                        vpBookmarkManage.setCurrentItem(1);
                        return true;
                    default:
                        return false;
                }

            }
        });

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