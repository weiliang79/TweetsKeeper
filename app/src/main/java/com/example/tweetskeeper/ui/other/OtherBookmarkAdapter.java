package com.example.tweetskeeper.ui.other;

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
import com.example.tweetskeeper.database.other.bookmark.OtherBookmark;

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
                    Log.e("other touch", "long clicked");
                    return false;
                }
            });
        }
    }

    private LayoutInflater mInflater;
    private List<Color> colorList;
    private List<OtherBookmark> otherBookmarkList;
    final Context context;

    OtherBookmarkAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
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
        String bookmarkName = otherBookmarkList.get(position).getName();
        String bookmarkColor = colorList.get(otherBookmarkList.get(position).getColor() - 1).getName();
        viewHolder.bookmarkName.setText(bookmarkName);
        viewHolder.bookmarkItem.setImageResource(R.drawable.ic_icon_bookmark);
        viewHolder.bookmarkItem.setColorFilter(context.getResources().getColor(Color.getId(bookmarkColor, R.color.class)));
    }

    @Override
    public int getItemCount() {
        return otherBookmarkList == null ? 0 : otherBookmarkList.size();
    }

}
