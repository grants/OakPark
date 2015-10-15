package me.laudoak.oakpark.activity;

import android.content.Intent;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.fragment.EntireEditorFragment;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.message.AppMsg;

/**
 * Created by LaudOak on 2015-10-15 at 17:35.
 */
public class WhisperActivity extends XBaseActivity {

    private static final String TAG = "WhisperActivity";

    public static final String RESULT_WHISPER = "RESULT_WHISPER";

    private AutofitTextView whisper;

    @Override
    protected void setView()
    {
        setContentView(R.layout.activity_whisper);
    }

    @Override
    public void buildView()
    {
        whisper = (AutofitTextView) findViewById(R.id.activity_whisper_whisper);

        Intent i = getIntent();
        whisper.setText(i.getStringExtra(EntireEditorFragment.EXTRA_WHISPER));

        buildBar();
    }

    private void buildBar() {

        getSupportActionBar().hide();

        findViewById(R.id.ca_normal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhisperActivity.this.finish();
            }
        });

        findViewById(R.id.ca_normal_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whisper.getText().toString().length() > 10) {
                    sendWhisper();
                }else
                {
                    AppMsg.makeText(WhisperActivity.this,"文本太短",AppMsg.STYLE_CONFIRM).show();
                }
            }
        });
    }

    private void sendWhisper()
    {
        Intent intent = new Intent();
        intent.putExtra(this.RESULT_WHISPER,whisper.getText().toString());
        setResult(RESULT_OK, intent);

        this.finish();
    }
}
