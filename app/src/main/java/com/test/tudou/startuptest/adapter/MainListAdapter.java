package com.test.tudou.startuptest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.test.tudou.startuptest.R;
import com.test.tudou.startuptest.StartupTestApp;
import com.test.tudou.startuptest.ui.view.ShapeTextView;
import com.test.tudou.startuptest.util.ViewHolder;

/**
 * Created by tudou on 15-3-20.
 */
public class MainListAdapter extends BaseAdapter {

    private String[] titles;
    private Context mContext;
    private int mCheckPosition;
    private int[] primaryListColors;

    public MainListAdapter(Context context) {
        mContext = context;
        titles = StartupTestApp.getAppResources().getStringArray(R.array.first_list);
        primaryListColors = StartupTestApp.getAppResources().getIntArray(R.array.primary_list);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_main, parent, false);
        }

        ShapeTextView shapeTextView = ViewHolder.get(convertView, R.id.list_item_main);
        shapeTextView.setText(titles[position]);
        shapeTextView.setBackgroundColor(primaryListColors[position]);

        if (mCheckPosition == position) {
            shapeTextView.showTriangle();
        } else {
            shapeTextView.hideTriangle();
        }

        shapeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckPosition = position;
                notifyDataSetInvalidated();
            }
        });

        return convertView;
    }

    public void setCheckPosition(int position) {
        mCheckPosition = position;
        notifyDataSetInvalidated();
    }
}
