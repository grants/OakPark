package me.laudoak.oakpark.ui.settinglv.holder;

import android.view.View;

import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;

/**
 * Created by LaudOak on 2015-11-30 at 23:13.
 */
public abstract class AbViewHolder
{

    public AbViewHolder(View view)
    {
    }
    public abstract void bind(AbFilling filling);
}
