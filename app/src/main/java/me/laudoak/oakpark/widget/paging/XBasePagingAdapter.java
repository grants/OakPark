package me.laudoak.oakpark.widget.paging;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaudOak on 2015-10-4 at 22:45.
 */
public abstract class XBasePagingAdapter<T> extends BaseAdapter {

    private static final String TAG = "XBasePagingAdapter<T>";

    protected Context context;

    protected List<T> datas;

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
