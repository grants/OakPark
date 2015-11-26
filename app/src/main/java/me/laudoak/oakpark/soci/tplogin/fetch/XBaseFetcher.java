package me.laudoak.oakpark.soci.tplogin.fetch;


import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LaudOak on 2015-11-25 at 22:32.
 */
public class XBaseFetcher {

    private static final String TAG = "XBaseFetcher";

    public XBaseFetcher(String who,Context context,JSONObject authoinfo, FetchCallback callback)
    {
        if (who.equals("qq"))
        {
            new FetchQQInfo(context,authoinfo,callback).onFetch();

        }else if (who.equals("weibo"))
        {
            new FetchWeiboInfo(context,authoinfo,callback).onFetch();
        }
    }

    public interface FetchCallback
    {
        void onFetchSuccess(String desc);
        void onFetchFailure(String why);
    }

}
