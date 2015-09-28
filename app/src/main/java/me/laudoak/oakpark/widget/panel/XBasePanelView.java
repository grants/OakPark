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
public abstract class XBasePanelView extends RelativeLayout {

    protected Context context;
    protected OnClickListener listener;

    protected ImageView add;
    protected ImageView camera,preview;
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
        View view = inflate(context, R.layout.view_edit_panel,this);
        add = (ImageView) view.findViewById(R.id.edit_panel_add);
        preview = (ImageView) view.findViewById(R.id.edit_panel_preview);
        camera = (ImageView) view.findViewById(R.id.edit_panel_camera);
        activeLinear = (LinearLayout) view.findViewById(R.id.edit_panel_content);
        buildActiveView();
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
    }

    abstract void buildActiveView();
    abstract int getActiveViewWidthDp();

    public void setListener(OnClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnClickListener
    {
        void onClick(View view);
    }

    private void expand(final View view) {
        view.measure(getActiveViewWidthDp(), ViewGroup.LayoutParams.WRAP_CONTENT);

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

}
