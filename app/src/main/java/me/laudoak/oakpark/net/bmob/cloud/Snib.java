package me.laudoak.oakpark.net.bmob.cloud;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * Created by LaudOak on 2016-1-16 at 16:07.
 */
public class Snib
{

    private static final String TAG = Snib.class.getSimpleName();

    private Context context;

    AsyncCustomEndpoints ace;


    public Snib(Context context)
    {
        this.context = context;
    }

    public void querySnib(String uid, final SnibCallback callback)
    {
        ace = new AsyncCustomEndpoints();
        JSONObject object = new JSONObject();
        try
        {
            object.put("uid", uid);

            ace.callEndpoint(context, "Snib", object, new CloudCodeListener()
            {
                @Override
                public void onSuccess(Object o)
                {
                    Log.d(TAG, "Object o" + o.toString());
                    if (o.toString().equals("true"))
                    {
                        callback.pass();
                    }else
                    {
                        callback.block();
                    }
                }

                @Override
                public void onFailure(int i, String s)
                {
                    callback.onFailure(s);
                }
            });

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public interface SnibCallback
    {
        void onFailure(String why);
        void pass();
        void block();
    }
}
