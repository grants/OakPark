package me.laudoak.oakpark.ui.paging;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaudOak on 2015-10-4 at 22:45.
 */
public abstract class XBasePagingAdapter<T> extends BaseAdapter {

    private static final String TAG = XBasePagingAdapter.class.getName();

    protected Context context;

    protected List<T> datas;

    public List<T> getDatas()
    {
        return this.datas;
    }

    public XBasePagingAdapter(Context context,List<T> datas)
    {
        this.context = context;
        this.datas = datas;
    }

    public XBasePagingAdapter(Context context)
    {
        this.context = context;
        datas = new ArrayList<T>();
    }

    public XBasePagingAdapter(List<T> datas)
    {
        this.datas = datas;
    }

    public void addItems(List<T> datas)
    {
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void removeAllDatas()
    {
        this.datas.clear();
        this.notifyDataSetChanged();
    }



}
