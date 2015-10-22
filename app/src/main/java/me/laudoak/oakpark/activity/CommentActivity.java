package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.EntireEditorFragment;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-10-22 at 22:33.
 */
public class CommentActivity extends XBaseActivity {

    private static final String TAG = "CommentActivity";

    public static final String RESULT_WHISPER = "RESULT_COMMENT";

    private AutofitTextView comment;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_comment);
    }

    @Override
    public void buildView() {
        comment = (AutofitTextView) findViewById(R.id.activity_comment_comment);

        Intent i = getIntent();
        comment.setText(i.getStringExtra(EntireEditorFragment.EXTRA_WHISPER));

        buildBar();
    }

    private void buildBar() {

        getSupportActionBar().hide();

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.this.finish();
            }
        });

        findViewById(R.id.ca_normal_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString().length() > 2) {

                } else {
                    AppMsg.makeText(CommentActivity.this, "文本太短", AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

}
