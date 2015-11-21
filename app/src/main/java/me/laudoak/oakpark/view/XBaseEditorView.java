package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.fittext.AutofitTextView;
import me.laudoak.oakpark.ui.fittext.LinedEditor;
import me.laudoak.oakpark.ui.panel.XBasePanelView;
import me.laudoak.oakpark.ui.portable.PortableImage;

/**
 * Created by LaudOak on 2015-9-29.
 */
public abstract class XBaseEditorView extends RelativeLayout{

    private static final String TAG = "XBaseEditorView";

    protected Context context;

    protected AutofitTextView title;
    protected AutofitTextView author;
    protected LinedEditor verse;
    protected AutofitTextView whisper;
    protected PortableImage image;
    protected XBasePanelView panel;

    public XBaseEditorView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public XBaseEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init()
    {
        View view = inflate(context,R.layout.view_editor,this);

        title = (AutofitTextView) view.findViewById(R.id.view_edit_title);
        author = (AutofitTextView) view.findViewById(R.id.view_edit_author);
        verse = (LinedEditor) view.findViewById(R.id.view_edit_verse);
        whisper = (AutofitTextView) view.findViewById(R.id.view_edit_whisper);
        image = (PortableImage) view.findViewById(R.id.view_edit_image);

        panel = (XBasePanelView) findViewById(R.id.view_edit_panel);

        buildActiveEditorView();
    }

    abstract void buildActiveEditorView();
}
