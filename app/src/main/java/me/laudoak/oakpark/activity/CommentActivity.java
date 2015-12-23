package me.laudoak.oakpark.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.core.Poet;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.fragment.EntireEditorFragment;
import me.laudoak.oakpark.fragment.AbSupCommentFragment;
import me.laudoak.oakpark.net.bmob.DoComment;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.ui.fittext.AutofitTextView;
import me.laudoak.oakpark.ui.message.AppMsg;

/**
 * Created by LaudOak on 2015-10-22 at 22:33.
 */
public class CommentActivity extends XBaseActivity implements DoComment.CallBack{

    private static final String TAG = "CommentActivity";

    public static final String RESULT_WHISPER = "RESULT_COMMENT";

    private AutofitTextView comment;

    private XVerse xv;

    private ProgressDialog ld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        xv = (XVerse) getIntent().getSerializableExtra(AbSupCommentFragment.EXTRA_XVERSE);
        buildView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void buildView() {
        comment = (AutofitTextView) findViewById(R.id.activity_comment_comment);
        Intent i = getIntent();
        comment.setText(i.getStringExtra(EntireEditorFragment.EXTRA_WHISPER));
        buildBar();
    }

    private void buildBar() {

        getSupportActionBar().hide();

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CommentActivity.this.finish();
            }
        });

        TextView title = (TextView) findViewById(R.id.ca_normal_title);
        title.setText("评论");

        findViewById(R.id.ca_normal_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString().length() > 2) {

                    Poet poet = UserProxy.currentPoet(CommentActivity.this);
                    if (null != poet)
                    {
                        ld = new ProgressDialog(CommentActivity.this);
                        ld.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        ld.setMessage("正在评论...");
                        ld.setTitle(null);
                        ld.setCanceledOnTouchOutside(false);
                        ld.show();

                        DoComment doComment = new DoComment.Builder(CommentActivity.this)
                                .content(comment.getText().toString())
                                .xVerse(xv)
                                .poet(poet)
                                .commentTime(System.currentTimeMillis())
                                .build();
                        doComment.doComment(CommentActivity.this);
                    }else {
                        AppMsg.makeText(CommentActivity.this, "用户不存在", AppMsg.STYLE_CONFIRM).show();
                    }

                } else {
                    AppMsg.makeText(CommentActivity.this, "文本太短", AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

    /*Comment callback*/
    @Override
    public void onSuccess() {

        if (null!=ld&&ld.isShowing())
        {
            ld.dismiss();
        }

        AppMsg.makeText(this,"评论成功",AppMsg.STYLE_INFO).show();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                CommentActivity.this.setResult(RESULT_OK,null);
                CommentActivity.this.finish();
            }
        };

        handler.postDelayed(runnable, 1200);

    }

    @Override
    public void onFailure(String why) {
        if (null!=ld&&ld.isShowing())
        {
            ld.dismiss();
        }
        AppMsg.makeText(this,why,AppMsg.STYLE_ALERT).show();
    }
    /*Comment callback*/

}
