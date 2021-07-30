package com.example.tweetskeeper.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweetskeeper.R;
import com.example.tweetskeeper.database.color.Color;

import java.util.List;

public class ColorSpinnerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Color> colorList;

    public ColorSpinnerAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return colorList.size();
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
        icon.setColorFilter(context.getResources().getColor(Color.getId(colorList.get(position).getName(), R.color.class)));
        text.setText(colorList.get(position).getRealName());
        return view;
    }
}
