package me.laudoak.oakpark.ui.settinglv.fill;

/**
 * Created by LaudOak on 2015-11-30 at 23:36.
 */
public abstract class AbFilling {

    protected int viewYype;
    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewYype() {
        return viewYype;
    }

    public void setViewYype(int viewYype) {
        this.viewYype = viewYype;
    }
}
