package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

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

        handler.postDelayed(runnable,5000);


    }


}
