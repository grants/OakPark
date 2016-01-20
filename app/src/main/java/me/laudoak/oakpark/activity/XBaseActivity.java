package me.laudoak.oakpark.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import me.laudoak.oakpark.OP;


/**
 * Created by LaudOak on 2015-9-27.
 */

public abstract class XBaseActivity extends AppCompatActivity {

    private static final String TAG = "XBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Bmob initialize*/
        Bmob.initialize(XBaseActivity.this, OP.BMOB_APP_ID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
