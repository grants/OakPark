package me.laudoak.oakpark.net.bmob.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;

/**
 * Created by LaudOak on 2015-12-6 at 22:27.
 */
public class QueryOuterVerse
{
    private static final String TAG = QueryOuterVerse.class.getName();

    private static final String QK_POET = "poet";
    private static final String QK_ORDER = "-createdAt";


    public QueryOuterVerse(final Context context, final Poet poet, final QueryCallback callback)
    {

        new AsyncTask<Void,Void,List<Verse>>()
        {

            @Override
            protected List<Verse> doInBackground(Void... params)
            {

                Log.d(TAG,"doInBackground");

                BmobQuery<Verse> query = new BmobQuery<Verse>();
                query.addWhereEqualTo(QK_POET, new BmobPointer(poet));
                query.order(QK_ORDER);
                query.include(QK_POET);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);

                query.findObjects(context, new FindListener<Verse>()
                {
                    @Override
                    public void onSuccess(List<Verse> list)
                    {
                        Log.d(TAG,"onSuccess");
                        callback.onSuccess(list);
                    }

                    @Override
                    public void onError(int i, String s)
                    {
                        Log.d(TAG,"onError");
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
        void onSuccess(List<Verse> results);
    }

}
