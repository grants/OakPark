package me.laudoak.oakpark.net.bmob.delete;

import android.content.Context;

/**
 * Created by LaudOak on 2016-1-16 at 10:57.
 */
public class AbDelete
{

    protected Context context;

    public AbDelete(Context context)
    {
        this.context = context;
    }

    public interface DeleteCallback
    {
        void onDeleteSuccess();
        void onDeleteFailure(String why);
    }

}
