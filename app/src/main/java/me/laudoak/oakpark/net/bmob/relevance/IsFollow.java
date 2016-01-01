package me.laudoak.oakpark.net.bmob.relevance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.view.FollowButtonView;

/**
 * Created by LaudOak on 2016-1-1 at 17:19.
 */
public class IsFollow extends AsyncTask<Void,Void,Void>
{

    private static final String TAG = IsFollow.class.getName();

    private Context context;
    private Poet secondaryPoet;
    private FollowButtonView followButtonView;

    public IsFollow(Context context , Poet secondaryPoet , FollowButtonView followButtonView)
    {
        Log.d(TAG,"IsFollow(Context context , Poet secondaryPoet , FollowButtonView followButtonView)");

        this.context = context;
        this.secondaryPoet = secondaryPoet;
        this.followButtonView = followButtonView;

        followButtonView.loading();
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        Log.d(TAG,"doInBackground(Void... voids)");

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

                    String id = secondaryPoet.getObjectId();
                    boolean isFollow = false;

                    for (int i = 0; i < list.size(); i++)
                    {
                        if (list.get(i).getObjectId().equals(id))
                        {
                            isFollow = true;
                            break;
                        }
                    }

                    Log.d(TAG,"isFollow"+isFollow);
                    if (isFollow)
                    {
                        followButtonView.followState();
                    }else
                    {
                        followButtonView.unFollowSatate();
                    }

                }

                @Override
                public void onError(int i, String s)
                {
                    Log.d(TAG,"onError(int i, String s):"+s);

                    followButtonView.unFollowSatate();
                }
            });
        }

        return null;
    }

}
