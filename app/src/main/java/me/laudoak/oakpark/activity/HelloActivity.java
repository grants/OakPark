package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    private TextView oakpark;
    private View helloView;
    private TextView sipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        buildView();
    }

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

   private void buildView() {

        FontsManager.initFormAssets(this, "fonts/fang.TTF");
        oakpark = (TextView) findViewById(R.id.hello_app_name);
        FontsManager.changeFonts(oakpark);

        helloView = findViewById(R.id.view_welcome);
        sipText = (TextView) findViewById(R.id.view_welcome_sip);

        FontsManager.changeFonts(helloView);


        final long startTime = System.currentTimeMillis();

        new SipGetter(this, new SipGetter.CallBack() {
            @Override
            public void onGet(String sip) {
                long endTime=System.currentTimeMillis();
                if ((endTime - startTime ) < 1500)
                {
                    if (helloView.getVisibility() == View.GONE)
                    {
                        sipText.setText(sip);
                        helloView.setVisibility(View.VISIBLE);
                    }
                }
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

        handler.postDelayed(runnable, 4900);

    }

}
