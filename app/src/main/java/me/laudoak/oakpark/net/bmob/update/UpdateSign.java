package me.laudoak.oakpark.net.bmob.update;

import android.content.Context;

import cn.bmob.v3.listener.UpdateListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;

/**
 * Created by LaudOak on 2015-12-23 at 15:16.
 */
public class UpdateSign extends AbUpdate
{

    private String sign;

    public UpdateSign(Context context,String sign)
    {
        super(context);
        this.sign = sign;
    }

    public UpdateSign(Context context)
    {
        super(context);
    }

    @Override
    public void update(final UpdateCallback callback)
    {
        Poet poet = new Poet();
        Poet currentPoet = UserProxy.currentPoet(context);
        if (null != currentPoet)
        {
            poet.setObjectId(currentPoet.getObjectId());
            poet.setSign(sign);
            poet.update(context, poet.getObjectId(), new UpdateListener()
            {
                @Override
                public void onSuccess()
                {
                    callback.onSuccess();
                }

                @Override
                public void onFailure(int i, String s)
                {
                    callback.onFailure(s);
                }
            });
        }else
        {
            callback.onFailure("用户不存在");
        }
    }
}
