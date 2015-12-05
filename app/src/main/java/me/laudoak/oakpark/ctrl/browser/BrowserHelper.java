package me.laudoak.oakpark.ctrl.browser;


import android.content.Context;

import com.thefinestartist.finestwebview.FinestWebView;


/**
 * Created by LaudOak on 2015-12-5 at 12:40.
 */
public class BrowserHelper
{
    private static final String TAG = BrowserHelper.class.getName();

    private Context context;

    public BrowserHelper(Context context)
    {
        this.context = context;
    }

    public void show(String url)
    {
        new FinestWebView.Builder(context)
                .toolbarScrollFlags(0) // By sending as 0, toolbar collapsing will be disabled
                .show(url);
    }

}
