package me.laudoak.oakpark.soci.tplogin.fetch;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LaudOak on 2015-11-25 at 22:36.
 */
public class FetchWeiboInfo extends AbFetcher {

    private static final String TAG = "FetchWeiboInfo";

    private static final String FETCH_URL = "https://api.weibo.com/2/users/show.json";

    private static final String KEY_NICK = "name";
    private static final String KEY_FIGURE = "avatar_large";


    //    {"weibo":{"access_token":"2.00r8AqbFlL3BhB070106b978pSNS7C","expires_in":1606194964334,"uid":"5139783375"}}

    /*必选	类型及范围	说明
    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
    uid	false	int64	需要查询的用户ID。
    screen_name	false	string	需要查询的用户昵称。*/

    public FetchWeiboInfo(Context context, JSONObject authoinfo, XBaseFetcher.FetchCallback callback) {
        super(context, authoinfo, callback);
    }
    /**a useful example*/
//    https://api.weibo.com/2/users/show.json?uid=5139783375&source=1551838905&access_token=2.00r8AqbFlL3BhB070106b978pSNS7C
    @Override
    public void onFetch()
    {

    }

    /*{
        "id": 1404376560,
            "screen_name": "zaku",
            "name": "zaku",
            "province": "11",
            "city": "5",
            "location": "北京 朝阳区",
            "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。",
            "url": "http://blog.sina.com.cn/zaku",
            "profile_image_url": "http://tp1.sinaimg.cn/1404376560/50/0/1",
            "domain": "zaku",
            "gender": "m",
            "followers_count": 1204,
            "friends_count": 447,
            "statuses_count": 2908,
            "favourites_count": 0,
            "created_at": "Fri Aug 28 00:00:00 +0800 2009",
            "following": false,
            "allow_all_act_msg": false,
            "geo_enabled": true,
            "verified": false,
            "status": {
        "created_at": "Tue May 24 18:04:53 +0800 2011",
                "id": 11142488790,
                "text": "我的相机到了。",
                "source": "<a href="http://weibo.com" rel="nofollow">新浪微博</a>",
        "favorited": false,
                "truncated": false,
                "in_reply_to_status_id": "",
                "in_reply_to_user_id": "",
                "in_reply_to_screen_name": "",
                "geo": null,
                "mid": "5610221544300749636",
                "annotations": [],
        "reposts_count": 5,
                "comments_count": 8
    },
        "allow_all_comment": true,
            "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
            "verified_reason": "",
            "follow_me": false,
            "online_status": 0,
            "bi_followers_count": 215
    }*/

}
