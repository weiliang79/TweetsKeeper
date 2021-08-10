package com.weiliang79.tweetskeeper.ui.other;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherBookmarkAdapter extends RecyclerView.Adapter<OtherBookmarkAdapter.OtherBookmarkViewHolder> {

    class OtherBookmarkViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout listItem;
        public final ImageView bookmarkItem;
        public final TextView bookmarkName;
        final OtherBookmarkAdapter mAdapter;

        OtherBookmarkViewHolder(View itemView, OtherBookmarkAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            bookmarkItem = itemView.findViewById(R.id.bookmark_image);
            bookmarkName = itemView.findViewById(R.id.bookmark_name);
            listItem = itemView.findViewById(R.id.list_item);

            listItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Log.e("other touch", "long clicked");
                    return false;
                }
            });
        }
    }

    private LayoutInflater mInflater;
    private List<BookmarkColor> bookmarkColorList;
    private List<OtherBookmark> otherBookmarkList;
    final Context context;

    OtherBookmarkAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setColorList(List<BookmarkColor> bookmarkColorList) {
        this.bookmarkColorList = bookmarkColorList;
        notifyDataSetChanged();

    }

    public void setOtherBookmarkList(List<OtherBookmark> otherBookmarkList) {
        this.otherBookmarkList = otherBookmarkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherBookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = mInflater.inflate(R.layout.bookmark_item, parent, false);
        return new OtherBookmarkViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherBookmarkViewHolder viewHolder, int position) {

        if(bookmarkColorList != null && otherBookmarkList != null){
            String bookmarkName = otherBookmarkList.get(position).getName();
            viewHolder.bookmarkName.setText(bookmarkName);
            viewHolder.bookmarkItem.setImageResource(R.drawable.ic_icon_bookmark);
            viewHolder.bookmarkItem.setColorFilter(Color.parseColor(bookmarkColorList.get(otherBookmarkList.get(position).getColor() - 1).getHexCode()));
        }

    }

    @Override
    public int getItemCount() {
        return otherBookmarkList == null ? 0 : otherBookmarkList.size();
    }

}
