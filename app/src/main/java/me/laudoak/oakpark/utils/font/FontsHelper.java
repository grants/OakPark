package me.laudoak.oakpark.utils.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by LaudOak on 2016-1-9 at 16:32.
 */
public class FontsHelper
{
    public static Typeface fang(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"fonts/fang.TTF");
    }

    public static Typeface def()
    {
        return Typeface.DEFAULT;
    }

}
