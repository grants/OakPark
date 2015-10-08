package me.laudoak.oakpark;

import android.content.Intent;
import android.view.View;

import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.activity.EnterActivity;
import me.laudoak.oakpark.activity.XBaseActivity;

public class MainActivity extends XBaseActivity {


    @Override
    protected void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void buildView() {

    }

    public void onA(View view)
    {
        startActivity(new Intent(MainActivity.this, EnterActivity.class));
    }

    public void onB(View view)
    {
        Intent intent = new Intent();
        intent.putExtra(EditorActivity.EXTRA_FRAGMENT_FLAG,0);
        intent.setClass(this,EditorActivity.class);
        startActivity(intent);
    }

}
