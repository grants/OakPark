package me.laudoak.oakpark.widget.paging;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.widget.loading.RotateLoading;

/**
 * Created by LaudOak on 2015-10-4 at 22:43.
 */
public class LoadMoreView extends LinearLayout {

    private RotateLoading rotateLoading;

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLoadingView(context);
    }

    public LoadMoreView(Context context) {
        super(context);
        initLoadingView(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoadingView(context);
    }

    private void initLoadingView(Context context)
    {
        View loadingView = inflate(context, R.layout.view_load_more,this);
        //rotateLoading = (RotateLoading) loadingView.findViewById(R.id.load_more_rotate);
        rotateLoading.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!rotateLoading.isStart())
        {
            rotateLoading.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        rotateLoading.stop();
    }

}
