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
public class FetchQQInfo extends AbFetcher {

    private static final String TAG = "FetchQQInfo";

//    http://wiki.open.qq.com/wiki/website/get_user_info
//    {"qq":{"access_token":"EB00E6E39DE1E322FD6F874CF7E6F241","expires_in":7776000,"openid":"3E406204BB5BA421D530D8E55AEEAFE3"}}


    private static final String FETCH_URL = "https://graph.qq.com/user/get_user_info";

    private static final String PAR_ACC_TOKEN = "access_token";
    private static final String PAR_OPEN_ID = "openid";
    private static final String PAR_OAU_CONS_KEY = "oauth_consumer_key";//use qq appid


    private static final String KEY_RET = "ret";
    private static final String KEY_MSG = "msg";
    private static final String KEY_NICK = "nickname";
    private static final String KEY_FIGURE = "figureurl_qq_2";


    private String _access_token;
    private String _openid;


    public FetchQQInfo(Context context, JSONObject authoinfo, XBaseFetcher.FetchCallback callback)
    {
        super(context, authoinfo, callback);

        try {

            JSONObject qqAuthInfo = authoinfo.getJSONObject("qq");

            _access_token = qqAuthInfo.getString("access_token");
            _openid = qqAuthInfo.getString("openid");

        } catch (JSONException e)
        {
            callback.onFetchFailure(e.getMessage());
            e.printStackTrace();
        }

    }


    /*QQInfo返回参数说明
    ret	返回码
    msg	如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
    nickname	用户在QQ空间的昵称。
    figureurl	大小为30×30像素的QQ空间头像URL。
    figureurl_1	大小为50×50像素的QQ空间头像URL。
    figureurl_2	大小为100×100像素的QQ空间头像URL。
    figureurl_qq_1	大小为40×40像素的QQ头像URL。
    figureurl_qq_2	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
    gender	性别。 如果获取不到则默认返回"男"
    is_yellow_vip	标识用户是否为黄钻用户（0：不是；1：是）。
    vip	标识用户是否为黄钻用户（0：不是；1：是）
    yellow_vip_level	黄钻等级
    level	黄钻等级
    is_yellow_year_vip	标识是否为年费黄钻用户（0：不是； 1：是）*/

    /*QQInfo正确返回示例
    JSON示例:
    Content-type: text/html; charset=utf-8
    {
        "ret":0,
            "msg":"",
            "nickname":"Peter",
            "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
            "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
            "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
            "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
            "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
            "gender":"男",
            "is_yellow_vip":"1",
            "vip":"1",
            "yellow_vip_level":"7",
            "level":"7",
            "is_yellow_year_vip":"1"
    }
*/


//    参数	含义
//    access_token	可通过使用Authorization_Code获取Access_Token 或来获取。
//    access_token有3个月有效期。
//    oauth_consumer_key	申请QQ登录成功后，分配给应用的appid
//    openid	用户的ID，与QQ号码一一对应。
//    可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。

/**useful example*/
//    https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
//    https://graph.qq.com/user/get_user_info?access_token=EB00E6E39DE1E322FD6F874CF7E6F241&oauth_consumer_key=1104939996&openid=3E406204BB5BA421D530D8E55AEEAFE3
//    describe:
//----------------------------
//    access_token:YOUR_ACCESS_TOKEN
//    oauth_consumer_key:YOUR_APP_ID
//    openid:YOUR_OPENID
    @Override
    public void onFetch()
    {
        StringBuilder desUrlBuilder = new StringBuilder();
        desUrlBuilder
                .append(FETCH_URL)
                .append("?")

                //access_token
                .append(PAR_ACC_TOKEN)
                .append("=")
                .append(_access_token)

                //oauth_consumer_key
                .append("&")
                .append(PAR_OAU_CONS_KEY)
                .append("=")
                .append(OP.QQ_APP_ID)

                //openid
                .append("&")
                .append(PAR_OPEN_ID)
                .append("=")
                .append(_openid);

        String destUrl = desUrlBuilder.toString();

        OkHttpClientManager.getAsyn(destUrl,
                new OkHttpClientManager.ResultCallback<String>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        callback.onFetchFailure(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        try {

                            Log.d(TAG,"onResponse(String response)"+response);

                            JSONObject object = new JSONObject(response);

                            switch (object.getInt(KEY_RET))
                            {
                                case 0:
                                {
                                    String tail = StringUtil.genNickTail(_openid);
                                    String qqNick = object.getString(KEY_NICK) + tail;
                                    String qqFigure = object.getString(KEY_FIGURE);

                                    reUpdateInfo(qqNick,qqFigure);
                                }
                                default:
                                {
                                    String errmsg = object.getString(KEY_MSG);
                                    callback.onFetchFailure(errmsg);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFetchFailure(e.getMessage());
                        }
                    }
                });

    }


}
