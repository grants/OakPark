package me.laudoak.oakpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LaudOak on 2015-10-25 at 11:26.
 */
public class SUpPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;

    public SUpPagerAdapter(FragmentManager fm , List<Fragment> fragmentList) {
        super(fm);

        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
