package com.weiliang79.tweetskeeper.ui.settings.bookmarkmanage.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;
import com.weiliang79.tweetskeeper.database.other.bookmark.OtherBookmark;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OtherBookmarkManageAdapter extends RecyclerView.Adapter<OtherBookmarkManageAdapter.OtherBookmarkManageViewHolder>{

    class OtherBookmarkManageViewHolder extends RecyclerView.ViewHolder {

        final OtherBookmarkManageAdapter adapter;
        final ShapeableImageView ivBookmarkIcon;
        final TextView tvBookmarkName;
        final ImageButton btnEdit;
        final ImageButton btnDelete;

        OtherBookmarkManageViewHolder(View itemView, OtherBookmarkManageAdapter adapter){
            super(itemView);
            this.adapter = adapter;
            ivBookmarkIcon = itemView.findViewById(R.id.iv_bookmark_manage_icon);
            tvBookmarkName = itemView.findViewById(R.id.tv_bookmark_manage_name);
            btnEdit = itemView.findViewById(R.id.btn_edit_bookmark_manage);
            btnDelete = itemView.findViewById(R.id.btn_delete_bookmark_manage);
        }

    }

    private LayoutInflater inflater;
    private List<BookmarkColor> colorList;
    private List<OtherBookmark> bookmarkList;
    final Context context;
    private static ButtonClickListener buttonClickListener;

    public OtherBookmarkManageAdapter(Context context, ButtonClickListener buttonClickListener){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.buttonClickListener = buttonClickListener;
    }

    public void setColorList(List<BookmarkColor> colorList){
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    public void setBookmarkList(List<OtherBookmark> bookmarkList){
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OtherBookmarkManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.manage_bookmark_item, parent, false);
        return new OtherBookmarkManageViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherBookmarkManageViewHolder holder, int position) {

        if(colorList != null && bookmarkList != null){
            holder.ivBookmarkIcon.setImageResource(R.drawable.ic_icon_bookmark);
            holder.ivBookmarkIcon.setColorFilter(Color.parseColor(colorList.get(bookmarkList.get(position).getColor() - 1).getHexCode()));

            holder.tvBookmarkName.setText(bookmarkList.get(position).getName());

            if(bookmarkList.get(position).getId() == 1){
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
            }

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.editButtonClicked(holder.getPosition());
                }
            });

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickListener.deleteButtonClicked(holder.getPosition());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return bookmarkList == null ? 0 : bookmarkList.size();
    }

    public interface ButtonClickListener{
        public void editButtonClicked(int id);
        public void deleteButtonClicked(int id);
    }

}
