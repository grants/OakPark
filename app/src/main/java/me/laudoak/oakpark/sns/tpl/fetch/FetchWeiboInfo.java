package me.laudoak.oakpark.sns.tpl.fetch;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import me.laudoak.oakpark.OP;
import me.laudoak.oakpark.net.raw.OkHttpClientManager;
import me.laudoak.oakpark.utils.StringUtil;

/**
 * Created by LaudOak on 2015-11-25 at 22:36.
 */
public class FetchWeiboInfo extends AbFetcher {

    private static final String TAG = "FetchWeiboInfo";

    private static final String FETCH_URL = "https://api.weibo.com/2/users/show.json";

    /**request params */
    private static final String PAR_UID = "uid";
    private static final String PAR_ACCESS_TOKEN = "access_token";
    private static final String PAR_SOURCE = "source";//App id

    /**needed JSON result key*/
    private static final String KEY_NICK = "name";
    private static final String KEY_FIGURE = "avatar_large";


//    {"weibo":{"access_token":"2.00r8AqbFlL3BhB070106b978pSNS7C","expires_in":1606194964334,"uid":"5139783375"}}

    /*必选	类型及范围	说明
    source	false	string	采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
    access_token	false	string	采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
    uid	false	int64	需要查询的用户ID。
    screen_name	false	string	需要查询的用户昵称。*/

    private String _uid ;
    private String _access_token;

    public FetchWeiboInfo(Context context, JSONObject authoinfo, XBaseFetcher.FetchCallback callback) {
        super(context, authoinfo, callback);

        try {

            JSONObject jsonObject = authoinfo.getJSONObject("weibo");
            _uid = jsonObject.getString("uid");
            _access_token = jsonObject.getString("access_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /**a useful example*/
//    https://api.weibo.com/2/users/show.json?uid=5139783375&source=1551838905&access_token=2.00r8AqbFlL3BhB070106b978pSNS7C
    @Override
    public void onFetch()
    {
        StringBuilder desUrlBuilder = new StringBuilder();
        desUrlBuilder
                .append(FETCH_URL)
                .append("?")

                        //access_token
                .append(PAR_ACCESS_TOKEN)
                .append("=")
                .append(_access_token)

                        //oauth_consumer_key
                .append("&")
                .append(PAR_SOURCE)
                .append("=")
                .append(OP.WEIBO_APP_KEY)

                        //openid
                .append("&")
                .append(PAR_UID)
                .append("=")
                .append(_uid);

        String destUrl = desUrlBuilder.toString();

        OkHttpClientManager.getAsyn(destUrl,
                new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        callback.onFetchFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {

                        try {

                            Log.d(TAG, "onResponse(String response)" + response);

                            JSONObject object = new JSONObject(response);

                            String tail = StringUtil.genNickTail(_access_token);
                            String qqNick = object.getString(KEY_NICK) + tail;
                            String qqFigure = object.getString(KEY_FIGURE);

                            reUpdateInfo(qqNick,qqFigure);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFetchFailure(e.getMessage());
                        }

                    }
                });

    }
}

       /* "id": 5139783375,
            "idstr": "5139783375",
            "class": 1,
            "screen_name": "海听你诗",
            "name": "海听你诗",
            "province": "37",
            "city": "2",
            "location": "山东 青岛",
            "description": "",
            "url": "",
            "profile_image_url": "http://tp4.sinaimg.cn/5139783375/50/5737899982/1",
            "cover_image_phone": "http://ww1.sinaimg.cn/crop.0.0.640.640.640/6cf8d7ebjw1ehfr4xa8psj20hs0hsgpg.jpg",
            "profile_url": "u/5139783375",
            "domain": "",
            "weihao": "",
            "gender": "m",
            "followers_count": 28,
            "friends_count": 52,
            "pagefriends_count": 0,
            "statuses_count": 107,
            "favourites_count": 0,
            "created_at": "Fri May 16 13:34:05 +0800 2014",
            "following": false,
            "allow_all_act_msg": false,
            "geo_enabled": true,
            "verified": false,
            "verified_type": -1,
            "remark": "",
            "status": {
        "created_at": "Tue Sep 22 00:01:40 +0800 2015",
                "id": 3889662630628732,
                "mid": "3889662630628732",
                "idstr": "3889662630628732",
                "text": "分享图片",
                "source_allowclick": 0,
                "source_type": 1,
                "source": "<a href=\"http://app.weibo.com/t/feed/1KucM4\" rel=\"nofollow\">荣耀3C</a>",
                "favorited": false,
                "truncated": false,
                "in_reply_to_status_id": "",
                "in_reply_to_user_id": "",
                "in_reply_to_screen_name": "",
                "pic_urls": [
        {
            "thumbnail_pic": "http://ww4.sinaimg.cn/thumbnail/005BQ0rRjw1ewaiouq9mnj30k00k0dpx.jpg"
        }
        ],
        "thumbnail_pic": "http://ww4.sinaimg.cn/thumbnail/005BQ0rRjw1ewaiouq9mnj30k00k0dpx.jpg",
                "bmiddle_pic": "http://ww4.sinaimg.cn/bmiddle/005BQ0rRjw1ewaiouq9mnj30k00k0dpx.jpg",
                "original_pic": "http://ww4.sinaimg.cn/large/005BQ0rRjw1ewaiouq9mnj30k00k0dpx.jpg",
                "geo": null,
                "annotations": [
        {
            "client_mblogid": "f47d43b8-083c-4be5-af99-c0aa6ff1a1db"
        },
        {
            "mapi_request": true
        }
        ],
        "reposts_count": 0,
                "comments_count": 0,
                "attitudes_count": 0,
                "isLongText": false,
                "mlevel": 0,
                "visible": {
            "type": 0,
                    "list_id": 0
        },
        "biz_feature": 4294967300,
                "darwin_tags": [],
        "userType": 0
    },
        "ptype": 0,
            "allow_all_comment": true,
            "avatar_large": "http://tp4.sinaimg.cn/5139783375/180/5737899982/1",
            "avatar_hd": "http://ww3.sinaimg.cn/crop.0.0.720.720.1024/005BQ0rRjw8ewblwcrosij30k00k0ab7.jpg",
            "verified_reason": "",
            "verified_trade": "",
            "verified_reason_url": "",
            "verified_source": "",
            "verified_source_url": "",
            "follow_me": false,
            "online_status": 0,
            "bi_followers_count": 8,
            "lang": "zh-cn",
            "star": 0,
            "mbtype": 0,
            "mbrank": 0,
            "block_word": 0,
            "block_app": 0,
            "credit_score": 80,
            "user_ability": 0,
            "urank": 14
    }*/

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

