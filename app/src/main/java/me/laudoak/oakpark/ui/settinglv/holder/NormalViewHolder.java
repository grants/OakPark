package me.laudoak.oakpark.ui.settinglv.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;

/**
 * Created by LaudOak on 2015-11-30 at 23:14.
 */
public class NormalViewHolder extends AbViewHolder
{

    private TextView title;

    public NormalViewHolder(Context context, View view) {
        super(context,view);
        title = (TextView) view.findViewById(R.id.item_stv_normal_tv);
    }

    @Override
    public void bind(AbFilling filling) {
        title.setText(filling.getTitle());
    }
}
