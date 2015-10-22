package me.laudoak.oakpark.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.ViewPagerAdapter;
import me.laudoak.oakpark.fragment.SUPCommentFragment;
import me.laudoak.oakpark.fragment.SUPShareFragment;
import me.laudoak.oakpark.fragment.SUPWhisperFragment;
import me.laudoak.oakpark.fragment.XVHFragment;

/**
 * Created by LaudOak on 2015-10-16 at 21:46.
 */
public class OakParkActivity extends XBaseActivity {

    private static final String TAG = "OakParkActivity";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ViewPager supPager;
    private ImageView indicator;
    private int indicatorWidth;
    private int curPage;
    private ImageView whisper,comment,share;

    private List<Fragment> supFragments;



    @Override
    protected void setView() {
        setContentView(R.layout.activity_oakpark);

    }

    /*build drawer layout & pager*/
    private void initDrawer()
    {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public void buildView()
    {

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_oakpark_drawer);
        initDrawer();

        XVHFragment fragment = new XVHFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containe_rxverse_fragment, fragment).commit();

        buildSUPFragment();
    }

    private void buildSUPFragment()
    {
        whisper = (ImageView) findViewById(R.id.sup_head_whisper);
        comment = (ImageView) findViewById(R.id.sup_head_comment);
        share = (ImageView) findViewById(R.id.sup_head_share);

        indicator = (ImageView) findViewById(R.id.sup_head_indicator);
        indicatorWidth = indicator.getLayoutParams().width;

        supPager = (ViewPager) findViewById(R.id.sup_main_container_viewpager);


        supFragments = new ArrayList<Fragment>();
        supFragments.add(SUPWhisperFragment.newInstance());
        supFragments.add(SUPCommentFragment.newInstance());
        supFragments.add(SUPShareFragment.newInstance());

        supPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), supFragments));

        supPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();

                if (curPage == 0 && position == 0)
                {
                    layoutParams.rightMargin = (int) (2*indicatorWidth-positionOffset * indicatorWidth);
                } else if (curPage == 1 && position == 0)
                {
                    layoutParams.rightMargin = (int) (curPage * indicatorWidth + (positionOffset - 1) * indicatorWidth);
                } else if (curPage == 1 && position == 1)
                {
                    layoutParams.rightMargin = (int) (curPage * indicatorWidth + positionOffset
                            * indicatorWidth);
                } else if (curPage == 2 && position == 1)
                {
                    layoutParams.rightMargin = (int) (curPage * indicatorWidth + ( positionOffset-1)
                            * indicatorWidth);
                }

                indicator.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                curPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        whisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supPager.setCurrentItem(0);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        supPager.setCurrentItem(1);
                    }
                });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supPager.setCurrentItem(2);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

}
