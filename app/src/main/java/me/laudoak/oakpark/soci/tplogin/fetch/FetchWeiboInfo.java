package me.laudoak.oakpark.soci.tplogin.fetch;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LaudOak on 2015-11-25 at 22:36.
 */
public class FetchWeiboInfo extends AbFetcher {

    private static final String TAG = "FetchWeiboInfo";

    private static final String FETCH_URL = "https://api.weibo.com/2/users/show.json";

    /*必选	类型及范围	说明
    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
    uid	false	int64	需要查询的用户ID。
    screen_name	false	string	需要查询的用户昵称。*/

    public FetchWeiboInfo(Context context, JSONObject authoinfo, XBaseFetcher.FetchCallback callback) {
        super(context, authoinfo, callback);
    }

    @Override
    public void onFetch()
    {

    }
}
