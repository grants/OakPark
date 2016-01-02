package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PagingFriendAdapter;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryFriendVerse;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.paging.ExtPagingListView;

/**
 * Created by LaudOak on 2016-1-1 at 20:45.
 */
public class FriendActivity extends XBaseActivity implements
        ExtPagingListView.ExtListViewLoadCallback,
        QueryFriendVerse.QueryFriendCallback
{

    private static final String TAG = FriendActivity.class.getName();

    @Bind(R.id.activity_friend_lv) ExtPagingListView listView;
    @Bind(R.id.toolbar_friend) Toolbar toolbar;
    @Bind(R.id.hint_nofollow) TextView noFollowHint;
    @Bind(R.id.activity_friend_axle) View axle;

    private PagingFriendAdapter adapter;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.bind(this);

        currentPage = 0;
        adapter = new PagingFriendAdapter(this);

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
        getSupportActionBar().setTitle("关注");
    }


    private void buildViews()
    {
        listView.setLoadCallback(this);
        listView.setAdapter(adapter);
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


    /**Paging ListView Callback*/
    @Override
    public void onLoadMore()
    {
        new QueryFriendVerse(this,this,currentPage++);
    }

    @Override
    public void onReload()
    {
        new QueryFriendVerse(this,this,currentPage);
    }


    /**Query Callback*/
    @Override
    public void onFailure(String why)
    {
        AppMsg.makeText(this,why,AppMsg.STYLE_ALERT).show();
    }

    @Override
    public void onNoFollow()
    {
        if (noFollowHint.getVisibility() == View.GONE)
        {
            noFollowHint.setVisibility(View.VISIBLE);
            axle.setVisibility(View.GONE);
            listView.hideFooter();
        }
    }

    @Override
    public void onSuccess(boolean hasMore, List<Verse> results)
    {
        listView.onLoadCompleted(hasMore,results);
    }
}
