package com.weiliang79.tweetskeeper.ui.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiliang79.tweetskeeper.R;
import com.weiliang79.tweetskeeper.database.color.BookmarkColor;

import java.util.List;

public class ColorSpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<BookmarkColor> bookmarkColorList;

    public ColorSpinnerAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setColorList(List<BookmarkColor> bookmarkColorList) {
        this.bookmarkColorList = bookmarkColorList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bookmarkColorList.size();
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
        icon.setImageResource(R.drawable.ic_icon_bookmark);
        icon.setColorFilter(Color.parseColor(bookmarkColorList.get(position).getHexCode()));
        text.setText(bookmarkColorList.get(position).getRealName());

        switch (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK){
            case Configuration.UI_MODE_NIGHT_YES:
                text.setTextColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                text.setTextColor(Color.BLACK);
                break;
        }

        return view;
    }
}
