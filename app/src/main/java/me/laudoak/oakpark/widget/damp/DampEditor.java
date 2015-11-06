package me.laudoak.oakpark.widget.damp;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by LaudOak on 2015-11-6 at 17:21.
 */
public class DampEditor extends ScrollView {

    private static final String TAG = "DampEditor";

    private boolean isCalled ;
    private boolean isFirst = true ;
    private Callback callback ;
    private View contentView ;
    private Rect rect = new Rect() ;
    private int y ;

    public DampEditor(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     * 由xml文件生成视图对象(所有子视图加载完成)之后调用,即使子类重写该方法，也应调用父类方法使其执行。
     */
    @Override
    protected void onFinishInflate()
    {
        if (getChildCount() > 0)
        {
            contentView = getChildAt(0);
        }

        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (null != contentView)
        {
            commonOnTouch(ev);
        }

        return super.onTouchEvent(ev);
    }

    private void commonOnTouch(MotionEvent ev) {

        int action = ev.getAction();
        int cy = (int) ev.getY();

        switch (action) {

            /**单点触摸*/
            case MotionEvent.ACTION_DOWN:
            {
                break;
            }

            /**触摸点移动动作*/
            case MotionEvent.ACTION_MOVE:
            {
                int dy = cy - y;
                if (isFirst) {
                    dy = 0;
                    isFirst = false;
                }
                y = cy;

                if (isNeedMove()) {
                    if (rect.isEmpty()) {
                        /*记录移动前的位置*/
                        rect.set(contentView.getLeft(), contentView.getTop(),
                                contentView.getRight(), contentView.getBottom());
                    }

                    contentView.layout(contentView.getLeft(), contentView.getTop() + 2 * dy / 3,
                            contentView.getRight(), contentView.getBottom() + 2 * dy / 3);

                    if (shouldCallBack(dy)) {
                        if (callback != null) {
                            if (!isCalled) {
                                isCalled = true;
                                resetPosition();
                                callback.onDamp();
                            }
                        }
                    }
                }

                break;
            }
            /**单点触摸离开动作*/
            case MotionEvent.ACTION_UP:
                if (!rect.isEmpty()) {
                    resetPosition();
                }
                break;

        }
    }


    /**当从上往下,移动距离达到一半时，回调接口*/
    private boolean shouldCallBack(int dy) {
        if (dy > 0 && contentView.getTop() > getHeight() / 2)
            return true;
        return false;
    }

    /**重置坐标*/
    private void resetPosition() {
        Animation animation = new TranslateAnimation(0, 0, contentView.getTop(),
                rect.top);
        animation.setDuration(200);
        animation.setFillAfter(true);
        contentView.startAnimation(animation);
        contentView.layout(rect.left, rect.top, rect.right, rect.bottom);
        rect.setEmpty();
        isFirst = true;
        isCalled = false;
    }

    /**是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度*/
    public boolean isNeedMove() {
        int offset = contentView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    /**当scrollView下拉到一定程度后，进行的回调方法*/
    public void setCallBack(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onDamp();
    }

}
