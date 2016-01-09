package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.adapter.vholder.XVerseViewHolder;
import me.laudoak.oakpark.entity.core.XVerse;

/**
 * Created by LaudOak on 2015-10-20 at 18:02.
 */
public class XVAdapter extends RecyclerView.Adapter<XVerseViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener
{

    private static final String TAG = XVAdapter.class.getName();

    private Context context;

    private LayoutInflater inflater;

    protected List<XVerse> xVerseList;


    public List<XVerse> getxVerseList()
    {
        return this.xVerseList;
    }

    public XVAdapter(Context context, List<XVerse> list,RecyclerView recy)
    {
        this.inflater = LayoutInflater.from(context);
        this.xVerseList = list;
        this.context = context;

    }

    public void addItem(int position)
    {
        notifyItemInserted(position);
    }

    public void removeItem(int position)
    {
        xVerseList.remove(position);
        notifyItemRemoved(position);
    }

    public XVerse getXVByPosition(int position)
    {
        return this.xVerseList.get(position);
    }

    @Override
    public XVerseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        final View view = inflater.inflate(R.layout.view_item_xverse,parent,false);

        return new XVerseViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(XVerseViewHolder holder, int position)
    {
        XVerse vs = xVerseList.get(position);
        holder.bindData(vs);
    }

    @Override
    public int getItemCount()
    {
        return xVerseList.size();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {

    }
}
