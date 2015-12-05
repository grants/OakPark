package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PagingPoetAdapter;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.net.bmob.query.QueryVerse;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-8 at 20:22.
 */
public class PoetFragment extends XBaseFragment implements
        PagingListView.LoadCallback,
        QueryVerse.QueryCallback
{

    private static final String TAG = "PoetFragment";

    private PagingListView pagingListView;
    private PagingPoetAdapter adapter;

    private View rootView;

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
        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.fragment_poet, container, false);
        } else if (null != (rootView.getParent()))
        {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }


    private void buildViews(View view)
    {
        pagingListView = (PagingListView) view.findViewById(R.id.lv_poet);
        pagingListView.setLoadCallback(this);
        pagingListView.setAdapter(adapter);
        pagingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {

                return false;
            }
        });
    }

    @Override
    public void onLoadMore()
    {
        new QueryVerse(context, currPage++, this);
    }


    /*Query callback*/
    @Override
    public void onFailure(String why)
    {
        AppMsg.makeText(context, why, AppMsg.STYLE_CONFIRM).show();
    }

    @Override
    public void onSuccess(boolean hasMore, List<Verse> results)
    {
        pagingListView.onLoadCompleted(hasMore, results);
    }
}
