package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-12-15 at 18:41.
 */
public class AboutActivity extends XBaseActivity
{
    private static final String TAG = AboutActivity.class.getName();

    @Bind(R.id.toolbar_about) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        buildBar();
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
