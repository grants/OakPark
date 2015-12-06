package me.laudoak.oakpark.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.sns.tpl.weibo.LoginButton;
import me.laudoak.oakpark.ui.fittext.AutofitTextView;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-10-20 at 20:01.
 */
public class BulbulActivity extends XBaseActivity {

    private static final String TAG = "BulbulActivity";

    private static final int REQUEST_PICKER = 106;

    @Bind(R.id.bulbul_avatar) SimpleDraweeView avatar;
    @Bind(R.id.bulbul_nick) AutofitTextView nick;
    @Bind(R.id.bulbul_email) AutofitTextView email;
    @Bind(R.id.bulbul_num) AutofitTextView num;
    @Bind(R.id.bulbul_qq) ImageView associateQQ;
    @Bind(R.id.bulbul_weibo) LoginButton associateWeibo;
    @Bind(R.id.bulbul_logout) Button loginOut;

    private Poet poet;

    private String newPath = null;

    private void buildView()
    {
        buildBar();
        initViews();
        initlistener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulbul);
        ButterKnife.bind(this);

        poet = UserProxy.currentPoet(this);
        buildView();
    }

    private void buildBar()
    {
        getSupportActionBar().hide();

        TextView title = (TextView) findViewById(R.id.ca_normal_title);
        title.setVisibility(View.VISIBLE);
        title.setText("设置");

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BulbulActivity.this.finish();
            }
        });

        TextView done = (TextView) findViewById(R.id.ca_normal_done);
        done.setText("保存");
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (nick.getText().toString().trim().length() >= 2)
                {
                    final ProgressDialog dialog = new ProgressDialog(BulbulActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.show();

                    UserProxy.doUpdate(BulbulActivity.this,
                            nick.getText().toString().trim(),
                            newPath,
                            new UserProxy.CallBack() {
                                @Override
                                public void onSuccess(String nick)
                                {
                                    dialog.dismiss();
                                    AppMsg.makeText(BulbulActivity.this, "已更新", AppMsg.STYLE_INFO).show();
                                    delayExit();
                                }

                                @Override
                                public void onFailure(String reason)
                                {
                                    dialog.dismiss();
                                    AppMsg.makeText(BulbulActivity.this, reason, AppMsg.STYLE_ALERT).show();
                                }
                            });
                } else
                {
                    AppMsg.makeText(BulbulActivity.this, "用户名不符", AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

    private void initlistener()
    {
        avatar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(BulbulActivity.this, MultiImageSelectorActivity.class);
                intent.putExtra(CropperActivity.EXTRA_CROP_MODE, CropperActivity.CROP_MODE_AVATAR);
                startActivityForResult(intent, REQUEST_PICKER);
            }
        });


        loginOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(BulbulActivity.this,R.style.CustomDialog)
                        .setMessage("确定退出登录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                BmobUser.logOut(BulbulActivity.this);
                                AppMsg.makeText(BulbulActivity.this, "已退出登陆", AppMsg.STYLE_INFO).show();

                                delayExit();

                            }
                        })
                        .setNegativeButton("取消", null);

                builder.create().show();
            }
        });
    }

    private void delayExit()
    {
        Handler handler = new Handler();
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                BulbulActivity.this.finish();
            }
        };

        handler.postDelayed(runnable, 500);
    }

    private void initViews()
    {
        if (null != poet)
        {
            nick.setText(poet.getUsername());
            email.setText(poet.getEmail());
            if (null!=poet.getAvatarURL())
            {
                Uri uri = Uri.parse(poet.getAvatarURL());
                avatar.setImageURI(uri);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!=RESULT_OK)
        {
            return;
        }

        if (requestCode == REQUEST_PICKER&&data!=null)
        {
            String path = data.getStringExtra(MultiImageSelectorActivity.EXTRA_CROPPER_RESULT);
            newPath = path;
            Uri uri = Uri.fromFile(new File(path));
            avatar.setImageURI(uri);
        }
    }

}
