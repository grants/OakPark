package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-10-20 at 17:42.
 */
public class SettingActivity extends XBaseActivity {

    private LinearLayout account,cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.openActivityDurationTrack(false);
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

    @Override
    protected void setView()
    {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void buildView()
    {
        buildBar();
        initViews();
    }

    private void buildBar() {

        getSupportActionBar().hide();

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        findViewById(R.id.ca_normal_done).setVisibility(View.GONE);
    }

    private void initViews() {
        account = (LinearLayout) findViewById(R.id.set_account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserProxy.ifLogin(SettingActivity.this))
                {
                    startActivity(new Intent(SettingActivity.this,BulbulActivity.class));
                }else {
                    AppMsg.makeText(SettingActivity.this,"未登录",AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }
}
