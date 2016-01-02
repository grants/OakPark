package me.laudoak.oakpark.net.bmob.query;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2016-1-2 at 22:20.
 */
public class QueryFriendVerse
{

    private static final String TAG = QueryFriendVerse.class.getName();

    private static final String QK_OBJID = "objectId";
    private static final String QK_POET = "poet";
    private static final String QK_ORDER = "-createdAt";
    private static final int QK_PERPAGE = 15;


    public QueryFriendVerse(final Context context, final QueryFriendCallback callback , final int page)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Log.d(TAG, "doInBackground(Void... voids)");

                final Poet currentPoet = UserProxy.currentPoet(context);

                if (null != currentPoet)
                {
                    BmobQuery<Poet> query = new BmobQuery<Poet>();
                    query.addWhereRelatedTo("follow",new BmobPointer(currentPoet));

                    query.findObjects(context, new FindListener<Poet>()
                    {
                        @Override
                        public void onSuccess(List<Poet> list)
                        {
                            Log.d(TAG,"onSuccess(List<Poet> list)");
                            if (list.size()<=0)
                            {
                                callback.onNoFollow();
                            }else
                            {
                                String[] friendsId = new String[list.size()];
                                for (int i =0 ; i < list.size() ; i++)
                                {
                                    friendsId[i] = list.get(i).getObjectId();
                                }

                                BmobQuery<Poet> innerQuery = new BmobQuery<Poet>();
                                innerQuery.addWhereContainedIn(QK_OBJID, Arrays.asList(friendsId));

                                BmobQuery<Verse> query = new BmobQuery<Verse>();
                                query.addWhereMatchesQuery(QK_POET, "_User", innerQuery);
                                query.setLimit(QK_PERPAGE);
                                query.setSkip(QK_PERPAGE * page);
                                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                                query.order(QK_ORDER);
                                query.include(QK_POET);
                                query.findObjects(context, new FindListener<Verse>()
                                {
                                    @Override
                                    public void onSuccess(List<Verse> list)
                                    {
                                        if (list.size()>=QK_PERPAGE)
                                        {
                                            callback.onSuccess(true,list);
                                        }
                                        else
                                        {
                                            callback.onSuccess(false,list);
                                        }
                                        Log.d(TAG,"onSuccess(List<Verse> list)"+list.size());
                                    }

                                    @Override
                                    public void onError(int i, String s)
                                    {
                                        callback.onFailure(s);
                                        Log.d(TAG,"onError(int i, String s)"+s);
                                    }
                                });

                            }
                        }

                        @Override
                        public void onError(int i, String s)
                        {
                            callback.onFailure(s);
                            Log.d(TAG,"onError(int i, String s):"+s);
                        }
                    });
                }

                return null;
            }
        }.execute();

    }


    public interface QueryFriendCallback
    {
        void onFailure(String why);
        void onNoFollow();
        void onSuccess(boolean hasMore,List<Verse> results);
    }

}
