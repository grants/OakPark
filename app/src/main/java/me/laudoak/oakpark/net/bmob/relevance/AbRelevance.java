package me.laudoak.oakpark.net.bmob.relevance;

import android.content.Context;

/**
 * Created by LaudOak on 2015-12-31 at 23:25.
 */
public class AbRelevance
{
    protected Context context;
    RelevanceCallBack callBack;

    public AbRelevance(Context context , RelevanceCallBack callBack)
    {
        this.context = context;
        this.callBack = callBack;
    }

    public interface RelevanceCallBack
    {
        void onAssociateSucess();
        void onAssociateFailure(String why);
    }
}
