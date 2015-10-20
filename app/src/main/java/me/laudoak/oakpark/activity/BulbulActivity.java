package me.laudoak.oakpark.activity;


import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Poet;
import me.laudoak.oakpark.net.UserProxy;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.loading.LoadingDialog;
import me.laudoak.oakpark.widget.message.AppMsg;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-10-20 at 20:01.
 */
public class BulbulActivity extends XBaseActivity {

    private static final String TAG = "BulbulActivity";

    private static final int REQUEST_PICKER = 106;

    private SimpleDraweeView avatar;
    private AutofitTextView nick,email,num;
    private Poet poet;

    private String newPath = null;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_bulbul);
        poet = UserProxy.currentPoet(this);
    }

    @Override
    public void buildView() {
        finViews();
        buildBar();
        initViews();
        initlistener();
    }

    private void initlistener()
    {
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BulbulActivity.this, MultiImageSelectorActivity.class);
                intent.putExtra(CropperActivity.EXTRA_CROP_MODE, CropperActivity.CROP_MODE_AVATAR);
                startActivityForResult(intent,REQUEST_PICKER);
            }
        });
    }

    private void initViews()
    {
        if (null!=poet)
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

    private void finViews()
    {
        avatar = (SimpleDraweeView) findViewById(R.id.bulbul_avatar);
        nick = (AutofitTextView) findViewById(R.id.bulbul_nick);
        email = (AutofitTextView) findViewById(R.id.bulbul_email);
        num = (AutofitTextView) findViewById(R.id.bulbul_num);
    }

    private void buildBar()
    {
        getSupportActionBar().hide();

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BulbulActivity.this.finish();
            }
        });

        TextView done = (TextView) findViewById(R.id.ca_normal_done);
        done.setText("保存");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nick.getText().toString().trim().length() >= 2) {

                    final LoadingDialog dialog = new LoadingDialog(BulbulActivity.this);
                    dialog.show();

                    UserProxy.doUpdate(BulbulActivity.this,
                            nick.getText().toString().trim(),
                            newPath,
                            new UserProxy.CallBack()
                            {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            AppMsg.makeText(BulbulActivity.this,"已更新",AppMsg.STYLE_INFO).show();
                        }

                        @Override
                        public void onFailure(String reason) {
                            dialog.dismiss();
                            AppMsg.makeText(BulbulActivity.this,reason,AppMsg.STYLE_ALERT).show();
                        }
                    });
                } else {
                    AppMsg.makeText(BulbulActivity.this,"用户名不符",AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
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
