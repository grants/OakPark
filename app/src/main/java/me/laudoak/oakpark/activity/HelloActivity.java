package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.net.raw.SipGetter;
import me.laudoak.oakpark.utils.font.FontsManager;

/**
 * Created by LaudOak on 2015-11-4 at 22:36.
 */
public class HelloActivity extends XBaseActivity {

    private static final String TAG = "HelloActivity";

    private View hello;
    private TextView sipTv;
    private TextView version;
    private TextView oakpark;


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_hello);
    }

    @Override
    public void buildView() {

        FontsManager.initFormAssets(this, "fonts/fang.TTF");
        hello = findViewById(R.id.view_welcome);
        FontsManager.changeFonts(hello);

        sipTv = (TextView) findViewById(R.id.view_welcome_sip);
        version = (TextView) findViewById(R.id.hello_app_version);
        if (null != getVersion())
        {
            version.setText(getVersion());
        }
        oakpark = (TextView) findViewById(R.id.hello_app_name);
        FontsManager.changeFonts(oakpark);


        new SipGetter(this, new SipGetter.CallBack() {
            @Override
            public void onGet(String sip) {
                sipTv.setText(sip);
            }
        });


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelloActivity.this,OakParkActivity.class));
                HelloActivity.this.finish();
            }
        };

        handler.postDelayed(runnable, 5000);

    }

    private String getVersion()
    {
        PackageManager packageManager = getPackageManager();

        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            String versionName = packageInfo.versionName;

            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }



}
