package me.laudoak.oakpark.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
        QueryVerse.QueryCallback {

    private PagingListView pagingListView;
    private PoetAdapter adapter;

    private View rootView;

    private int currPage;

    public static PoetFragment newIastance()
    {
        PoetFragment fragment = new PoetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currPage = 0;
        adapter = new PoetAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.fragment_poet,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }


    private void buildViews(View view) {
        pagingListView = (PagingListView) view.findViewById(R.id.lv_poet);
        pagingListView.setLoadCallback(this);
        pagingListView.setAdapter(adapter);
        pagingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

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
