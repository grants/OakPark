package me.laudoak.oakpark.widget.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-10-2.
 */
public class BubbleLinear extends LinearLayout {

    private static final String TAG = "BubbleLinear";

    private Context context;

    private BubbleDrb bubbleDrb;
    private float mArrowWidth;
    private float mArrowHeight;
    private float mArrowPosition;
    private BubbleDrb.ArrowLocation mArrowLocation;
    private int bubbleColor;

    public BubbleLinear(Context context) {
        super(context);
        this.context = context;

        init(null);
    }

    public BubbleLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(attrs);
    }


    private void init(AttributeSet attributeSet) {
        if (null!=attributeSet)
        {
            TypedArray array = context.obtainStyledAttributes(attributeSet,R.styleable.BubbleLinear);
            mArrowWidth = array.getDimension(R.styleable.BubbleLinear_arrowWidth,
                    BubbleDrb.Builder.DEFAULT_ARROW_WIDTH);
            mArrowHeight = array.getDimension(R.styleable.BubbleLinear_arrowHeight,
                    BubbleDrb.Builder.DEFAULT_ARROW_HEIGHT);
            mArrowPosition = array.getDimension(R.styleable.BubbleLinear_arrowPosition,
                    BubbleDrb.Builder.DEFAULT_ARROW_POSITION);
            bubbleColor = array.getColor(R.styleable.BubbleLinear_bubbleColor,
                    BubbleDrb.Builder.DEFAULT_BUBBLE_COLOR);
            int location = array.getInt(R.styleable.BubbleLinear_arrowLocation, 0);
            mArrowLocation = BubbleDrb.ArrowLocation.mapIntToValue(location);

            array.recycle();
        }

        setPadding((int) mArrowWidth, 0, 0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        if (w>0&&h>0)
        {
            setUp(w,h);
        }
    }


    private void setUp(int left, int right, int top, int bottom){
        if (right < left || bottom < top)
            return;
        RectF rectF = new RectF(left, top, right, bottom);

        bubbleDrb = new BubbleDrb.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .arrowPosition(mArrowPosition)
                .bubbleColor(bubbleColor)
                .build();

    }

    private void setUp(int width, int height) {

        //!!! #2015-10-9 when left params using getPaddingLeft() the child view it will overflow
        setUp(0, +width - getPaddingRight(), getPaddingTop(), height - getPaddingBottom());
        setBackground(bubbleDrb);
    }

}
