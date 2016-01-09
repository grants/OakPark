package me.laudoak.oakpark.ctrl.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import me.laudoak.oakpark.PreferConstants;

/**
 * Created by LaudOak on 2016-1-9 at 19:56.
 */
public class XVFontListener implements
        SharedPreferences.OnSharedPreferenceChangeListener
{

    private static final String TAG = XVFontListener.class.getName();

    private boolean useDefaultFont ;

    public XVFontListener(Context context)
    {
        useDefaultFont = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PreferConstants.USE_DEFAULT_FONT,true);
    }

    public boolean useDefaultFont()
    {
        return this.useDefaultFont;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {
        if (s.equals(PreferConstants.USE_DEFAULT_FONT))
        {
            useDefaultFont = sharedPreferences.getBoolean(PreferConstants.USE_DEFAULT_FONT,true);
            Log.d(TAG,PreferConstants.USE_DEFAULT_FONT+useDefaultFont);
        }
    }
}
