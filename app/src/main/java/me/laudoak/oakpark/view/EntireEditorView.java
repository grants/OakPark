package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;

import me.laudoak.oakpark.ui.fittext.AutofitTextView;
import me.laudoak.oakpark.ui.fittext.LinedEditor;
import me.laudoak.oakpark.ui.panel.XBasePanelView;
import me.laudoak.oakpark.ui.portable.PortableImage;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EntireEditorView extends XBaseEditorView {

    private static final String TAG = "EntireEditorView";

    private Holder holder;

    public EntireEditorView(Context context) {
        super(context);
        init();
    }

    public EntireEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    void buildActiveEditorView() {

    }

    private void init()
    {
        holder = new Holder();
        holder.panel = panel;
        holder.title = title;
        holder.author = author;
        holder.verse = verse;
        holder.whisper = whisper;
        holder.image = image;
    }

    public class Holder
    {
        public XBasePanelView panel;
        public AutofitTextView title;
        public AutofitTextView author;
        public LinedEditor verse;
        public AutofitTextView whisper;
        public PortableImage image;
    }

    public Holder getHolder()
    {
        return this.holder;
    }

}
