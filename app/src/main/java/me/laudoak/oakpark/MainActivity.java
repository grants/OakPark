package me.laudoak.oakpark;

import android.content.Intent;
import android.view.View;

import me.laudoak.oakpark.activity.EnterActivity;
import me.laudoak.oakpark.activity.XBaseActivity;

public class MainActivity extends XBaseActivity {

    @Override
    public int callContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void buildView() {

    }

    public void onA(View view)
    {
        startActivity(new Intent(MainActivity.this, EnterActivity.class));
    }

}
