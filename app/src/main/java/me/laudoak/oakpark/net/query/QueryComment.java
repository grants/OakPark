package me.laudoak.oakpark.net.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-24 at 16:31.
 */
public class QueryComment {

    private static final String TAG = "QueryComment";

    private Context context;

    private static final String QK_POET = "poet";
    private static final String QK_XVERSE = "xVerse";
    private static final String QK_ORDER = "-createdAt";
    private static final int QK_LIMIT = 6;

    public QueryComment(final Context context, final XVerse xVerse,int page, final QueryCallback callback)
    {
        this.context = context;

        new AsyncTask<Integer , Void, Void>()
        {
            @Override
            protected Void doInBackground(Integer... params) {

                int page = params[0];

                BmobQuery<Comment> query = new BmobQuery<Comment>();
                query.addWhereEqualTo(QK_XVERSE,new BmobPointer(xVerse));
                query.include(QK_POET);
                query.order(QK_ORDER);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.setLimit(QK_LIMIT);
                query.setSkip(QK_LIMIT * page);

                query.findObjects(context, new FindListener<Comment>() {
                    @Override
                    public void onSuccess(List<Comment> list) {
                        if (list.size()==QK_LIMIT)
                        {
                            callback.onSuccess(true,list);
                        }else if (list.size()==0||list.size()<QK_LIMIT){
                            callback.onSuccess(false,list);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError");
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
        void onSuccess(boolean hasMore,List<Comment> results);
    }

}
