package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-10-25 at 10:16.
 */
public class LoadingFragment extends XBaseFragment {

    public static LoadingFragment newInstance()
    {
        LoadingFragment fragment = new LoadingFragment();

        return fragment;

    }

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading,null);
    }

    @Override
    public void buildViews(View view) {

    }
}
