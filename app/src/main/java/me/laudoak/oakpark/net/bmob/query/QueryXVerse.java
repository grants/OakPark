package me.laudoak.oakpark.net.bmob.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.utils.TimeUtil;

/**
 * Created by LaudOak on 2015-10-20 at 23:05.
 */
public class QueryXVerse
{

    private static final String TAG = QueryXVerse.class.getSimpleName();

    private static final String QK_POET = "bulbul";
    private static final String QK_DATECODE = "-dateCode";
    private static final int QK_LIMIT = 25;
    private static final String COMP_DATECODE = "dateCode";

    public QueryXVerse(final Context context,final QueryCallback callback)
    {
        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {

                BmobQuery<XVerse> mainQuery = new BmobQuery<XVerse>();
                mainQuery.order(QK_DATECODE);
                mainQuery.include(QK_POET);
                mainQuery.setLimit(QK_LIMIT);
                mainQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);

                BmobQuery<XVerse> query1 = new BmobQuery<XVerse>();
                query1.addWhereLessThanOrEqualTo(COMP_DATECODE, TimeUtil.getTodayDateCodeNumber());

                BmobQuery<XVerse> query2 = new BmobQuery<XVerse>();
                query2.addWhereGreaterThan(COMP_DATECODE, 0);

                List<BmobQuery<XVerse>> queries = new ArrayList<BmobQuery<XVerse>>();
                queries.add(query1);
                queries.add(query2);

                BmobQuery<XVerse> que =  mainQuery.and(queries);

                mainQuery.findObjects(context, new FindListener<XVerse>()
                {
                    @Override
                    public void onSuccess(List<XVerse> list)
                    {
                        Log.d(TAG,"onSuccess"+"list length"+list.size());
                        callback.onSuccess(list);
                    }

                    @Override
                    public void onError(int i, String s)
                    {
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
