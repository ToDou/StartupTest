package com.test.tudou.startuptest.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.tudou.startuptest.R;
import com.test.tudou.startuptest.StartupTestApp;
import com.test.tudou.startuptest.ui.view.shapeview.BubbleImageView;
import com.test.tudou.startuptest.util.ViewHolder;

/**
 * Created by tudou on 15-3-20.
 */
public class MainListAdapter extends BaseAdapter {

    private String[] titles;
    private TypedArray colors;
    private Context mContext;

    public MainListAdapter(Context context) {
        mContext = context;

        titles = StartupTestApp.getAppResources().getStringArray(R.array.first_list);
        colors = StartupTestApp.getAppResources().obtainTypedArray(R.array.primary_list);
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_main, parent, false);
        }

        TextView textTitle = ViewHolder.get(convertView, R.id.text_list_item_main);
        BubbleImageView imageBackground = ViewHolder.get(convertView, R.id.image_list_item_main);
        textTitle.setText(titles[position]);


        imageBackground.setBackgroundColor(colors.getColor(position, R.color.theme_accent_1));

        return convertView;
    }
}
