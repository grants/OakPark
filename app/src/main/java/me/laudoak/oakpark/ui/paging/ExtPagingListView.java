package me.laudoak.oakpark.ui.paging;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import me.laudoak.oakpark.view.EndDividerView;

/**
 * Created by LaudOak on 2015-12-15 at 21:56.
 */
public class ExtPagingListView extends ListView implements
        AbsListView.OnScrollListener ,
        EndDividerView.ReloadCallback
{
    private static final String TAG = ExtPagingListView.class.getName();

    private Context context;

    private EndDividerView footerView;

    private int totalCount;
    private int lastItem;

    private boolean isLoading;
    private boolean hasMoreItems;

    private ListAdapter adapter;

    private ExtListViewLoadCallback loadCallback;

    public ExtPagingListView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public ExtPagingListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ExtPagingListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        Log.d(TAG,"init()");

        isLoading = false;
        hasMoreItems = true;

        footerView = new EndDividerView(context);
        addFooterView(footerView);
        footerView.setReloadCallback(this);

        //!!!don't forget add listener to this class
        this.setOnScrollListener(this);
    }

    //
    public void onLoadCompleted(boolean hasMore,List<? extends Object> newDatas)
    {
        Log.d(TAG, "onLoadCompleted(boolean hasMore,List<? extends Object> newDatas)" + "hasMore:" + hasMore);
        setHasMoreItems(hasMore);
        setIsLoading(false);
        if (null != newDatas && newDatas.size() > 0)
        {
            if (adapter instanceof XBasePagingAdapter)
            {
                ((XBasePagingAdapter) adapter).addItems(newDatas);
            }

        }
    }

    public void hideFooter()
    {
        footerView.hideAll();
    }

    public void onLoadFailed()
    {
        Log.d(TAG, "onLoadFailed()");
        footerView.failed();
    }
    //

    //Getter & Setter
    public boolean isLoading()
    {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading)
    {
        Log.d(TAG,"setIsLoading(boolean isLoading)");
        this.isLoading = isLoading;
        if (isLoading())
        {
            footerView.load();
        }
    }

    public boolean isHasMoreItems()
    {
        return hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems)
    {
        Log.d(TAG,"setHasMoreItems(boolean hasMoreItems)");
        this.hasMoreItems = hasMoreItems;
        if ( !isHasMoreItems())
        {
            footerView.end();
        }else
        {
            footerView.hideAll();
        }
    }

    //end of Getter & Setter


    @Override
    public ListAdapter getAdapter()
    {
        return super.getAdapter();
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        //Caution: don't forget call super method;
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    public ExtListViewLoadCallback getLoadCallback()
    {
        return loadCallback;
    }

    public void setLoadCallback(ExtListViewLoadCallback loadCallback)
    {
        this.loadCallback = loadCallback;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i)
    {
        //do nothing here
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        Log.d(TAG,"onScroll...");

        this.lastItem = firstVisibleItem + visibleItemCount;
        this.totalCount = totalItemCount;
        if ( !isLoading() && isHasMoreItems() && (this.totalCount == this.lastItem) && null != loadCallback )
        {
            setIsLoading(true);
            loadCallback.onLoadMore();
        }
    }

    /**when click loadfailed text reload*/
    @Override
    public void reload()
    {
        setIsLoading(true);
        loadCallback.onReload();
    }

    public interface ExtListViewLoadCallback
    {
        void onLoadMore();
        void onReload();
    }
}
