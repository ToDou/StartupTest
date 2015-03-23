package com.test.tudou.startuptest.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.tudou.startuptest.R;
import com.test.tudou.startuptest.adapter.MainListAdapter;
import com.test.tudou.startuptest.ui.view.ShapeTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.activity_main_list)
    ListView mPrimaryList;

    private MainListAdapter mMainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setupPrimaryList();

    }

    private void setupPrimaryList() {
        mMainListAdapter = new MainListAdapter(MainActivity.this);
        mPrimaryList.setAdapter(mMainListAdapter);
        mPrimaryList.setOnItemClickListener(this);
        mPrimaryList.setVerticalScrollBarEnabled(false);
        mPrimaryList.setItemChecked(0, true);
        mPrimaryList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = v;
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMainListAdapter.setCheckPosition(position);
    }
}
