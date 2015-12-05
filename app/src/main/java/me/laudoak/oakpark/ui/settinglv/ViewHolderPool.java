package me.laudoak.oakpark.ui.settinglv;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;
import me.laudoak.oakpark.ui.settinglv.holder.AbViewHolder;
import me.laudoak.oakpark.ui.settinglv.holder.LoadingViewHolder;
import me.laudoak.oakpark.ui.settinglv.holder.NormalViewHolder;
import me.laudoak.oakpark.ui.settinglv.holder.ToggleViewHolder;

/**
 * Created by LaudOak on 2015-11-30 at 23:17.
 */
public class ViewHolderPool
{

    private static final String TAG = "ViewHolderPool";

    public static final int TYPE_ISTV_NORMAL = 101;
    public static final int TYPE_ISTV_WITH_TOGGLE = 102;
    public static final int TYPE_ISTV_WITH_LOAD = 103;
    public static final int TYPE_ISTV_WITH_RIGHTTXT = 104;
    public static final int TYPE_ISTV_WITH_SUBTXT = 105;

    private LayoutInflater inflater;
    private List<AbFilling> fillings;

    public ViewHolderPool (LayoutInflater inflater,List<AbFilling> fils)
    {
        this.inflater = inflater;
        this.fillings = fils;
    }

    public int getViewTypeCount()
    {
        return 5;
    }

    public int getItemViewType(int position)
    {
        return fillings.get(position).getViewYype();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        AbViewHolder viewHolder = null;

        if (null == convertView)
        {
            switch (getItemViewType(position))
            {
                case TYPE_ISTV_NORMAL:
                {
                    convertView = inflater.inflate(R.layout.view_item_stv_norm,parent,false);
                    viewHolder = new NormalViewHolder(convertView);

                    break;
                }
                case TYPE_ISTV_WITH_TOGGLE:
                {
                    convertView = inflater.inflate(R.layout.view_item_stv_swibt,parent,false);
                    viewHolder = new ToggleViewHolder(convertView);

                    break;
                }
                case TYPE_ISTV_WITH_LOAD:
                {
                    convertView = inflater.inflate(R.layout.view_item_stv_load,parent,false);
                    viewHolder = new LoadingViewHolder(convertView);

                    break;
                }
            }

            if (convertView != null)
            {
                convertView.setTag(viewHolder);
            }

        }else
        {
            viewHolder = (AbViewHolder) convertView.getTag();
        }

        if (null != viewHolder)
        {
            viewHolder.bind(fillings.get(position));
        }

        return convertView;
    }

}
