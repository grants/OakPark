package me.laudoak.oakpark.net.bmob.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.Poet;
import me.laudoak.oakpark.entity.Verse;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-10-8 at 21:45.
 */
public class QueryVerse {

    private static final String TAG = "QueryVerse";

    private static final String QK_POET = "poet";
    private static final String QK_ORDER = "-createdAt";
    private static final int QK_LIMIT = 10;


    public QueryVerse(final Context context,int page, final QueryCallback callback)
    {
        Log.d(TAG,"QueryVerse constructor"+";page:"+page);

        new AsyncTask<Integer,Void,List<Verse>>()
        {

            @Override
            protected List<Verse> doInBackground(Integer... params) {

                Log.d(TAG,"doInBackground");

                int page = params[0];

                BmobQuery<Verse> query = new BmobQuery<Verse>();
                Poet poet = UserProxy.currentPoet(context);
                query.addWhereEqualTo(QK_POET,new BmobPointer(poet));
                query.order(QK_ORDER);
                query.include(QK_POET);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.setLimit(QK_LIMIT);
                query.setSkip(QK_LIMIT * page);

                query.findObjects(context, new FindListener<Verse>() {
                    @Override
                    public void onSuccess(List<Verse> list) {

                        Log.d(TAG,"onSuccess");

                        if (list.size()==QK_LIMIT)
                        {
                            callback.onSuccess(true,list);
                        }else if (list.size()==0||list.size()<QK_LIMIT){
                            callback.onSuccess(false,list);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG,"onError");
                        callback.onFailure(s);
                    }
                });
                return null;
            }

        }.execute(page);
    }

    public interface QueryCallback
    {
        void onFailure(String why);
        void onSuccess(boolean hasMore,List<Verse> results);
    }

}
