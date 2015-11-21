package me.laudoak.oakpark.ui.fittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import me.laudoak.oakpark.R;


public class LinedEditor extends EditText implements AutofitHelper.OnTextSizeChangeListener {

    private AutofitHelper mHelper;

    private Paint paint;
    private Rect rect;

    public LinedEditor(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LinedEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LinedEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mHelper = AutofitHelper.create(this, attrs, defStyle)
                .addOnTextSizeChangeListener(this);

        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.gray_1));
        setLineSpacing(0,1.15f);
    }


    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (mHelper != null) {
            mHelper.setTextSize(unit, size);
        }
    }


    @Override
    public void setLines(int lines) {
        super.setLines(lines);
        if (mHelper != null) {
            mHelper.setMaxLines(lines);
        }
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        if (mHelper != null) {
            mHelper.setMaxLines(maxLines);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = getLineCount();
        for (int i=0;i<count;i++)
        {
            int baseLine = getLineBounds(i,rect);
            canvas.drawLine(rect.left,baseLine+7,rect.right,baseLine+7,paint);
        }
    }

    public AutofitHelper getAutofitHelper() {
        return mHelper;
    }

    public boolean isSizeToFit() {
        return mHelper.isEnabled();
    }

    public void setSizeToFit() {
        setSizeToFit(true);
    }

    public void setSizeToFit(boolean sizeToFit) {
        mHelper.setEnabled(sizeToFit);
    }


    public float getMaxTextSize() {
        return mHelper.getMaxTextSize();
    }


    public void setMaxTextSize(float size) {
        mHelper.setMaxTextSize(size);
    }


    public void setMaxTextSize(int unit, float size) {
        mHelper.setMaxTextSize(unit, size);
    }

    public float getMinTextSize() {
        return mHelper.getMinTextSize();
    }

    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    public void setMinTextSize(int unit, float minSize) {
        mHelper.setMinTextSize(unit, minSize);
    }


    public float getPrecision() {
        return mHelper.getPrecision();
    }


    public void setPrecision(float precision) {
        mHelper.setPrecision(precision);
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
    }
}
