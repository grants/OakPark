package me.laudoak.oakpark.net.bmob.push;

import android.content.Context;

/**
 * Created by LaudOak on 2015-10-2.
 */
public abstract class XBasePush {

    Context context;
    String title;
    String author;
    String verse;
    String imagePath;
    long pushTime;

    public abstract void push(PushCallBack callBack);

    public interface PushCallBack
    {
        void onSuccess();
        void onFailure(String reason);
    }
}
