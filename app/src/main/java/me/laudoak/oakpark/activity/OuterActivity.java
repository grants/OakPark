package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.OuterPoetAdapter;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryOuterVerse;
import me.laudoak.oakpark.ui.circle.CircleImageView;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.text.FluidTextView;
import me.laudoak.oakpark.view.FollowButtonView;
import me.laudoak.oakpark.view.LvStateView;

/**
 * Created by LaudOak on 2015-11-14 at 14:25.
 */
public class OuterActivity extends XBaseActivity implements QueryOuterVerse.QueryCallback
{

    private static final String TAG = OuterActivity.class.getName();

    public static final String EXTRA_POET = "extra_poet";

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Bind(R.id.activity_outer_collapsing) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.activity_outer_toolbar) Toolbar toolbar;
    @Bind(R.id.activity_outer_app_bar) AppBarLayout alphAppBar;
    @Bind(R.id.activity_outer_toolbar_title) TextView toolBarTitle;
    @Bind(R.id.activity_outer_cover_title_container) FrameLayout titleContainer;

    @Bind(R.id.activity_outer_cover) ImageView cover;
    @Bind(R.id.usrsub_followbtn) FollowButtonView followButtonView;
    @Bind(R.id.usrsub_avatar) CircleImageView avatar;
    @Bind(R.id.usrsub_nick) TextView nick;
    @Bind(R.id.usrsub_sign) FluidTextView sign;


    @Bind(R.id.activity_outer_lvsta) LvStateView lvStateView;
    @Bind(R.id.activity_outer_recycler) RecyclerView recyclerView;

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

    private void buildBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(outerPoet.getUsername());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
        alphAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });
    }


//    // 设置自动滑动的动画效果
//    private void initParallaxValues() {
//        CollapsingToolbarLayout.LayoutParams petDetailsLp =
//                (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();
//
//        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
//                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();
//
//        petDetailsLp.setParallaxMultiplier(0.9f);
//        petBackgroundLp.setParallaxMultiplier(0.3f);
//
//        mIvPlaceholder.setLayoutParams(petDetailsLp);
//        mFlTitleContainer.setLayoutParams(petBackgroundLp);
//    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage)
    {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR)
        {
            if (!mIsTheTitleVisible)
            {
                startAlphaAnimation(toolBarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else
        {
            if (mIsTheTitleVisible)
            {
                startAlphaAnimation(toolBarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage)
    {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS)
        {
            if (mIsTheTitleContainerVisible)
            {
                startAlphaAnimation(titleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else
        {
            if (!mIsTheTitleContainerVisible)
            {
                startAlphaAnimation(titleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility)
    {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public void getExtraIntent()
    {
        Intent intent = getIntent();
        outerPoet = (Poet) intent.getSerializableExtra(EXTRA_POET);
    }

    private void buildViews()
    {
        if (null != outerPoet)
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
            lvStateView.loading();
            new QueryOuterVerse(this,outerPoet,this);

            nick.setText(outerPoet.getUsername());
            sign.setText(outerPoet.getSign());
            followButtonView.setSecondaryPoet(outerPoet);
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

    /**query callback*/
    @Override
    public void onFailure(String why)
    {
        lvStateView.loadFailed();
        Log.d(TAG,"onFailure"+why);
        AppMsg.makeText(this,"Query Failure",AppMsg.STYLE_ALERT).show();
    }

    @Override
    public void onSuccess(List<Verse> results)
    {
        if (results.size() == 0)
        {
            lvStateView.noData();
        }else
        {
            lvStateView.hide();
        }

        Log.d(TAG,"onSuccess(List<Verse> results)"+results.size());
        adapter = new OuterPoetAdapter(this,results);
        recyclerView.setAdapter(adapter);
    }
}
