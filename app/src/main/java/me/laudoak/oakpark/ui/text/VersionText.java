package me.laudoak.oakpark.ui.text;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LaudOak on 2015-11-17 at 22:09.
 */
public class VersionText extends TextView {


    public VersionText(Context context) {
        super(context);
        init(context);
    }



    public VersionText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VersionText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        PackageManager packageManager = context.getPackageManager();

        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            String versionName = packageInfo.versionName;

            this.setText(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
