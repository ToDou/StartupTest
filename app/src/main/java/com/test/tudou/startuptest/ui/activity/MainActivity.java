package com.test.tudou.startuptest.ui.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
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

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.test.tudou.startuptest.R;
import com.test.tudou.startuptest.adapter.MainListAdapter;
import com.test.tudou.startuptest.ui.fragment.MainActivityFragment;
import com.test.tudou.startuptest.ui.view.ShapeTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.activity_main_list)
    ListView mPrimaryList;

    private AnimationAdapter mAnimAdapter;
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
        mAnimAdapter = new ScaleInAnimationAdapter(mMainListAdapter);
        mAnimAdapter.setAbsListView(mPrimaryList);
        mPrimaryList.setAdapter(mAnimAdapter);
        mPrimaryList.setOnItemClickListener(this);
        mPrimaryList.setVerticalScrollBarEnabled(false);
        mPrimaryList.setItemChecked(0, true);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMainListAdapter.setCheckPosition(position);
    }
}
