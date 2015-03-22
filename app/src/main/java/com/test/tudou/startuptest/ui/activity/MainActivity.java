package com.test.tudou.startuptest.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.test.tudou.startuptest.R;
import com.test.tudou.startuptest.adapter.MainListAdapter;
import com.test.tudou.startuptest.ui.view.ShapeTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.activity_main_list)
    ListView mList;

    @InjectView(R.id.background_text)
    ShapeTextView mBackgroundText;

    private MainListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
        mAdapter = new MainListAdapter(MainActivity.this);
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackgroundText.reDraw();
    }
}
