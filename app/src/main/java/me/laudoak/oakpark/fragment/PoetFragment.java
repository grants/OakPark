package me.laudoak.oakpark.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.widget.paging.PagingListView;

/**
 * Created by LaudOak on 2015-10-8 at 20:22.
 */
public class PoetFragment extends XBaseFragment implements PagingListView.LoadCallback{

    private PagingListView pagingListView;

    public static PoetFragment newIastance()
    {
        PoetFragment fragment = new PoetFragment();
        return fragment;
    }

    /*XBaseFragment abstract method*/
    @Override
    public void initData() {

    }

    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_poet,null);
    }

    @Override
    public void buildViews(View view) {

    }
    /**/

    @Override
    public void onLoadMore() {

    }


}
