package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-11-7 at 9:25.
 */
public class PrinterPanelView extends LinearLayout implements View.OnClickListener{

    private static final String TAG = "PrinterPanelView";

    private TextView font,save,share;
    private CallBack callBack;

    public PrinterPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        inflate(getContext(), R.layout.view_tool_panel_printer,this);
        font = (TextView) findViewById(R.id.printer_panel_font);
        save = (TextView) findViewById(R.id.printer_panel_save);
        share = (TextView) findViewById(R.id.printer_panel_share);
        setListener();
    }

    private void setListener() {
        font.setOnClickListener(this);
        save.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    public void setCallBack(CallBack cb)
    {
        this.callBack = cb;
    }

    @Override
    public void onClick(View v) {
        if (null != callBack)
        {
            callBack.onClick(v);
        }
    }

    public interface CallBack
    {
        void onClick(View view);
    }

}
