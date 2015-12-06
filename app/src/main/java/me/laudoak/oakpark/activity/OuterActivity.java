package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.OuterPoetAdapter;
import me.laudoak.oakpark.adapter.PagingComAdapter;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryOuterVerse;
import me.laudoak.oakpark.ui.message.AppMsg;

/**
 * Created by LaudOak on 2015-11-14 at 14:25.
 */
public class OuterActivity extends XBaseActivity implements QueryOuterVerse.QueryCallback{

    private static final String TAG = "OuterActivity";

    @Bind(R.id.activity_outer_collapsing) CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_outer_cover) SimpleDraweeView cover;

    @Bind(R.id.activity_outer_toolbar) Toolbar toolbar;

    @Bind(R.id.activity_outer_recycler) RecyclerView recyclerView;

    @Bind(R.id.activity_outer_avatar) SimpleDraweeView avatar;

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
            Uri uri = Uri.parse(outerPoet.getCoverURL());
            cover.setAspectRatio(1.67f);
            cover.setImageURI(uri);
        }else
        {
            Uri uri = Uri.parse("res://me.luadoak.oakpark/"+R.drawable.sower);
            cover.setAspectRatio(1.67f);
            cover.setImageURI(uri);
        }

        /**init avatar*/
        if (null!=outerPoet.getAvatarURL())
        {
            Uri uri = Uri.parse(outerPoet.getAvatarURL());
            avatar.setImageURI(uri);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        new QueryOuterVerse(this,outerPoet,this);

    }

    /**query callback*/
    @Override
    public void onFailure(String why)
    {
        AppMsg.makeText(this,"Query Failure",AppMsg.STYLE_ALERT).show();
    }

    @Override
    public void onSuccess(List<Verse> results)
    {
        adapter = new OuterPoetAdapter(this,results);
        recyclerView.setAdapter(adapter);
    }
}
