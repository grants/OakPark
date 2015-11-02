package me.laudoak.oakpark.sosh.tplogin;

import android.content.Context;

import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;

/**
 * Created by LaudOak on 2015-11-2 at 13:17.
 */
public class XBaseLogin {

    private static final String TAG = "XBaseLogin";

    private Context context;

    protected TPLoginCallback callback;

    public XBaseLogin(Context ctxt , TPLoginCallback cb)
    {
        this.context = ctxt;
        this.callback = cb;
    }

    protected void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(context, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
               callback.onFailure(msg);
            }

        });
    }

    public interface TPLoginCallback
    {
        void onSuccess();
        void onFailure(String why);
        void onCancel(String who);
    }

}
