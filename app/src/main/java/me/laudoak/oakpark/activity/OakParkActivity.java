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
import android.widget.TextView;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.ViewPagerAdapter;
import me.laudoak.oakpark.ctrl.xv.AbXVOberver;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.fragment.AbSupCommentFragment;
import me.laudoak.oakpark.fragment.SupCommentFragment;
import me.laudoak.oakpark.fragment.SupShareFragment;
import me.laudoak.oakpark.fragment.SupWhisperFragment;
import me.laudoak.oakpark.fragment.XVHFragment;
import me.laudoak.oakpark.view.LanternTextView;

/**
 * Created by LaudOak on 2015-10-16 at 21:46.
 */
public class OakParkActivity extends XBaseActivity implements AbXVOberver{

    private static final String TAG = OakParkActivity.class.getName();

    @Bind(R.id.activity_oakpark_drawer) DrawerLayout drawerLayout;
    @Bind(R.id.sup_head_date) LanternTextView dateCode;
    @Bind(R.id.sup_main_container_viewpager) ViewPager supPager;
    @Bind(R.id.sup_head_indicator) ImageView indicator;
    @Bind(R.id.sliding_layout) SlidingUpPanelLayout slidingUpPanelLayout;

    private int indicatorWidth;
    private int currViewPagerPage;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private XVHFragment xvhFragment;
    private SupWhisperFragment whisperFragment;
    private AbSupCommentFragment commentFragment;
    private SupShareFragment shareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MobclickAgent.openActivityDurationTrack(false);
        UmengUpdateAgent.update(this);

        setContentView(R.layout.activity_oakpark);
        ButterKnife.bind(this);

        buildFragments();
        buildView();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void buildFragments()
    {
        whisperFragment = SupWhisperFragment.getSingletonInstance();
        commentFragment = SupCommentFragment.getSingletonInstance();
        shareFragment = SupShareFragment.getSingletonInstance();

        xvhFragment = XVHFragment.getSingletonInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.containe_rxverse_fragment,xvhFragment).commit();
    }

    private void buildView()
    {
        buildDrawer();
        buildSUP();
    }

    /*build drawer layout & pager*/
    private void buildDrawer()
    {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void buildSUP()
    {
         /*three indicator button*/
        ImageView whisper = (ImageView) findViewById(R.id.sup_head_whisper);
        ImageView comment = (ImageView) findViewById(R.id.sup_head_comment);
        ImageView share = (ImageView) findViewById(R.id.sup_head_share);

        /*indicator attr*/
        indicatorWidth = indicator.getLayoutParams().width;

        /*init viewpager fragments & update xverse callback*/
        List<Fragment> supFragments = new ArrayList<Fragment>();
        supFragments.add(whisperFragment);
        supFragments.add(commentFragment);
        supFragments.add(shareFragment);
        supPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), supFragments));

        supPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();

                if (currViewPagerPage == 0 && position == 0)
                {
                    layoutParams.leftMargin = (int) (positionOffset * indicatorWidth);
                } else if (currViewPagerPage == 1 && position == 0)
                {
                    layoutParams.leftMargin = (int) (currViewPagerPage * indicatorWidth + (positionOffset - 1) * indicatorWidth);
                } else if (currViewPagerPage == 1 && position == 1)
                {
                    layoutParams.leftMargin = (int) (currViewPagerPage * indicatorWidth + positionOffset
                            * indicatorWidth);
                } else if (currViewPagerPage == 2 && position == 1)
                {
                    layoutParams.leftMargin = (int) (currViewPagerPage * indicatorWidth + ( positionOffset-1)
                            * indicatorWidth);
                }

                indicator.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position)
            {
                currViewPagerPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });


        whisper.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                judgeSUP();
                supPager.setCurrentItem(0);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judgeSUP();
                supPager.setCurrentItem(1);
            }
        });
        share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                judgeSUP();
                supPager.setCurrentItem(2);
            }
        });

    }

    private void judgeSUP()
    {
        if (null!=slidingUpPanelLayout)
        {
            if (    slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN ||
                    slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED ||
                    slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void notifyXVUpdate(XVerse xv)
    {
        dateCode.setLanternText(xv.getDateCode());
    }

}