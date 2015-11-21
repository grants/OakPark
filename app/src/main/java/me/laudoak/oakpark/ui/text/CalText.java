package me.laudoak.oakpark.ui.text;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import me.laudoak.oakpark.utils.TimeUtil;
import me.laudoak.oakpark.utils.font.FontsManager;

/**
 * Created by LaudOak on 2015-11-4 at 22:08.
 */
public class CalText extends TextView {

    private Context context;

    public CalText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CalText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CalText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        FontsManager.initFormAssets(context, "fonts/optima.ttf"); //初始化
        FontsManager.changeFonts(this);
        setTextSize(23);
        setTextColor(Color.BLACK);
        setText(TimeUtil.getWelcomeDateText());
    }

}

