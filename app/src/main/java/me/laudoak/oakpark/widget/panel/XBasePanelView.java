package me.laudoak.oakpark.widget.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-9-28.
 */
public class XBasePanelView extends RelativeLayout {

    private static final String TAG = "XBasePanelView";

    protected Context context;
    protected OnPanelClickListener listener;


    protected XBasePanelView panel;

    protected ImageView add;
    protected ImageView camera,preview,calendar;
    protected LinearLayout activeLinear;

    public XBasePanelView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public XBasePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public XBasePanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init()
    {
        panel = (XBasePanelView) inflate(context, R.layout.view_edit_panel,this);

        add = (ImageView) panel.findViewById(R.id.edit_panel_add);
        preview = (ImageView) panel.findViewById(R.id.edit_panel_preview);
        camera = (ImageView) panel.findViewById(R.id.edit_panel_camera);
        calendar = (ImageView) panel.findViewById(R.id.edit_panel_calendar);
        activeLinear = (LinearLayout) panel.findViewById(R.id.edit_panel_content);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeLinear.isShown()) {
                    collapse(activeLinear);
                    add.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_add));

                } else {
                    expand(activeLinear);
                    add.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_subtract));
                }
            }
        });

        preview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=listener)
                {
                    listener.onClick(preview);
                }
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=listener)
                {
                    listener.onClick(camera);
                }
            }
        });
        calendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=listener)
                {
                    listener.onClick(calendar);
                }
            }
        });


//        //#2015-9-30
//        //bug here , repeat draw panel!!!
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)panel.getLayoutParams();
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        /*left,top,right,bottom*/
//        params.setMargins(MARGIN_PANEL, MARGIN_PANEL, 0, 0);
//        panel.setLayoutParams(params); //causes layout update


    }

    public void setListener(OnPanelClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnPanelClickListener
    {
        void onClick(View view);
    }

    private void expand(final View view) {
        view.measure(129, ViewGroup.LayoutParams.WRAP_CONTENT);

        final int targetWidth = view.getMeasuredWidth();

        view.getLayoutParams().width = 0;

        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().width = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int)(targetWidth * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int) (targetWidth / view.getContext().getResources().getDisplayMetrics().density));

        animation.setInterpolator(new AccelerateDecelerateInterpolator());

        view.startAnimation(animation);
    }

    private void collapse(final View view) {
        final int initialWidth = view.getMeasuredWidth();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().width = initialWidth - (int)(initialWidth * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int) (initialWidth / view.getContext().getResources().getDisplayMetrics().density));
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.startAnimation(animation);
    }

    public ImageView getCalendar() {
        return calendar;
    }

    public ImageView getPreview() {
        return preview;
    }

    public ImageView getCamera() {
        return camera;
    }

}
