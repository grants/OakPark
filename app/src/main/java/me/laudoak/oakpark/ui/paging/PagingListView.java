package me.laudoak.oakpark.ui.paging;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-10-4 at 22:43.
 */
public class PagingListView extends ListView implements AbsListView.OnScrollListener{

    private static final String TAG = PagingListView.class.getName();

    private Context context;

    private View footerView;

    private int totalCount;
    private int lastItem;
    private boolean isLoading;
    private boolean hasMoreItems;

    private LoadCallback callback;

    private ListAdapter adapter;

    public PagingListView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public PagingListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PagingListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        isLoading = false;
        hasMoreItems = true;

        footerView = inflate(context, R.layout.view_load_more,null);
        footerView.setVisibility(View.GONE);
        addFooterView(footerView);

        //!!!don't forget add listener to this class
        this.setOnScrollListener(this);
    }

    /*Getter & Setter*/
    public boolean hasMoreItems()
    {
        return this.hasMoreItems;
    }

    public void setHasMoreItems(boolean hasMoreItems)
    {
        this.hasMoreItems = hasMoreItems;
        if (!hasMoreItems)
        {
            removeFooterView(footerView);
        }
    }

    public boolean isLoading()
    {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading)
    {
        this.isLoading = isLoading;
        if (isLoading())
        {
            footerView.setVisibility(VISIBLE);
        }else
        {
            footerView.setVisibility(View.GONE);
        }
    }
    /**/

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        this.lastItem = firstVisibleItem+visibleItemCount;
        this.totalCount = totalItemCount;
        if ( !isLoading && hasMoreItems && (this.totalCount == this.lastItem) && null!=callback )
        {
            setIsLoading(true);
            callback.onLoadMore();
        }
    }

    public void onLoadCompleted(boolean hasMore,List<? extends Object> newDatas)
    {
        setHasMoreItems(hasMore);
        setIsLoading(false);
        if (null!=newDatas&&newDatas.size()>0)
        {
            if (adapter instanceof XBasePagingAdapter)
            {
                ((XBasePagingAdapter) adapter).addItems(newDatas);
            }
        }
    }


    public void setLoadCallback(LoadCallback call)
    {
        this.callback = call;
    }

    public interface LoadCallback
    {
        void onLoadMore();
    }

}
