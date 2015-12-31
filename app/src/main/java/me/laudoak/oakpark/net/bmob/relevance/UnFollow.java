package me.laudoak.oakpark.net.bmob.relevance;

import android.content.Context;

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

    public void unFollow(Poet secondaryPoet)
    {
        Poet currentPoet = UserProxy.currentPoet(context);

        if (null != currentPoet)
        {
            BmobRelation relation = new BmobRelation();
            relation.remove(secondaryPoet);

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
    }
}
