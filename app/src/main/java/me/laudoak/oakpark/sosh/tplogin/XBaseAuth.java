package me.laudoak.oakpark.sosh.tplogin;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;

/**
 * Created by LaudOak on 2015-11-3 at 19:45.
 */
public class XBaseAuth {

    private static final String TAG = "XBaseAuth";

    protected Context context;

    protected AuthCallback callback;

    public XBaseAuth(Context ctxt,AuthCallback cb)
    {
        this.context = ctxt;
        this.callback = cb;
    }


    protected void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){

        BmobUser.loginWithAuthData(context, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                Log.d(TAG, authInfo.getSnsType() + "Third party loginsuccess:(UserAuth Info)" + userAuth);
                callback.onSuccess("第三方登录成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d(TAG, "Third party login failure:"+msg);
                callback.onFailure("第三方登录失败"+msg);
            }

        });
    }

    public interface AuthCallback
    {
        void onSuccess(String desc);
        void onFailure(String why);
        void onCancel(String desc);
    }

}
