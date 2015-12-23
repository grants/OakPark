package me.laudoak.oakpark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.loani.ProgressWheel;
import me.laudoak.oakpark.utils.StringUtil;

/**
 * Created by LaudOak on 2015-12-15 at 21:39.
 */
public class EndDividerView extends RelativeLayout implements View.OnClickListener
{

    private static final String TAG = EndDividerView.class.getName();

    private Context context;

    private LinearLayout end;
    private ProgressWheel progressBar;
    private TextView loadFailed;

    private ReloadCallback reloadCallback;

    public EndDividerView(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public EndDividerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public EndDividerView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        View view = inflate(context, R.layout.view_tailed,this);

        end = (LinearLayout) view.findViewById(R.id.tailed_end);
        progressBar = (ProgressWheel) view.findViewById(R.id.tailed_progressbar);
        loadFailed = (TextView) view.findViewById(R.id.tailed_loadfailed);
        loadFailed.setText(StringUtil.genSpannyText("获取数据失败\n点击重新加载", StringUtil.SpannyType.UNDERLINE));
        loadFailed.setOnClickListener(this);
    }

    public void load()
    {
        hideAll();
        progressBar.setVisibility(View.VISIBLE);
    }

    public void failed()
    {
        hideAll();
        loadFailed.setVisibility(View.VISIBLE);
    }

    public void end()
    {
        hideAll();
        end.setVisibility(View.VISIBLE);
    }

    public void hideAll()
    {
        progressBar.setVisibility(View.GONE);
        loadFailed.setVisibility(View.GONE);
        end.setVisibility(View.GONE);
    }

    public void setReloadCallback(ReloadCallback reloadCallback)
    {
        this.reloadCallback = reloadCallback;
    }

    /**load failed text*/
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tailed_loadfailed:
            {
                if (null != reloadCallback)
                {
                    reloadCallback.reload();
                }

                break;
            }
        }
    }

    public interface ReloadCallback
    {
        void reload();
    }


}
