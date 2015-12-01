package me.laudoak.oakpark.ui.settinglv.fill;

import me.laudoak.oakpark.ui.settinglv.ViewHolderPool;

/**
 * Created by LaudOak on 2015-11-30 at 23:37.
 */
public class ToggleFill extends AbFilling {

    private boolean isSelected;

    public ToggleFill(String title,boolean isSelected)
    {
        viewYype = ViewHolderPool.TYPE_ISTV_WITH_TOGGLE;
        this.title = title;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }


    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
