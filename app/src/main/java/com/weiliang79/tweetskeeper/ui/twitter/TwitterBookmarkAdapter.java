package com.weiliang79.tweetskeeper.ui.twitter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.twitter.bookmark.TwitterBookmark;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TwitterBookmarkAdapter extends RecyclerView.Adapter<TwitterBookmarkAdapter.TwitterBookmarkViewHolder> {

    class TwitterBookmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final LinearLayout listItem;
        public final ImageView bookmarkItem;
        public final TextView bookmarkName;
        final TwitterBookmarkAdapter mAdapter;

        TwitterBookmarkViewHolder(View itemView, TwitterBookmarkAdapter mAdapter){
            super(itemView);
            itemView.setOnClickListener(this);
            this.mAdapter = mAdapter;
            bookmarkItem = itemView.findViewById(R.id.bookmark_image);
            bookmarkName = itemView.findViewById(R.id.bookmark_name);
            listItem = itemView.findViewById(R.id.list_item);
        }

        @Override
        public void onClick(View view) {

            if(clickable){
                notifyItemChanged(selectedPos);
                selectedPos = getLayoutPosition();
                notifyItemChanged(selectedPos);
                recyclerViewClickListener.recyclerViewClicked(view, this.getPosition());
            }

        }
    }

    private int selectedPos = 0;
    private boolean clickable = true;
    private LayoutInflater mInflater;
    private List<BookmarkColor> bookmarkColorList;
    private List<TwitterBookmark> twitterBookmarkList;
    final Context context;
    private static RecyclerViewClickListener recyclerViewClickListener;

    TwitterBookmarkAdapter(Context context, RecyclerViewClickListener recyclerViewClickListener){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public void setColorList(List<BookmarkColor> bookmarkColorList) {
        this.bookmarkColorList = bookmarkColorList;
        notifyDataSetChanged();
    }

    public void setTwitterBookmarkList(List<TwitterBookmark> twitterBookmarkList) {
        this.twitterBookmarkList = twitterBookmarkList;
        notifyDataSetChanged();
    }

    public void changeSelectedItem(int selectedPos){
        notifyDataSetChanged();
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    public void setClickable(boolean clickable){
        this.clickable = clickable;
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

        viewHolder.itemView.setSelected(selectedPos == position);

        if(twitterBookmarkList != null && bookmarkColorList != null){

            String bookmarkName = twitterBookmarkList.get(position).getName();
            viewHolder.bookmarkName.setText(bookmarkName.length() < 10 ? bookmarkName : bookmarkName.substring(0, Math.min(bookmarkName.length(), 10)) + "...");
            viewHolder.bookmarkItem.setImageResource(R.drawable.ic_icon_bookmark);
            viewHolder.bookmarkItem.setColorFilter(Color.parseColor(bookmarkColorList.get(twitterBookmarkList.get(position).getColor() - 1).getHexCode()));
            viewHolder.listItem.setTooltipText(bookmarkName);

        }

    }

    @Override
    public int getItemCount(){
        return twitterBookmarkList == null ? 0 : twitterBookmarkList.size();
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewClicked(View v, int position);
    }
}
