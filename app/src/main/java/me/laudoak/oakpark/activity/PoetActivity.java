package me.laudoak.oakpark.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.PoetFragment;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-9-30.
 */
public class PoetActivity extends XBaseActivity
{

    private static final String TAG = PoetActivity.class.getName();

    @Bind(R.id.toolbar_poet) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poet);
        ButterKnife.bind(this);
        buildView();
        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void buildView()
    {
        buildBar();
        PoetFragment fragment = PoetFragment.newIastance();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_poet_container,fragment).commit();
    }


    private void buildBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (null != UserProxy.currentPoet(this))
        {
            getSupportActionBar().setTitle(UserProxy.currentPoet(this).getUsername());
        }
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
