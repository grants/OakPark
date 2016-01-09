package me.laudoak.oakpark.ui.settinglv.holder;

import android.content.Context;
import android.view.View;

import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;

/**
 * Created by LaudOak on 2015-11-30 at 23:13.
 */
public abstract class AbViewHolder
{

    protected Context context;

    public AbViewHolder(Context context ,View view)
    {
        this.context = context;
    }
    public abstract void bind(AbFilling filling);
}
