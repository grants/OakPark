package me.laudoak.oakpark.sosh.tplogin;

import android.app.Activity;
import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.OP;

/**
 * Created by LaudOak on 2015-10-31 at 22:56.
 */
public class QQLogin extends XBaseLogin implements IUiListener{

    private static final String TAG = "QQLogin";

    private static final String QK_ALL = "all";

    private Activity activity;

    private Tencent tencent;

    public QQLogin(Activity aty,Context context,TPLoginCallback cb)
    {
        super(context,cb);
        this.activity = aty;

        if (null == tencent)
        {
            tencent = Tencent.createInstance(OP.QQ_APP_ID,activity);
        }

        tencent.login(activity,QK_ALL,this);

    }

    @Override
    public void onComplete(Object object) {
        if(object != null){
            JSONObject jsonObject = (JSONObject) object;
            try {

                String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
                loginWithAuth(authInfo);

            } catch (JSONException e)
            {

            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        callback.onFailure(uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        callback.onCancel("QQ授权登陆取消");
    }

}
