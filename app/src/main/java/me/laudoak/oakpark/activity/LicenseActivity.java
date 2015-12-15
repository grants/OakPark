package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.LicenseFragment;

/**
 * Created by LaudOak on 2015-12-15 at 20:50.
 */
public class LicenseActivity extends XBaseActivity
{

    private static final String TAG = LicenseActivity.class.getName();


    @Bind(R.id.toolbar_license) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MobclickAgent.openActivityDurationTrack(false);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);
        buildBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_license, LicenseFragment.newInstance()).commit();
    }

    @Override
    protected void onResume()
    {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    private void buildBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                this.finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
