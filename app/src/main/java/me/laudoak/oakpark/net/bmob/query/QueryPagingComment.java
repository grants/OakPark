package me.laudoak.oakpark.net.bmob.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.core.Comment;
import me.laudoak.oakpark.entity.core.XVerse;

/**
 * Created by LaudOak on 2015-12-4 at 23:22.
 */
public class QueryPagingComment
{
    private static final String TAG = QueryPagingComment.class.getName();

    private static final String QK_POET = "poet";
    private static final String QK_XVERSE = "xVerse";
    private static final String QK_ORDER = "-createdAt";
    private static final int QK_LIMIT = 18;


    public QueryPagingComment(final Context context,int page,final XVerse xVerse, final QueryCallback callback)
    {
        new AsyncTask<Integer,Void,Void>()
        {
            @Override
            protected Void doInBackground(Integer... params)
            {

                int currentPage = params[0];

                BmobQuery<Comment> query = new BmobQuery<Comment>();
                query.addWhereEqualTo(QK_XVERSE, new BmobPointer(xVerse));
                query.include(QK_POET);
                query.order(QK_ORDER);
                query.setLimit(QK_LIMIT);
                query.setSkip(QK_LIMIT * currentPage);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);

                query.findObjects(context, new FindListener<Comment>()
                {
                    @Override
                    public void onSuccess(List<Comment> list)
                    {
                        Log.d(TAG,"onSuccess(List<Comment> list):"+list.size());

                        if (list.size()==QK_LIMIT)
                        {
                            callback.onSuccess(true,list);

                        }else if (list.size()==0 || list.size()<QK_LIMIT)
                        {
                            callback.onSuccess(false,list);
                        }

                    }

                    @Override
                    public void onError(int i, String s)
                    {
                        Log.d(TAG,"onError:"+s);
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
