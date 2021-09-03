package com.weiliang79.tweetskeeper.ui.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;

import com.weiliang79.tweetskeeper.MainActivity;
import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetViewModel;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetWithMedias;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class TweetFragment extends Fragment {

    private Context context;
    private MainActivity mainActivity;
    private Drawable navDrawable;

    private int optionsMenuStatus = 0;

    private Toolbar toolbar;

    private TwitterBookmark twitterBookmark;

    private TwitterTweetViewModel twitterTweetViewModel;

    private TextView tvTweetEmpty;

    private RecyclerView recyclerView;
    private TweetAdapter tweetAdapter;
    private OptionMenuHandler optionMenuHandler;

    public TweetFragment () {

    }

    public static TweetFragment newInstance(int twitterBookmarkId){
        TweetFragment tweetFragment = new TweetFragment();

        Bundle args = new Bundle();
        args.putInt("bookmarkId", twitterBookmarkId);
        tweetFragment.setArguments(args);

        return tweetFragment;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try{
            optionMenuHandler = (OptionMenuHandler) getParentFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(getParentFragment().toString() + " must implement MyInterface ");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).setNavItemChecked(R.id.nav_tweets);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                if(optionsMenuStatus != 0){

                    tweetAdapter.setShowCheckBox(false);
                    optionMenuHandler.setLockViewPager(true);

                    toolbar.setNavigationIcon(navDrawable);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).onSupportNavigateUp();
                        }
                    });
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.action_menu);
                    toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary));

                    mainActivity.setDrawerLock(false);

                    optionsMenuStatus = 0;

                } else {

                    requireActivity().finish();

                }

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu, menu);
        navDrawable = toolbar.getNavigationIcon();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_move:

                if(tweetAdapter.getTweetsList().size() == 0){

                    Toast.makeText(getContext(), "Bookmark List are empty.", Toast.LENGTH_SHORT).show();

                } else {
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.option_done_menu);
                    toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);

                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tweetAdapter.setUnchecked();
                            tweetAdapter.setShowCheckBox(false);
                            optionMenuHandler.setLockViewPager(true);

                            toolbar.setNavigationIcon(navDrawable);
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((MainActivity) getActivity()).onSupportNavigateUp();
                                }
                            });
                            toolbar.getMenu().clear();
                            toolbar.inflateMenu(R.menu.action_menu);
                            toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary));

                            mainActivity.setDrawerLock(false);
                        }
                    });

                    mainActivity.setDrawerLock(true);

                    optionsMenuStatus = 1;

                    tweetAdapter.setShowCheckBox(true);
                    optionMenuHandler.setLockViewPager(false);

                    toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary_edit));

                }

                return true;
            case R.id.menu_delete:

                if(tweetAdapter.getTweetsList().size() == 0){

                    Toast.makeText(getContext(), "Bookmark List are empty.", Toast.LENGTH_SHORT).show();

                } else {

                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.option_done_menu);
                    toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);

                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tweetAdapter.setUnchecked();
                            tweetAdapter.setShowCheckBox(false);
                            optionMenuHandler.setLockViewPager(true);

                            toolbar.setNavigationIcon(navDrawable);
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((MainActivity) getActivity()).onSupportNavigateUp();
                                }
                            });
                            toolbar.getMenu().clear();
                            toolbar.inflateMenu(R.menu.action_menu);
                            toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary));

                            mainActivity.setDrawerLock(false);
                        }
                    });

                    mainActivity.setDrawerLock(true);

                    optionsMenuStatus = 2;

                    tweetAdapter.setShowCheckBox(true);
                    optionMenuHandler.setLockViewPager(false);

                    toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary_edit));

                }

                return true;
            case R.id.menu_done:

                if(tweetAdapter.getTweetsList().size() == 0){

                    Toast.makeText(getContext(), "Bookmark List are empty.", Toast.LENGTH_SHORT).show();

                } else {

                    List<TwitterTweetWithMedias> selectedTweetsList = tweetAdapter.getSelectedTweetsList();

                    if (selectedTweetsList.size() != 0) {
                        switch (optionsMenuStatus) {
                            case 1:
                                new ChangeBookmarkAsyncTask(getContext(), selectedTweetsList).execute();
                                break;
                            case 2:

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                alertDialog.setTitle("Delete Confirmation");
                                alertDialog.setMessage("Are you sure want to delete these tweets?");
                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        twitterTweetViewModel.deleteTweetList(selectedTweetsList);
                                        Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });


                                alertDialog.setCancelable(true);
                                alertDialog.show();
                                break;
                            default:
                        }

                    }
                    tweetAdapter.setUnchecked();
                    tweetAdapter.setShowCheckBox(false);
                    optionMenuHandler.setLockViewPager(true);

                    toolbar.setNavigationIcon(navDrawable);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).onSupportNavigateUp();
                        }
                    });
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.action_menu);
                    toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.color_primary));

                    mainActivity.setDrawerLock(false);

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv, container, false);

        initViewModel();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvTweetEmpty = view.findViewById(R.id.tvListEmpty);

        recyclerView = view.findViewById(R.id.rvList);
        tweetAdapter = new TweetAdapter(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.divider_layer));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(tweetAdapter);

        twitterTweetViewModel.getTweetsAndMediasWithBookmarkId(getArguments().getInt("bookmarkId"));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    private void initViewModel(){

        twitterTweetViewModel = new ViewModelProvider(getActivity()).get(TwitterTweetViewModel.class);

        twitterTweetViewModel.getTweetsAndMediasWithBookmarkId(getArguments().getInt("bookmarkId")).observe(
                getViewLifecycleOwner(),
                new Observer<List<TwitterTweetWithMedias>>() {
                    @Override
                    public void onChanged(List<TwitterTweetWithMedias> twitterTweets) {

                        if(twitterTweets.isEmpty()){
                            tvTweetEmpty.setVisibility(View.VISIBLE);
                            tvTweetEmpty.setText("Empty");
                        } else {
                            tvTweetEmpty.setVisibility(View.GONE);
                        }

                        tweetAdapter.setTwitterTweetList(twitterTweets);
                    }
                }
        );

    }

    public interface OptionMenuHandler {
        void setLockViewPager(boolean setLocked);
    }

    private class ChangeBookmarkAsyncTask extends AsyncTask<Void, Void, ArrayList<List>> {

        Context context;
        List<TwitterTweetWithMedias> twitterTweetWithMediasList;
        AlertDialog dialogBuilder;
        TweetsKeeperRoomDatabase db;

        public ChangeBookmarkAsyncTask(Context context, List<TwitterTweetWithMedias> twitterTweetWithMediasList){
            this.context = context;
            this.twitterTweetWithMediasList = twitterTweetWithMediasList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(context).create();
            db = TweetsKeeperRoomDatabase.getDbInstance(context);
        }

        @Override
        protected ArrayList<List> doInBackground(Void... params) {
            ArrayList<List> result = new ArrayList<>();
            result.add(db.colorDao().getAllColorList());
            result.add(db.twitterBookmarkDao().getAllBookmarkList());
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);
            View view = getLayoutInflater().inflate(R.layout.change_bookmark_dialog, null);

            Spinner bookmarkSpinner = view.findViewById(R.id.changeBookmarkColorSpinner);
            Button btnSave = view.findViewById(R.id.btn_save_change_bookmark);
            Button btnCancel = view.findViewById(R.id.btn_cancel_change_bookmark);

            List<BookmarkColor> colorList = result.get(0);
            List<TwitterBookmark> bookmarkList = result.get(1);

            TwitterBookmarkSpinnerAdapter adapter = new TwitterBookmarkSpinnerAdapter(context);
            adapter.setBothList(colorList, bookmarkList);
            bookmarkSpinner.setAdapter(adapter);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TwitterBookmark selectedBookmark = bookmarkList.get(bookmarkSpinner.getSelectedItemPosition());

                    for(int i = 0; i < twitterTweetWithMediasList.size(); i++){
                        twitterTweetWithMediasList.get(i).twitterTweet.setIsChecked(false);
                        twitterTweetWithMediasList.get(i).twitterTweet.setBookmark_id(selectedBookmark.getId());
                    }

                    twitterTweetViewModel.updateTweetList(twitterTweetWithMediasList);

                    Toast.makeText(getContext(), "Move Successful", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(view);
            dialogBuilder.show();

        }
    }

}
