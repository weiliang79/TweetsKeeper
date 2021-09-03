package com.weiliang79.tweetskeeper.ui.twitter;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.weiliang79.tweetskeeper.MainActivity;
import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.twitter.tweet.TwitterTweetWithMedias;
import com.weiliang79.tweetskeeper.ui.CustomLinearLayoutManager;
import com.weiliang79.tweetskeeper.ui.adapter.ImagePreviewAdapter;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

    class TweetViewHolder extends RecyclerView.ViewHolder {

        final TweetAdapter adapter;
        final ConstraintLayout clTweet;
        final ShapeableImageView ivUserProfilePic;
        final CheckBox cbTweet;
        final TextView tvUserName;
        final TextView tvUserScreenName;
        final TextView tvStatus;
        final TextView tvDateCreated;
        final RecyclerView rvMedia;
        final Button btnToTwitter;

        TweetViewHolder(View itemView, TweetAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            cbTweet = itemView.findViewById(R.id.cbTweet);
            clTweet = itemView.findViewById(R.id.clTweet);
            ivUserProfilePic = itemView.findViewById(R.id.ivTweet_UserProfilePic);
            tvUserName = itemView.findViewById(R.id.tvTweet_userName);
            tvUserScreenName = itemView.findViewById(R.id.tvTweet_userScreenName);
            tvStatus = itemView.findViewById(R.id.tvTweet_status);
            tvDateCreated = itemView.findViewById(R.id.tvTweet_dateCreated);
            rvMedia = itemView.findViewById(R.id.rvTweet_media);
            btnToTwitter = itemView.findViewById(R.id.btn_tweet_to_twitter);
        }

    }

    private boolean showCheckBox = false;
    private LayoutInflater inflater;
    private List<TwitterTweetWithMedias> twitterTweetList;
    private ViewGroup.MarginLayoutParams params;
    final Context context;

    TweetAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTwitterTweetList(List<TwitterTweetWithMedias> twitterTweetList) {
        this.twitterTweetList = twitterTweetList;
        notifyDataSetChanged();
    }

    public void setShowCheckBox(boolean showCheckBox){
        this.showCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public List<TwitterTweetWithMedias> getTweetsList(){
        return twitterTweetList;
    }

    public List<TwitterTweetWithMedias> getSelectedTweetsList(){
        List<TwitterTweetWithMedias> twitterTweetsList = new ArrayList<>();
        for(int i = 0; i < twitterTweetList.size(); i++){
            if(twitterTweetList.get(i).twitterTweet.getIsChecked()){
                twitterTweetsList.add(twitterTweetList.get(i));
            }
        }
        return twitterTweetsList;
    }

    public void setUnchecked(){
        for(int i = 0; i < twitterTweetList.size(); i++){
            twitterTweetList.get(i).twitterTweet.setIsChecked(false);
        }
    }

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.tweet_item, parent, false);

        return new TweetViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, @SuppressLint("RecyclerView") int position) {

        params = (ViewGroup.MarginLayoutParams) holder.clTweet.getLayoutParams();

        holder.cbTweet.setChecked(twitterTweetList.get(position).twitterTweet.getIsChecked());
        holder.cbTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twitterTweetList.get(position).twitterTweet.setIsChecked(holder.cbTweet.isChecked());
            }
        });

        if(showCheckBox){
            holder.cbTweet.setVisibility(View.VISIBLE);
            params.setMargins(0, 0, 0, 0);

            holder.btnToTwitter.animate()
                    .alpha(0)
                    .setDuration(100)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            holder.btnToTwitter.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

        } else {

            holder.cbTweet.setVisibility(View.GONE);
            int leftPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8,
                    context.getResources().getDisplayMetrics()
            );
            params.setMargins(leftPx, 0, 0, 0);

            holder.btnToTwitter.animate()
                    .alpha(1)
                    .setDuration(100)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            holder.btnToTwitter.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

        }

        holder.clTweet.setLayoutParams(params);

        holder.clTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Holder", "Holder " + position + " Clicked");
                Intent intent = new Intent((MainActivity) context, ViewTweetActivity.class);
                intent.putExtra("id", twitterTweetList.get(position).twitterTweet.getId());
                context.startActivity(intent);
            }
        });

        holder.rvMedia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    return v.performClick();
                }
                return false;
            }
        });

        holder.rvMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Holder", "Holder " + position + " Clicked");
                Intent intent = new Intent((MainActivity) context, ViewTweetActivity.class);
                intent.putExtra("id", twitterTweetList.get(position).twitterTweet.getId());
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(twitterTweetList.get(position).twitterTweet.getUser_profile_pic_url())
                .centerCrop()
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .fallback(R.drawable.ic_user)
                .into(holder.ivUserProfilePic);

        holder.tvUserName.setText(twitterTweetList.get(position).twitterTweet.getUser_name());
        holder.tvUserScreenName.setText("@" + twitterTweetList.get(position).twitterTweet.getUser_screen_name());
        holder.tvStatus.setText(twitterTweetList.get(position).twitterTweet.getStatus());

        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        holder.tvDateCreated.setText(dateFormat.format(twitterTweetList.get(position).twitterTweet.getCreated_date()));

        if(twitterTweetList.get(position).twitterMedia != null && twitterTweetList.get(position).twitterMedia.size() != 0){

            CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            holder.rvMedia.setLayoutManager(customLinearLayoutManager);
            ImagePreviewAdapter imagePreviewAdapter = new ImagePreviewAdapter(context, twitterTweetList.get(position).twitterMedia);
            holder.rvMedia.setHasFixedSize(true);
            holder.rvMedia.setAdapter(imagePreviewAdapter);
            imagePreviewAdapter.notifyDataSetChanged();

        } else {
            holder.rvMedia.setVisibility(View.GONE);
            //holder.glMedia.setVisibility(View.GONE);
        }

        holder.btnToTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterTweetList.get(position).twitterTweet.getUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return twitterTweetList == null ? 0 : twitterTweetList.size();
    }

}
