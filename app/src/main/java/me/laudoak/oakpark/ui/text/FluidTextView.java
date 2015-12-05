package me.laudoak.oakpark.ui.text;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LaudOak on 2015-12-5 at 13:31.
 */
public class FluidTextView extends TextView
{


    public FluidTextView(Context context)
    {
        super(context);
    }

    public FluidTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FluidTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused()
    {
        return true;
    }
}
