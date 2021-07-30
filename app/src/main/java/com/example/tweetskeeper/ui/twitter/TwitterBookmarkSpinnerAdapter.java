package com.example.tweetskeeper.ui.twitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweetskeeper.R;
import com.example.tweetskeeper.database.color.Color;
import com.example.tweetskeeper.database.twitter.bookmark.TwitterBookmark;

import java.util.List;

public class TwitterBookmarkSpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<TwitterBookmark> bookmarkList;
    List<Color> colorList;

    public TwitterBookmarkSpinnerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    public void setBookmarkList(List<TwitterBookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }

    public void setBothList(List<Color> colorList, List<TwitterBookmark> bookmarkList){
        this.colorList = colorList;
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.spinner_color_iv);
        TextView text = (TextView) view.findViewById(R.id.spinner_color_tv);

        text.setText(bookmarkList.get(position).getName());
        icon.setImageResource(R.drawable.ic_icon_bookmark);
        icon.setColorFilter(context.getResources().getColor(Color.getId(colorList.get(bookmarkList.get(position).getColor() - 1).getName(), R.color.class)));
        return view;
    }
}
