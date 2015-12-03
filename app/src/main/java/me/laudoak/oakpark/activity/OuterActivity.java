package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-11-14 at 14:25.
 */
public class OuterActivity extends XBaseActivity {

    private static final String TAG = "OuterActivity";

    @Bind(R.id.activity_outer_collapsing) CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_outer_cover) SimpleDraweeView cover;

    @Bind(R.id.activity_outer_toolbar) Toolbar toolbar;

    @Bind(R.id.activity_outer_recycler) RecyclerView recyclerView;

    @Bind(R.id.activity_outer_avatar) SimpleDraweeView avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);
    }



}
