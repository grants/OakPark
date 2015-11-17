package me.laudoak.oakpark.net.raw;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import me.laudoak.oakpark.activity.XBaseActivity;
import me.laudoak.oakpark.net.Constants;
import me.laudoak.oakpark.utils.TimeUtil;

/**
 * Created by LaudOak on 2015-11-4 at 22:50.
 */
public class SipGetter {

    private static final String TAG = "SipGetter";

    private static final String SHR_KEY_DATECODE = "_datecode";
    private static final String SHR_KEY_SIP = "_sip";

    private static final String KEY_CONTEXT = "content";

    public SipGetter(XBaseActivity activity, final CallBack callback)
    {
        final SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);

        String code = preferences.getString(SHR_KEY_DATECODE, null);
        final String sip = preferences.getString(SHR_KEY_SIP, null);
        final String dateCode = TimeUtil.getTodayDateCode();

        if (null != code && code.equals(dateCode))
        {
            callback.onGet(sip);
        }else
        {
            OkHttpClientManager.getAsyn(Constants.END_POT_SIP + dateCode, new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {

                    if (null != sip)
                    {
                        callback.onGet(sip);
                    }
                }

                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String sipContent = jsonObject.getString(KEY_CONTEXT);
                        callback.onGet(sipContent);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SHR_KEY_DATECODE,dateCode);
                        editor.putString(SHR_KEY_SIP,sipContent);
                        editor.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public interface CallBack
    {
        void onGet(String sip);
    }

}
