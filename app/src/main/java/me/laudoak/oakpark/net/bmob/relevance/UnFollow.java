package me.laudoak.oakpark.net.bmob.relevance;

import android.content.Context;
import android.os.AsyncTask;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-12-31 at 23:36.
 */
public class UnFollow extends AbRelevance
{
    public UnFollow(Context context, RelevanceCallBack callBack)
    {
        super(context, callBack);
    }

    public void unFollow(final Poet secondaryPoet)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Poet currentPoet = UserProxy.currentPoet(context);

                if (null != currentPoet)
                {
                    Poet poet = new Poet();
                    poet.setObjectId(currentPoet.getObjectId());

                    BmobRelation relation = new BmobRelation();
                    relation.remove(secondaryPoet);

                    poet.setFollow(relation);

                    poet.update(context, poet.getObjectId(), new UpdateListener()
                    {
                        @Override
                        public void onSuccess()
                        {
                            if (null != callBack)
                            {
                                callBack.onAssociateSucess();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s)
                        {
                            if (null != callBack)
                            {
                                callBack.onAssociateFailure(s);
                            }
                        }
                    });
                }
                else
                {
                    callBack.onAssociateFailure("请先登录");
                }

                return null;
            }
        }.execute();

    }
}
