package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ctrl.SettingLvListener;
import me.laudoak.oakpark.ui.seplv.SeparateListView;
import me.laudoak.oakpark.ui.settinglv.SettingLvAdapter;

/**
 * Created by LaudOak on 2015-10-20 at 17:42.
 */
public class SettingActivity extends XBaseActivity {

    private static final String TAG = "SettingActivity";

    private SettingLvAdapter settingLvAdapter;

    @Bind(R.id.lv_setting) SeparateListView settingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobclickAgent.openActivityDurationTrack(false);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

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

   private void buildView()
    {
        buildBar();

        settingLvAdapter = new SettingLvAdapter(this);
        settingListView.setAdapter(settingLvAdapter);
        settingListView.setOnItemClickListener(new SettingLvListener(this, settingLvAdapter));

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

}
