package me.laudoak.oakpark.sosh.tplogin;

import android.content.Context;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.OP;

/**
 * Created by LaudOak on 2015-11-2 at 13:14.
 */
public class WBLogin extends XBaseLogin implements WeiboAuthListener {


    private AuthInfo authInfo;


    public WBLogin(Context ctxt, TPLoginCallback cb) {
        super(ctxt, cb);

        authInfo = new AuthInfo(ctxt, OP.WEIBO_APP_KEY, OP.WEIBO_REDIRECT_URL, OP.WEIBO_SCOPE);
    }

    @Override
    public void onComplete(Bundle bundle) {

        Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (accessToken != null && accessToken.isSessionValid())
        {
            String token = accessToken.getToken();
            String expires = String.valueOf(accessToken.getExpiresTime());
            String uid = accessToken.getUid();
            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO,token, expires,uid);

            loginWithAuth(authInfo);
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        callback.onFailure(e.toString());
    }

    @Override
    public void onCancel() {
        callback.onCancel("微博登陆已取消");
    }
}
