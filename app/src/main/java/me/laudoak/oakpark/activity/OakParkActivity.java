package me.laudoak.oakpark.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.XVHFragment;

/**
 * Created by LaudOak on 2015-10-16 at 21:46.
 */
public class OakParkActivity extends XBaseActivity {

    private static final String TAG = "OakParkActivity";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ViewPager supPager;
    private SlidingUpPanelLayout supLayout;

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

        getSupportFragmentManager().beginTransaction().replace(R.id.containe_rxverse_fragment,new XVHFragment()).commit();



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
