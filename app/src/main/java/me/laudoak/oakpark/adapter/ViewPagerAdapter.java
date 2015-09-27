package me.laudoak.oakpark.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm , List<Fragment> fragmentList) {
        super(fm);

        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
