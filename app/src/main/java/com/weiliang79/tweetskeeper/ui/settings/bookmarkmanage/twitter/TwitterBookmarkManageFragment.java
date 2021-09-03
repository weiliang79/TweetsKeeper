package com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.twitter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.TweetsKeeperRoomDatabase;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.color.ColorViewModel;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmarkViewModel;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweet;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetDao;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetWithMedias;
import com.weiliang79.tweetskeeper.ui.adapter.ColorSpinnerAdapter;
import com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.twitter.TwitterBookmarkManageAdapter;
import com.weiliang79.tweetskeeper.ui.twitter.TwitterBookmarkSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class TwitterBookmarkManageFragment extends Fragment implements TwitterBookmarkManageAdapter.ButtonCLickListener {

    private TweetsKeeperRoomDatabase db;
    private ColorViewModel colorViewModel;
    private TwitterBookmarkViewModel twitterBookmarkViewModel;

    private RecyclerView rvList;
    private TwitterBookmarkManageAdapter adapter;
    private TextView tvListEmpty;

    public TwitterBookmarkManageFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rv, container, false);

        db = TweetsKeeperRoomDatabase.getDbInstance(getContext());
        initModel();

        rvList = root.findViewById(R.id.rvList);
        tvListEmpty = root.findViewById(R.id.tvListEmpty);
        adapter = new TwitterBookmarkManageAdapter(getContext(), this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getDrawable(R.drawable.divider_layer));
        rvList.addItemDecoration(dividerItemDecoration);
        rvList.setAdapter(adapter);

        colorViewModel.getColorList();
        twitterBookmarkViewModel.getAllBookmark();

        return root;
    }

    private void initModel(){

        colorViewModel = new ViewModelProvider(getActivity()).get(ColorViewModel.class);

        colorViewModel.getColorList().observe(
                getViewLifecycleOwner(),
                new Observer<List<BookmarkColor>>() {
                    @Override
                    public void onChanged(List<BookmarkColor> bookmarkColors) {
                        adapter.setColorList(bookmarkColors);
                    }
                }
        );

        twitterBookmarkViewModel = new ViewModelProvider(getActivity()).get(TwitterBookmarkViewModel.class);

        twitterBookmarkViewModel.getAllBookmark().observe(
                getViewLifecycleOwner(),
                new Observer<List<TwitterBookmark>>() {
                    @Override
                    public void onChanged(List<TwitterBookmark> twitterBookmarks) {

                        if(twitterBookmarks.isEmpty()){
                            tvListEmpty.setVisibility(View.VISIBLE);
                            tvListEmpty.setText("Empty");
                        } else {
                            tvListEmpty.setVisibility(View.GONE);
                        }

                        adapter.setBookmarkList(twitterBookmarks);

                    }
                }
        );

    }

    @Override
    public void editButtonClicked(TwitterBookmark twitterBookmark) {
        new EditBookmarkAsyncTask(twitterBookmark).execute();
    }

    @Override
    public void deleteButtonClicked(TwitterBookmark twitterBookmark) {
        Log.e("Twitter Manage Bookmark", "Delete: " + twitterBookmark.getId());
        new CheckBookmarkAsyncTask(twitterBookmark).execute();
    }

    private class EditBookmarkAsyncTask extends AsyncTask<Void, Void, List<BookmarkColor>> {

        TwitterBookmark twitterBookmark;
        AlertDialog dialogBuilder;

        EditBookmarkAsyncTask(TwitterBookmark twitterBookmark){
            this.twitterBookmark = twitterBookmark;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(getContext()).create();
        }

        @Override
        protected List<BookmarkColor> doInBackground(Void... voids) {
            List<BookmarkColor> result = db.colorDao().getUnusedColor(db.twitterBookmarkDao().getUsedColorIds());
            result.add(0, db.colorDao().getColorWithId(twitterBookmark.getColor()));
            return result;
        }

        @Override
        protected void onPostExecute(List<BookmarkColor> lists) {

            super.onPostExecute(lists);

            View editBookmarkView = getLayoutInflater().inflate(R.layout.edit_bookmark_dialog, null);
            TextView tvTitle = editBookmarkView.findViewById(R.id.editBookmarkTitle);
            EditText etName = editBookmarkView.findViewById(R.id.editBookmarkDialogName);
            Spinner spColor = editBookmarkView.findViewById(R.id.editBookmarkColorSpinner);
            Button btnSave = editBookmarkView.findViewById(R.id.btn_save_edit_bookmark);
            Button btnCancel = editBookmarkView.findViewById(R.id.btn_cancel_edit_bookmark);

            ColorSpinnerAdapter colorSpinnerAdapter = new ColorSpinnerAdapter(getContext());
            colorSpinnerAdapter.setColorList(lists);

            spColor.setAdapter(colorSpinnerAdapter);

            tvTitle.setText(tvTitle.getText() + " - " + twitterBookmark.getName());
            etName.setText(twitterBookmark.getName());

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    twitterBookmark.setName(etName.getText().toString());

                    if(spColor.getSelectedItemPosition() != 0){
                        twitterBookmark.setColor(lists.get(spColor.getSelectedItemPosition()).getId());
                    }

                    twitterBookmarkViewModel.updateBookmark(twitterBookmark);
                    Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(editBookmarkView);
            dialogBuilder.show();

        }

    }

    private class CheckBookmarkAsyncTask extends AsyncTask<Void, Void, ArrayList<List>> {

        TwitterBookmark twitterBookmark;
        AlertDialog dialogBuilder;

        CheckBookmarkAsyncTask(TwitterBookmark twitterBookmark){
            this.twitterBookmark = twitterBookmark;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(getContext()).create();
        }

        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            ArrayList<List> result = new ArrayList<>();
            result.add(db.colorDao().getAllColorList());
            result.add(db.twitterBookmarkDao().getAllBookmarkList());
            result.add(db.twitterTweetDao().getTweetsAndMediasListWithBookmarkId(twitterBookmark.getId()));
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);

            List<BookmarkColor> colorList = result.get(0);
            List<TwitterBookmark> twitterBookmarkList = result.get(1);
            List<TwitterTweetWithMedias> twitterTweetWithMediasList = result.get(2);

            for(int i = 0; i < twitterBookmarkList.size(); i++){
                if(twitterBookmarkList.get(i).getId() == twitterBookmark.getId()){
                    twitterBookmarkList.remove(i);
                    break;
                }
            }

            View dialogView = getLayoutInflater().inflate(R.layout.delete_bookmark_dialog, null);
            TextView tvSubtitle = dialogView.findViewById(R.id.subtitle_delete_bookmark_dialog);
            CheckBox chDeleteCheck = dialogView.findViewById(R.id.ch_delete_bookmark_dialog);
            Spinner spBookmark = dialogView.findViewById(R.id.sp_delete_bookmark_dialog);
            Button btnCancel = dialogView.findViewById(R.id.btn_cancel_delete_bookmark);
            Button btnDelete = dialogView.findViewById(R.id.btn_delete_delete_bookmark);

            if(twitterTweetWithMediasList.isEmpty()){
                tvSubtitle.setText(R.string.twitter_delete_dialog_no_tweet);
                chDeleteCheck.setVisibility(View.GONE);
                spBookmark.setVisibility(View.GONE);
            } else {
                tvSubtitle.setText(R.string.twitter_delete_dialog_have_tweet);
                btnDelete.setText(R.string.twitter_delete_dialog_move_tweet_btn);
                chDeleteCheck.setVisibility(View.VISIBLE);
                chDeleteCheck.setText(R.string.twitter_delete_dialog_ch_delete);
                spBookmark.setVisibility(View.VISIBLE);

                TwitterBookmarkSpinnerAdapter adapter = new TwitterBookmarkSpinnerAdapter(getContext());
                adapter.setBothList(colorList, twitterBookmarkList);
                spBookmark.setAdapter(adapter);

                chDeleteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            btnDelete.setText(R.string.twitter_delete_dialog_delete_tweet_btn);
                            btnDelete.setTextColor(Color.RED);
                            spBookmark.setEnabled(false);
                        } else {
                            btnDelete.setText(R.string.twitter_delete_dialog_move_tweet_btn);
                            btnDelete.setTextColor(getContext().getColor(R.color.color_primary));
                            spBookmark.setEnabled(true);
                        }
                    }
                });

            }

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(twitterTweetWithMediasList.isEmpty()){
                        twitterBookmarkViewModel.deleteBookmark(twitterBookmark);
                        dialogBuilder.dismiss();
                        Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        dialogBuilder.dismiss();
                        if(chDeleteCheck.isChecked()){
                            new DeleteBookmarkAsyncTask(twitterTweetWithMediasList, true, twitterBookmark, null).execute();
                        } else {
                            new DeleteBookmarkAsyncTask(twitterTweetWithMediasList, false, twitterBookmark, twitterBookmarkList.get(spBookmark.getSelectedItemPosition())).execute();
                        }

                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();

        }

    }

    private class DeleteBookmarkAsyncTask extends AsyncTask<Void, Integer, Void> {

        List<TwitterTweetWithMedias> tweetWithMediasList;
        boolean confirmDelete;
        TwitterBookmark from;
        TwitterBookmark target;
        AlertDialog dialogBuilder;
        ProgressBar progressBar;
        int progressBarMaxValue;

        DeleteBookmarkAsyncTask(List<TwitterTweetWithMedias> tweetWithMediasList, boolean confirmDelete, TwitterBookmark from, TwitterBookmark target){
            this.tweetWithMediasList = tweetWithMediasList;
            this.confirmDelete = confirmDelete;
            this.from = from;
            this.target = target;
            progressBarMaxValue = tweetWithMediasList.size();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBuilder = new AlertDialog.Builder(getContext()).create();

            View dialogView = getLayoutInflater().inflate(R.layout.delete_bookmark_progress_dialog, null);
            TextView tvSubtitle = dialogView.findViewById(R.id.subtitle_delete_bookmark_progress_dialog);
            progressBar = dialogView.findViewById(R.id.delete_bookmark_progress_bar);
            progressBar.setMax(progressBarMaxValue);

            if(confirmDelete){
                tvSubtitle.setText(R.string.twitter_delete_progress_dialog_delete);
            } else {
                tvSubtitle.setText(R.string.twitter_delete_progress_dialog_move);
            }

            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);
            dialogBuilder.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for(int i = 0; i < tweetWithMediasList.size(); i++){
                if(confirmDelete){
                    db.twitterTweetDao().deleteTweetWithMedias(tweetWithMediasList.get(i));
                } else {
                    TwitterTweetWithMedias tweet = tweetWithMediasList.get(i);
                    tweet.twitterTweet.setBookmark_id(target.getId());
                    db.twitterTweetDao().updateTweetWithMedias(tweet);
                }

                publishProgress(i);
            }

            twitterBookmarkViewModel.deleteBookmark(from);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            dialogBuilder.dismiss();
            Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();

        }
    }

}
