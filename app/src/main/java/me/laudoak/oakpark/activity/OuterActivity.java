package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.OuterPoetAdapter;
import me.laudoak.oakpark.adapter.PagingComAdapter;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryOuterVerse;
import me.laudoak.oakpark.ui.circle.CircleImageView;
import me.laudoak.oakpark.ui.message.AppMsg;

/**
 * Created by LaudOak on 2015-11-14 at 14:25.
 */
public class OuterActivity extends XBaseActivity implements QueryOuterVerse.QueryCallback{

    private static final String TAG = "OuterActivity";

    @Bind(R.id.activity_outer_collapsing) CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_outer_cover) ImageView cover;

    @Bind(R.id.activity_outer_toolbar) Toolbar toolbar;

    @Bind(R.id.activity_outer_recycler) RecyclerView recyclerView;

    @Bind(R.id.activity_outer_avatar) CircleImageView avatar;

    private Poet outerPoet;
    private OuterPoetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);
        ButterKnife.bind(this);

        getExtraIntent();
        buildBar();
        buildViews();
    }

    private void buildBar()
    {
        setSupportActionBar(toolbar);
        toolbar.setTitle(outerPoet.getUsername());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    public void getExtraIntent()
    {
        Intent intent = getIntent();
        outerPoet = (Poet) intent.getSerializableExtra(PagingComAdapter.EXTRA_POET);
    }

    private void buildViews()
    {
        /**init cover*/
        if (null != outerPoet.getCoverURL())
        {
            ImageLoader.getInstance().displayImage(outerPoet.getCoverURL(),cover);
        }

        /**init avatar*/
        if (null!=outerPoet.getAvatarURL())
        {
            ImageLoader.getInstance().displayImage(outerPoet.getAvatarURL(), avatar);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        new QueryOuterVerse(this,outerPoet,this);

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

    /**query callback*/
    @Override
    public void onFailure(String why)
    {
        Log.d(TAG,"onFailure"+why);
        AppMsg.makeText(this,"Query Failure",AppMsg.STYLE_ALERT).show();
    }

    @Override
    public void onSuccess(List<Verse> results)
    {
        Log.d(TAG,"onSuccess(List<Verse> results)"+results.size());
        adapter = new OuterPoetAdapter(this,results);
        recyclerView.setAdapter(adapter);
    }
}
