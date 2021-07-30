package com.example.tweetskeeper.ui.twitter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tweetskeeper.R;
import com.example.tweetskeeper.database.color.Color;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmark;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TwitterBookmarkAdapter extends RecyclerView.Adapter<TwitterBookmarkAdapter.TwitterBookmarkViewHolder> {

    class TwitterBookmarkViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout listItem;
        public final ImageView bookmarkItem;
        public final TextView bookmarkName;
        final TwitterBookmarkAdapter mAdapter;

        TwitterBookmarkViewHolder(View itemView, TwitterBookmarkAdapter mAdapter){
            super(itemView);
            this.mAdapter = mAdapter;
            bookmarkItem = itemView.findViewById(R.id.bookmark_image);
            bookmarkName = itemView.findViewById(R.id.bookmark_name);
            listItem = itemView.findViewById(R.id.list_item);

            listItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.e("twitter touch", "long clicked");
                    return false;
                }
            });
        }
    }

    private LayoutInflater mInflater;
    private List<Color> colorList;
    private List<TwitterBookmark> twitterBookmarkList;
    final Context context;

    TwitterBookmarkAdapter(Context context){
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    public void setTwitterBookmarkList(List<TwitterBookmark> twitterBookmarkList) {
        this.twitterBookmarkList = twitterBookmarkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TwitterBookmarkAdapter.TwitterBookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View itemView = mInflater.inflate(R.layout.bookmark_item, parent, false);
        return new TwitterBookmarkViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TwitterBookmarkAdapter.TwitterBookmarkViewHolder viewHolder, int position){
        String bookmarkName = twitterBookmarkList.get(position).getName();
        String bookmarkColor = colorList.get(twitterBookmarkList.get(position).getColor() - 1).getName();
        viewHolder.bookmarkName.setText(bookmarkName);
        viewHolder.bookmarkItem.setImageResource(R.drawable.ic_icon_bookmark);
        viewHolder.bookmarkItem.setColorFilter(context.getResources().getColor(Color.getId(bookmarkColor, R.color.class)));
    }

    @Override
    public int getItemCount(){
        return twitterBookmarkList == null ? 0 : twitterBookmarkList.size();
    }
}
