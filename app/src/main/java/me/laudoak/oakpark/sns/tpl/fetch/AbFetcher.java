package me.laudoak.oakpark.sns.tpl.fetch;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-11-25 at 22:39.
 */
abstract class AbFetcher {

    private static final String TAG = "AbFetcher";

    protected Context context;
    protected JSONObject auth;
    protected XBaseFetcher.FetchCallback callback;


    public AbFetcher(Context context,JSONObject authoinfo, XBaseFetcher.FetchCallback callback)
    {
        this.context = context;
        this.auth = authoinfo;
        this.callback = callback;
    }

    protected void reUpdateInfo(final String nick, final String figure)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                UserProxy.qqUpdate(context, nick, figure, new UserProxy.CallBack() {
                    @Override
                    public void onSuccess(String nick)
                    {
                        callback.onFetchSuccess("Hello. "+nick);
                    }

                    @Override
                    public void onFailure(String reason) {
                        callback.onFetchFailure(reason);
                    }
                });

                return null;
            }

        }.execute();
    }

    abstract void onFetch();
}
