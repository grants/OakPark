package me.laudoak.oakpark.ui.settinglv.fill;

import me.laudoak.oakpark.ui.settinglv.ViewHolderPool;

/**
 * Created by LaudOak on 2015-11-30 at 23:37.
 */
public class NormalFill extends AbFilling {

    public NormalFill(String title)
    {
        viewYype = ViewHolderPool.TYPE_ISTV_NORMAL;
        this.title = title;
    }
}
