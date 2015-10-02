package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import me.laudoak.oakpark.widget.fittext.AutofitTextView;
import me.laudoak.oakpark.widget.fittext.LinedEditor;
import me.laudoak.oakpark.widget.panel.XBasePanelView;
import me.laudoak.oakpark.widget.portable.PortableImage;


/**
 * Created by LaudOak on 2015-9-29.
 */
public class NormalEditorView extends XBaseEditorView{

    private static final String TAG = "NormalEditorView";

    private Holder holder;

    public NormalEditorView(Context context) {
        super(context);
        init();
    }

    public NormalEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    void buildActiveEditorView() {
        date.setVisibility(View.GONE);
        whisper.setVisibility(View.GONE);
    }

    private void init()
    {
        holder = new Holder();
        holder.panel = panel;
        holder.title = title;
        holder.author = author;
        holder.verse = verse;
        holder.image = image;
    }

    public class Holder
    {
        public XBasePanelView panel;
        public AutofitTextView title;
        public AutofitTextView author;
        public LinedEditor verse;
        public PortableImage image;
    }

    public Holder getHolder()
    {
        return this.holder;
    }
}
