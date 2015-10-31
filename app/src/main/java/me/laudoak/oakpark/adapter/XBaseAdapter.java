package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LaudOak on 2015-10-28 at 22:41.
 */
public abstract class XBaseAdapter<T> extends BaseAdapter {

    protected List<T> datas;
    protected Context context;
    protected LayoutInflater inflater;

    public XBaseAdapter(List<T> dataList, Context context) {

        this.datas = dataList;
        this.context = context;

        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return callView(position, convertView, parent);
    }

    abstract View callView(int position, View convertView, ViewGroup parent);
}
