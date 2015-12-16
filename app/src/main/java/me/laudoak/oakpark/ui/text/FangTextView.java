package me.laudoak.oakpark.ui.text;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import me.laudoak.oakpark.utils.font.FontsManager;

/**
 * Created by LaudOak on 2015-12-16 at 15:52.
 */
public class FangTextView extends TextView
{

    private static final String TAG = FangTextView.class.getName();

    private Context context;

    public FangTextView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public FangTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FangTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        FontsManager.initFormAssets(context, "fonts/optima.ttf");
        FontsManager.changeFonts(this);
    }
}
