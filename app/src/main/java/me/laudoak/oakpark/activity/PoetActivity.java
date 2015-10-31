package me.laudoak.oakpark.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.PoetFragment;

/**
 * Created by LaudOak on 2015-9-30.
 */
public class PoetActivity extends XBaseActivity {

    private ImageView close;
    private ImageView newVerse;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_poet);
    }

    @Override
    public void buildView() {

        buildBar();
        PoetFragment fragment = PoetFragment.newIastance();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_poet_container,fragment).commit();
    }

    private void buildBar() {

        getSupportActionBar().hide();

        close = (ImageView) findViewById(R.id.ca_poet_back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoetActivity.this.finish();
            }
        });

        newVerse = (ImageView) findViewById(R.id.ca_poet_done);
        newVerse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(EditorActivity.EXTRA_FRAGMENT_FLAG,0);
                intent.setClass(PoetActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });

        newVerse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                /*LongClick begin Entire Editor*/
                AlertDialog.Builder builder = new AlertDialog.Builder(PoetActivity.this,R.style.CustomDialog)
                        .setMessage("进入完全编辑模式?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent();
                                intent.putExtra(EditorActivity.EXTRA_FRAGMENT_FLAG,1);
                                intent.setClass(PoetActivity.this,EditorActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null);

                builder.create().show();

                return true;
            }
        });
    }

}
