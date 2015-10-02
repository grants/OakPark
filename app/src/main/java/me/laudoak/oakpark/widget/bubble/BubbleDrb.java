package me.laudoak.oakpark.widget.bubble;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by LaudOak on 2015-10-2.
 */
public class BubbleDrb extends Drawable {

    private RectF rect;
    private Path path;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float arrowHeight;
    private float arrowWidth;
    private float arrowPosition;

    private int bubbleColor;
    private ArrowLocation arrowLocation;

    private BubbleDrb(Builder builder)
    {
        this.rect = builder.mRect;
        this.arrowHeight = builder.mArrowHeight;
        this.arrowWidth = builder.mArrowWidth;
        this.arrowPosition = builder.mArrowPosition;
        this.bubbleColor = builder.bubbleColor;
        this.arrowLocation = builder.mArrowLocation;
    }

    /*implement father abstract method*/
    @Override
    public void draw(Canvas canvas) {

        paint.setColor(bubbleColor);

        path = new Path();

        if (null!=arrowLocation)
        {
            setPath();
            canvas.drawPath(path, paint);
        }

    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    /**/


    private void setPath()
    {
        switch (arrowLocation){
            case LEFT:
                setLeftPath(rect, path);
                break;
            case RIGHT:
                setRightPath(rect, path);
                break;
            case TOP:
                setTopPath(rect, path);
                break;
            case BOTTOM:
                setBottomPath(rect, path);
                break;
        }
    }

    private void setLeftPath(RectF r,Path p)
    {
        /**/
        p.moveTo(arrowWidth+r.left,r.top);
        p.lineTo(r.right,r.top);

        /**/
        p.lineTo(r.right,r.bottom);

        /**/
        p.lineTo(arrowWidth+r.left,r.bottom);

        /**/
        p.lineTo(arrowWidth+r.left,arrowPosition+arrowHeight);

        /**/
        p.lineTo(r.left,arrowPosition+arrowHeight/2);

        /**/
        p.lineTo(arrowWidth+r.left,arrowPosition);

        /**/
        p.lineTo(arrowWidth+r.left,r.top);

        p.close();
    }

    private void setTopPath(RectF r,Path p)
    {

    }

    private void setRightPath(RectF r,Path p)
    {

    }

    private void setBottomPath(RectF r,Path p)
    {

    }

    /*Builder pattern*/
    public static class Builder{
        public static float DEFAULT_ARROW_WIDTH = 25;
        public static float DEFAULT_ARROW_HEIGHT = 30;
        public static float DEFAULT_ARROW_POSITION = 40;
        public static int DEFAULT_BUBBLE_COLOR = Color.WHITE;

        private RectF mRect;
        private float mArrowWidth = DEFAULT_ARROW_WIDTH;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private float mArrowPosition = DEFAULT_ARROW_POSITION;
        private int bubbleColor = DEFAULT_BUBBLE_COLOR;
        private ArrowLocation mArrowLocation = ArrowLocation.LEFT;

        public Builder rect(RectF rect)
        {
            this.mRect = rect;
            return this;
        }

        public Builder arrowWidth(float mArrowWidth)
        {
            this.mArrowWidth = mArrowWidth;
            return this;
        }

        public Builder arrowHeight(float mArrowHeight)
        {
            this.mArrowHeight = mArrowHeight;
            return this;
        }

        public Builder arrowPosition(float mArrowPosition)
        {
            this.mArrowPosition = mArrowPosition;
            return this;
        }

        public Builder bubbleColor(int bubbleColor)
        {
            this.bubbleColor = bubbleColor;
            return this;
        }

        public Builder arrowLocation(ArrowLocation arrowLocation)
        {
            this.mArrowLocation = arrowLocation;
            return this;
        }


        public BubbleDrb build()
        {
            if (mRect == null)
            {
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }

            return new BubbleDrb(this);
        }
    }


    /*arrow location:left,top,right,bottom*/
    public enum ArrowLocation{
        LEFT(0x00),
        RIGHT(0x01),
        TOP(0x02),
        BOTTOM(0x03);

        private int mValue;

        ArrowLocation(int value){
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int stateInt) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowLocation getDefault(){
            return LEFT;
        }

        public int getIntValue() {
            return mValue;
        }
    }

}
