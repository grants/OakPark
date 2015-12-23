package me.laudoak.oakpark.net.bmob.update;

import android.content.Context;

import me.laudoak.oakpark.ctrl.bulbul.UpdateCallback;

/**
 * Created by LaudOak on 2015-12-23 at 15:12.
 */
public abstract class AbUpdate
{

    protected Context context;

    public AbUpdate(Context context)
    {
        this.context = context;
    }

    public abstract void update(UpdateCallback callback );

    public interface UpdateCallback
    {
        void onSuccess();
        void onFailure(String why);
    }
}
