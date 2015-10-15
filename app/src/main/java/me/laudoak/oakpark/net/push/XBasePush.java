package me.laudoak.oakpark.net.push;

import android.content.Context;

/**
 * Created by LaudOak on 2015-10-2.
 */
public abstract class XBasePush {

    Context context;
    String title;
    String author;
    String verse;
    String whisper;
    String imagePath;
    int dateCode;
    long pushTime;

    public abstract void push(PushCallBack callBack);

    public interface PushCallBack
    {
        void onSuccess();
        void onFailure(String reason);
    }
}
