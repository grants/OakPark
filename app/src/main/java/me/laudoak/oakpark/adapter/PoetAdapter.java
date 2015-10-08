package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.widget.fittext.ExpandableLinear;
import me.laudoak.oakpark.widget.paging.XBasePagingAdapter;


/**
 * Created by LaudOak on 2015-10-8 at 20:29.
 */
public class PoetAdapter extends XBasePagingAdapter<Verse> {
    
    private LayoutInflater inflater;

    public PoetAdapter(Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    public PoetAdapter(List<Verse> datas) {
        super(datas);
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

        ViewHolder holder;
        if (null==convertView)
        {
            convertView = inflater.inflate(R.layout.view_item_verse,null);
            holder = new ViewHolder(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != holder)
        {
            holder.bindData(datas.get(position));
        }

        return convertView;
    }

    private class ViewHolder
    {
        TextView date;
        SimpleDraweeView draweeView;
        TextView title;
        TextView author;
        ExpandableLinear expandVerse;

        public ViewHolder(View view)
        {
            date = (TextView) view.findViewById(R.id.item_verse_date);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.item_verse_image);
            title = (TextView) view.findViewById(R.id.item_verse_title);
            author = (TextView) view.findViewById(R.id.item_verse_author);
            expandVerse = (ExpandableLinear) view.findViewById(R.id.item_verse_linear_expand);

            view.setTag(this);
        }

        void bindData(Verse verse)
        {

        }
    }
}
