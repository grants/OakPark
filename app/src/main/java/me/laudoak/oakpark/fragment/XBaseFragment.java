package me.laudoak.oakpark.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LaudOak on 2015-9-27.
 */
public abstract class XBaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = callView(inflater,container);
        buildViews(view);

        return view;
    }

    public abstract void initData();

    public abstract View callView(LayoutInflater inflater,ViewGroup container);
    public abstract void buildViews(View view);
}
