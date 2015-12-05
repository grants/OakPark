package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.PoetryWebSite;
import me.laudoak.oakpark.ui.text.FluidTextView;

/**
 * Created by LaudOak on 2015-12-5 at 13:07.
 */
public class PoetryWebAdapter extends XBaseAdapter<PoetryWebSite>
{
    public PoetryWebAdapter(List<PoetryWebSite> dataList, Context context)
    {
        super(dataList, context);
    }

    @Override
    View callView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (null == convertView)
        {
            convertView = inflater.inflate(R.layout.view_item_poetry_website,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bind(datas.get(position));

        return convertView;
    }

    static class ViewHolder
    {

        @Bind(R.id.item_poetrysite_title) FluidTextView title;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this,view);
        }

        public void bind(PoetryWebSite site)
        {
            title.setText(site.getName());
        }
    }
}
