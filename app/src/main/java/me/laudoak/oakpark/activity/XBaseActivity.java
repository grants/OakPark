package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;

/**
 * Created by LaudOak on 2015-9-27.
 */

public abstract class XBaseActivity extends AppCompatActivity {

    private static final String TAG = "XBaseActivity";

    private static final String APPID = "49ef0b98a0a390a69f457dcd31051cf6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(XBaseActivity.this,APPID);

        setContentView(callContentView());
        buildView();
    }

    public abstract int callContentView();
    public abstract void buildView();
}
