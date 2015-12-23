package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PagingPoetAdapter;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryVerse;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.paging.ExtPagingListView;

/**
 * Created by LaudOak on 2015-10-8 at 20:22.
 */
public class PoetFragment extends XBaseFragment implements
        ExtPagingListView.ExtListViewLoadCallback,
        QueryVerse.QueryCallback
{

    private static final String TAG = PoetFragment.class.getName();

    @Bind(R.id.lv_poet) ExtPagingListView extPagingListView;
    @Bind(R.id.fragment_poet_axle) View axle;
    private PagingPoetAdapter adapter;

    private int currPage;

    @Override
    public void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    public static PoetFragment newIastance()
    {
        return new PoetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        currPage = 0;
        adapter = new PagingPoetAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_poet, container, false);
        ButterKnife.bind(this,view);
        buildViews();

        return view;
    }


    private void buildViews()
    {
        extPagingListView.setLoadCallback(this);
        extPagingListView.setAdapter(adapter);
        extPagingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {

                return false;
            }
        });
        axle.setVisibility(View.GONE);
    }

    /**ExtPagingListView*/
    @Override
    public void onLoadMore()
    {
        new QueryVerse(context, currPage++, this);
    }

    @Override
    public void onReload()
    {
        new QueryVerse(context, currPage, this);
    }


    /*Query callback*/
    @Override
    public void onFailure(String why)
    {
        AppMsg.makeText(context, why, AppMsg.STYLE_CONFIRM).show();
        extPagingListView.onLoadFailed();
    }

    @Override
    public void onSuccess(boolean hasMore, List<Verse> results)
    {
        extPagingListView.onLoadCompleted(hasMore, results);
        axle.setVisibility(View.VISIBLE);
    }
}
