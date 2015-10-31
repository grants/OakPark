package me.laudoak.oakpark.net.query;

import android.content.Context;
import android.os.AsyncTask;

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

    public QueryComment(final Context ctxt, final XVerse xVerse, final QueryCallback callback)
    {
        this.context = ctxt;

        new AsyncTask<Void , Void, Void>()
        {

            @Override
            protected Void doInBackground(Void... params) {

                BmobQuery<Comment> query = new BmobQuery<Comment>();
                query.addWhereEqualTo(QK_XVERSE, new BmobPointer(xVerse));
                query.include(QK_POET);
                query.order(QK_ORDER);
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);

                query.findObjects(context, new FindListener<Comment>() {
                    @Override
                    public void onSuccess(List<Comment> list) {
                        callback.onSuccess(list);
                    }

                    @Override
                    public void onError(int i, String s) {
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
        void onSuccess(List<Comment> results);
    }

}
