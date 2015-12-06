package me.laudoak.oakpark.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ctrl.bulbul.BulbulController;
import me.laudoak.oakpark.ctrl.bulbul.UpdateCallback;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.sns.tpl.weibo.LoginButton;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-10-20 at 20:01.
 */
public class BulbulActivity extends XBaseActivity implements TextWatcher{

    private static final String TAG = "BulbulActivity";

    private static final int REQUEST_PICKER = 106;
    private static final int REQUEST_COVER = 107;


    @Bind(R.id.bulbul_cover) SimpleDraweeView cover;
    @Bind(R.id.bulbul_avatar) SimpleDraweeView avatar;
    @Bind(R.id.bulbul_nick) EditText nick;
    @Bind(R.id.bulbul_email) TextView email;
    @Bind(R.id.bulbul_num) TextView num;
    @Bind(R.id.bulbul_qq) ImageView associateQQ;
    @Bind(R.id.bulbul_weibo) LoginButton associateWeibo;
    @Bind(R.id.bulbul_logout) Button loginOut;

    private Poet poet;

    private BulbulController bulbulController;
    private String oldNick;
    private String newAvatarPath = null;
    private String newCoverPath = null;

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
        if (poet != null)
        {
            oldNick = poet.getUsername();
        }
        bulbulController = new BulbulController(this);

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
            public void onClick(View v)
            {

                final ProgressDialog dialog = new ProgressDialog(BulbulActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("正在更新...");
                dialog.show();

                bulbulController.update(new UpdateCallback()
                {
                    @Override
                    public void onForbidden(String why)
                    {
                        dialog.dismiss();
                        AppMsg.makeText(BulbulActivity.this, why, AppMsg.STYLE_CONFIRM).show();
                    }

                    @Override
                    public void onFailure(String why)
                    {
                        dialog.dismiss();
                        AppMsg.makeText(BulbulActivity.this,why,AppMsg.STYLE_ALERT).show();
                    }

                    @Override
                    public void onSuccess()
                    {
                        bulbulController.onNickChanged(false,nick.getText().toString());
                        bulbulController.onCoverChanged(false, newCoverPath);
                        bulbulController.onAvatarChanged(false,newAvatarPath);
                        dialog.dismiss();
                        AppMsg.makeText(BulbulActivity.this,"已更新",AppMsg.STYLE_INFO).show();
                    }
                });

            }
        });
    }

    private void initlistener()
    {
        cover.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //!!!
                //this -> MultiImageSelectorActivity -> CropperActivity
                //CropperActivity -> MultiImageSelectorActivity -> this
                Intent intent = new Intent();
                intent.setClass(BulbulActivity.this, MultiImageSelectorActivity.class);
                intent.putExtra(CropperActivity.EXTRA_CROP_MODE, CropperActivity.CROP_MODE_NORMAL);
                startActivityForResult(intent,REQUEST_COVER);
            }
        });

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

        nick.addTextChangedListener(this);

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

            if (null != poet.getCoverURL())
            {
                Uri uri = Uri.parse(poet.getCoverURL());
                cover.setAspectRatio(1.67f);
                cover.setImageURI(uri);
            }else
            {
                Uri uri = Uri.parse("res://me.luadoak.oakpark/"+R.drawable.sower);
                cover.setAspectRatio(1.67f);
                cover.setImageURI(uri);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode!=RESULT_OK)
        {
            return;
        }

        if (requestCode == REQUEST_PICKER&&data!=null)
        {
            this.newAvatarPath = data.getStringExtra(MultiImageSelectorActivity.EXTRA_CROPPER_RESULT);;
            bulbulController.onAvatarChanged(true,newAvatarPath);
            Uri uri = Uri.fromFile(new File(newAvatarPath));
            avatar.setImageURI(uri);
        }

        if(requestCode==REQUEST_COVER && data != null)
        {
            this.newCoverPath = data.getStringExtra(MultiImageSelectorActivity.EXTRA_CROPPER_RESULT);
            bulbulController.onCoverChanged(true,newCoverPath);
            Log.d(TAG, "requestCode==REQUEST_PICKER && data != null" + "imagePath" + newCoverPath);
            Uri uri = Uri.fromFile(new File(newCoverPath));
            cover.setAspectRatio(1.67f);
            cover.setImageURI(uri);
        }
    }

    /**Monitor nick change*/
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //doNothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //doNothing
    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        if (!nick.getText().toString().equals(oldNick))
        {
            bulbulController.onNickChanged(true,nick.getText().toString());
        }
    }
}
