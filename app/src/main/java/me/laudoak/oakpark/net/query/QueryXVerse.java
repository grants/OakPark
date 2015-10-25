package me.laudoak.oakpark.net.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-20 at 23:05.
 */
public class QueryXVerse {

    private static final String TAG = "QueryXVerse";

    private static final String QK_POET = "bulbul";
    private static final String QK_DATECODE = "DateCode";

    private Context context;

    public QueryXVerse(final Context context,final QueryCallback callback)
    {
        this.context = context;

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {

                BmobQuery<XVerse> query = new BmobQuery<XVerse>();
                query.order("-"+QK_DATECODE);
                query.include(QK_POET);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);

                query.findObjects(context, new FindListener<XVerse>() {
                    @Override
                    public void onSuccess(List<XVerse> list) {
                        Log.d(TAG,"onSuccess"+"list length"+list.size());
                        callback.onSuccess(list);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG,"onError"+s);
                        callback.onFailure(s);
                    }
                });

                return null;
            }
        }.execute();

    }

    public interface QueryCallback
    {
        void onFailure(String why);
        void onSuccess(List<XVerse> results);
    }

}
