package com.test.tudou.startuptest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.test.tudou.startuptest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tudou on 15-3-23.
 */
public class MainActivityFragment extends Fragment {

    @InjectView(R.id.list_main_fragment)
    ListView mList;

    private String[] data = new String[20];
    private int key;
    private AnimationAdapter mAnimAdapter;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        key = getArguments().getInt("key");
        createData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        mAnimAdapter = new ScaleInAnimationAdapter(arrayAdapter);
        mAnimAdapter.setAbsListView(mList);
        mList.setAdapter(mAnimAdapter);

    }

    private void createData() {
        for (int i = 0; i < 20; i++) {
            data[i] = "key:" + key + "    value:" + i;
        }
    }


}
