package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.EntireEditorFragment;
import me.laudoak.oakpark.fragment.NormalEditorFragment;
import me.laudoak.oakpark.fragment.XBaseFragment;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EditorActivity extends XBaseActivity{

    private static final String TAG = "EditorActivity";

    public static final String EXTRA_FRAGMENT_FLAG = "EXTRA_FRAGMENT_FLAG";

    private XBaseFragment fragment;

    private TextView push;
    private ImageView close;

    private int flag;

    private PushCallback pushCallback;

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
    protected void setView() {
        setContentView(R.layout.activity_editor);
    }

    @Override
    public void buildView() {

        getSupportActionBar().hide();

        buildBar();

        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (null!=intent)
        {
            flag = intent.getIntExtra(EXTRA_FRAGMENT_FLAG,-1);

            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (flag)
            {
                case 0:
                {
                    //#2015-10-1 bug here
                    fragment = new NormalEditorFragment();
                    break;
                }

                case 1:
                {
                    fragment = new EntireEditorFragment();
                    break;
                }

            }

            if(null!=fragment)
            {
                this.pushCallback = (PushCallback) fragment;
                fragmentManager.beginTransaction().replace(R.id.activity_editor_content,fragment).commit();
            }
        }
    }

    private void buildBar() {
        close = (ImageView) findViewById(R.id.ca_editor_back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditorActivity.this.finish();
            }
        });

        push = (TextView) findViewById(R.id.ca_editor_done);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null!=fragment&&null!=pushCallback)
                {
                    pushCallback.onPush();
                }
            }
        });

    }

    public interface PushCallback
    {
        void onPush();
    }

}
