package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.utils.font.FontsManager;

/**
 * Created by LaudOak on 2015-12-23 at 12:43.
 */
public class StampView extends LinearLayout
{

    private static final String TAG = StampView.class.getName();

    private Context context;

    private TextView creator,by;

    public StampView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public StampView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public StampView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init()
    {
        View view = inflate(context, R.layout.view_stamp,this);
        creator = (TextView) view.findViewById(R.id.stamp_creator);
        by = (TextView) view.findViewById(R.id.stamp_by);
        FontsManager.initFormAssets(context,"fonts/fang.TTF");
        FontsManager.changeFonts(this);
    }

    public void setCreator(String creatorStr)
    {
        creator.setText(creatorStr);
    }

    public void setBy(String byStr)
    {
        by.setText(byStr);
    }
}
