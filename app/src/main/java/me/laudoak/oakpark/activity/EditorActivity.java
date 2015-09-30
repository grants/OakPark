package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.EntireEditorFragment;
import me.laudoak.oakpark.fragment.NormalEditorFragment;
import me.laudoak.oakpark.fragment.XBaseFragment;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EditorActivity extends XBaseActivity {

    private static final String TAG = "EditorActivity";

    public static final String EXTRA_FRAGMENT_FLAG = "EXTRA_FRAGMENT_FLAG";

    private XBaseFragment fragment;

    private int flag;

    private PushCallback pushCallback;

    @Override
    public int callContentView() {
        return R.layout.activity_editor;
    }

    @Override
    public void buildView() {

        Intent intent = getIntent();
        if (null!=intent)
        {
            flag = intent.getIntExtra(EXTRA_FRAGMENT_FLAG,-1);

            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (flag)
            {
                case 0:
                {
                    this.pushCallback = (PushCallback) fragment;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuBuilder = getMenuInflater();
        menuBuilder.inflate(R.menu.menu_editor,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.editor_push:
            {
                if(null!=fragment&&null!=pushCallback)
                {
                    pushCallback.onPush();
                }
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public interface PushCallback
    {
        void onPush();
    }

}
