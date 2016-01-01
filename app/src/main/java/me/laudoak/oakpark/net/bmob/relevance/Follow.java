package me.laudoak.oakpark.net.bmob.relevance;

import android.content.Context;
import android.os.AsyncTask;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-12-31 at 23:27.
 */
public class Follow extends AbRelevance
{

    public Follow(Context context, RelevanceCallBack callBack)
    {
        super(context, callBack);
    }

    public void follow(final Poet secondaryPoet)
    {

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {

                Poet currentPoet = UserProxy.currentPoet(context);

                if (null != currentPoet)
                {
                    BmobRelation relation = new BmobRelation();
                    relation.add(secondaryPoet);

                    currentPoet.setFollow(relation);

                    currentPoet.update(context, currentPoet.getObjectId(), new UpdateListener()
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
