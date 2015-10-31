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

import java.util.ArrayList;
import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.ViewPagerAdapter;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.fragment.SUPCommentFragment;
import me.laudoak.oakpark.fragment.SUPShareFragment;
import me.laudoak.oakpark.fragment.SUPWhisperFragment;
import me.laudoak.oakpark.fragment.XVHFragment;
import me.laudoak.oakpark.utils.CalUtil;

/**
 * Created by LaudOak on 2015-10-16 at 21:46.
 */
public class OakParkActivity extends XBaseActivity implements XVHFragment.XVUpdateCallback{

    private static final String TAG = "OakParkActivity";


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private SlidingUpPanelLayout slidingUpPanelLayout;

    private ViewPager supPager;
    private ImageView indicator;
    private int indicatorWidth;
    private int curPage;
    private ImageView whisper,comment,share;
    private TextView dateCode;

    private NXVUCallback whisperNotier;
    private NXVUCallback commentNotier;
    private NXVUCallback shareNotier;

    private List<Fragment> supFragments;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_oakpark);
    }

    @Override
    public void buildView()
    {
        initDrawer();

        buildSUP();
    }

    /*build drawer layout & pager*/
    private void initDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_oakpark_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
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

    private void buildSUP() {

        XVHFragment fragment = XVHFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.containe_rxverse_fragment, fragment).commit();

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

         /*three indicator button*/
        whisper = (ImageView) findViewById(R.id.sup_head_whisper);
        comment = (ImageView) findViewById(R.id.sup_head_comment);
        share = (ImageView) findViewById(R.id.sup_head_share);

        /*dateCode*/
        dateCode = (TextView) findViewById(R.id.sup_head_date);

        /*indicator attr*/
        indicator = (ImageView) findViewById(R.id.sup_head_indicator);
        indicatorWidth = indicator.getLayoutParams().width;

        /*init viewpager*/
        supPager = (ViewPager) findViewById(R.id.sup_main_container_viewpager);


        /*init viewpager fragments & update xverse callback*/
        supFragments = new ArrayList<Fragment>();

        SUPWhisperFragment whisperFragment = SUPWhisperFragment.newInstance();
        whisperNotier =  whisperFragment;
        supFragments.add(whisperFragment);

        SUPCommentFragment commentFragment = SUPCommentFragment.newInstance();
        commentNotier = commentFragment;
        supFragments.add(commentFragment);

        SUPShareFragment shareFragment = SUPShareFragment.newInstance();
        shareNotier = shareFragment;
        supFragments.add(shareFragment);
        /**/

        supPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), supFragments));

        supPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();

                if (curPage == 0 && position == 0)
                {
                    layoutParams.leftMargin = (int) (positionOffset * indicatorWidth);
                } else if (curPage == 1 && position == 0)
                {
                    layoutParams.leftMargin = (int) (curPage * indicatorWidth + (positionOffset - 1) * indicatorWidth);
                } else if (curPage == 1 && position == 1)
                {
                    layoutParams.leftMargin = (int) (curPage * indicatorWidth + positionOffset
                            * indicatorWidth);
                } else if (curPage == 2 && position == 1)
                {
                    layoutParams.leftMargin = (int) (curPage * indicatorWidth + ( positionOffset-1)
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
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public void onUpdateXV(XVerse xv) {
        whisperNotier.onUpdateXV(xv);
        commentNotier.onUpdateXV(xv);
        shareNotier.onUpdateXV(xv);
        dateCode.setText(CalUtil.dateCodeToDateString(xv.getDateCode()));
    }

    public interface NXVUCallback
    {
        void onUpdateXV(XVerse xv);
    }

}