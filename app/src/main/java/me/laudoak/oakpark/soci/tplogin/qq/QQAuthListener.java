package me.laudoak.oakpark.soci.tplogin.qq;

import android.content.Context;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.soci.tplogin.XBaseAuth;

/**
 * Created by LaudOak on 2015-11-3 at 19:43.
 */
public class QQAuthListener extends XBaseAuth implements
        IUiListener{

    private static final String TAG = "QQAuthListener";

//    {"qq":{"access_token":"EB00E6E39DE1E322FD6F874CF7E6F241","expires_in":7776000,"openid":"3E406204BB5BA421D530D8E55AEEAFE3"}}

    public QQAuthListener(Context ctxt, AuthCallback cb) {
        super(ctxt, cb);
    }

    @Override
    public void onComplete(Object o) {
        if(o!=null){

            JSONObject jsonObject = (JSONObject) o;
            try {

                String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);

                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);

                loginWithAuth(authInfo);

            } catch (JSONException e) {
                callback.onXBFailure(e.toString());
            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        Log.d(TAG, "QQ auth error:" + uiError.errorCode + "-" + uiError.errorDetail);
        callback.onXBFailure(uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "QQ authorized is cancelled");
        callback.onXBCancel("QQ授权被取消");
    }

}
