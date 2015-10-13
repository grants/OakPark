package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.widget.clocktext.ClockText;
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
            convertView = inflater.inflate(R.layout.view_item_verse,parent,false);
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
        ClockText date;
        SimpleDraweeView draweeView;
        TextView title;
        TextView author;
        ExpandableLinear expandVerse;

        public ViewHolder(View view)
        {
            date = (ClockText) view.findViewById(R.id.item_verse_date);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.item_verse_image);
            title = (TextView) view.findViewById(R.id.item_verse_title);
            author = (TextView) view.findViewById(R.id.item_verse_author);
            expandVerse = (ExpandableLinear) view.findViewById(R.id.item_verse_linear_expand);

            view.setTag(this);
        }

        void bindData(Verse verse)
        {
            //
            date.setPushTime(verse.getPushTime());

            if (null!=verse.getImageURL())
            {
                Uri uri = Uri.parse(verse.getImageURL());
                draweeView.setAspectRatio(1.67f);
                draweeView.setImageURI(uri);
            }

            title.setText(verse.getTitle());
            author.setText(verse.getAuthor());
            expandVerse.setText(verse.getVerse());
        }
    }
}
