package me.laudoak.oakpark.activity;

import android.os.Bundle;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-11-14 at 14:25.
 */
public class OuterActivity extends XBaseActivity {

    private static final String TAG = "OuterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);
    }

}
