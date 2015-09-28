package me.laudoak.oakpark.widget.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LaudOak on 2015-9-28.
 */
public class XPanelView extends XBasePanelView {


    public XPanelView(Context context) {
        super(context);
    }

    public XPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void buildActiveView() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    @Override
    int getActiveViewWidthDp() {
        return 86;
    }

}
