package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.fittext.LinedEditor;
import me.laudoak.oakpark.widget.panel.XBasePanelView;


/**
 * Created by LaudOak on 2015-9-29.
 */
public class NormalEditorView extends XBaseEditorView implements XBasePanelView.OnPanelClickListener{

    private static final String TAG = "NormalEditorView";

    public NormalEditorView(Context context) {
        super(context);
    }

    public NormalEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void buildActiveEditorView() {
        buildActivePanelView();
        time.setVisibility(View.GONE);
        whisper.setVisibility(View.GONE);
    }

    private void buildActivePanelView() {
        panel.setListener(this);
        panel.getCalendar().setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.preview:
            {
                break;
            }

            case R.id.edit_panel_camera:
            {
                break;
            }
        }
    }

    protected class Holder
    {
        AutofitTextView title;
        AutofitTextView author;
        LinedEditor verse;
        String imagePath;
    }
}
