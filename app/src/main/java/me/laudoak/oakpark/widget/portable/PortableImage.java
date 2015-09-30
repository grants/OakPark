package me.laudoak.oakpark.widget.portable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class PortableImage extends ImageView {

    private static final int PADDING = 3;
    private static final float STROKE_WIDTH = 4;

    private Paint paint;

    public PortableImage(Context context) {
        super(context);
        init();
    }

    public PortableImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PortableImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setPadding(PADDING,PADDING,PADDING,PADDING);
        setBackgroundColor(getResources().getColor(R.color.white));

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.gray_7));
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);

        setOnTouchListener(new MultiTouchListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect(PADDING,PADDING,getWidth()-PADDING,getHeight()-PADDING);
        canvas.drawRect(rect,paint);

    }
}
