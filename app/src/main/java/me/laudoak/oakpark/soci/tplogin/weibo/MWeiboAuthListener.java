package me.laudoak.oakpark.soci.tplogin.weibo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;

import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.soci.tplogin.XBaseAuth;

/**
 * Created by LaudOak on 2015-11-25 at 22:11.
 */
public class MWeiboAuthListener extends XBaseAuth implements
        com.sina.weibo.sdk.auth.WeiboAuthListener{

    private static final String TAG = "MWeiboAuthListener";

//    {"weibo":{"access_token":"2.00r8AqbFlL3BhB070106b978pSNS7C","expires_in":1606194964334,"uid":"5139783375"}}

    public MWeiboAuthListener(Context ctxt, AuthCallback cb)
    {
        super(ctxt, cb);
    }

    /**Weibo callback*/
    @Override
    public void onComplete(Bundle bundle)
    {

        Log.d(TAG,"onComplete"+bundle.toString());
        Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);

        if (accessToken.isSessionValid())
        {
            Log.d(TAG,"accessToken.isSessionValid()");
            String token = accessToken.getToken();
            String expires = String.valueOf(accessToken.getExpiresTime());
            String uid = accessToken.getUid();
            Log.d(TAG,"token:"+token+"  expires:"+expires+"   uid"+uid);

            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO,token, expires,uid);

            loginWithAuth(authInfo);
        }
    }

    @Override
    public void onWeiboException(WeiboException e)
    {
        Log.d(TAG,e.getMessage());
        callback.onXBFailure(e.getMessage());
    }

    @Override
    public void onCancel()
    {
        Log.d(TAG, "Weibo authorized is cancelled");
        callback.onXBCancel("微博授权被取消");
    }
    /**end of Weibo callback*/
}
