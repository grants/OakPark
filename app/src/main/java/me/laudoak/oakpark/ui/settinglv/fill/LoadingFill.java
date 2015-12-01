package me.laudoak.oakpark.ui.settinglv.fill;

import me.laudoak.oakpark.ui.settinglv.ViewHolderPool;

/**
 * Created by LaudOak on 2015-11-30 at 23:38.
 */
public class LoadingFill extends AbFilling {

    private boolean isLoading;

    public LoadingFill()
    {
        this.viewYype = ViewHolderPool.TYPE_ISTV_WITH_LOAD;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

}
