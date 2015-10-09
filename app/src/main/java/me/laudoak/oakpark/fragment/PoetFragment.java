package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.PoetAdapter;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.net.query.QueryVerse;
import me.laudoak.oakpark.widget.message.AppMsg;
import me.laudoak.oakpark.widget.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-8 at 20:22.
 */
public class PoetFragment extends XBaseFragment implements
        PagingListView.LoadCallback,
        QueryVerse.QueryCallback
{

    private PagingListView pagingListView;
    private PoetAdapter adapter;

    private int currPage;

    public static PoetFragment newIastance()
    {
        PoetFragment fragment = new PoetFragment();
        return fragment;
    }

    /*XBaseFragment abstract method*/
    @Override
    public void initData() {
        currPage = 0;
        adapter = new PoetAdapter(context);
    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_poet,container,false);
    }

    @Override
    public void buildViews(View view) {
        pagingListView = (PagingListView) view.findViewById(R.id.lv_poet);
        pagingListView.setLoadCallback(this);
        pagingListView.setAdapter(adapter);
    }
    /**/

    @Override
    public void onLoadMore() {
        new QueryVerse(context,currPage++,this);
    }


    /*Query callback*/
    @Override
    public void onFailure(String why) {
        AppMsg.makeText(context,why,AppMsg.STYLE_CONFIRM).show();
    }

    @Override
    public void onSuccess(boolean hasMore, List<Verse> results) {
        pagingListView.onLoadCompleted(hasMore,results);
    }
}
