package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPShareFragment extends XBaseFragment {

    public static SUPShareFragment newInstance()
    {
        SUPShareFragment fragment = new SUPShareFragment();

        return fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_share,null);
    }

    @Override
    public void buildViews(View view) {

    }
}
