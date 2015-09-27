package me.laudoak.oakpark.widget.message;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import me.laudoak.oakpark.R;

/**
 * In-layout notifications. Based on {@link android.widget.Toast} notifications
 * and article by Cyril Mottier (http://android.cyrilmottier.com/?p=773).
 *
 * @author e.shishkin
 */
public class AppMsg {

    /**
     * Show the view or text notification for a short period of time. This time
     * could be user-definable. This is the default.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = 1500;

    /**
     * Show the view or text notification for a long period of time. This time
     * could be user-definable.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 3000;

    /**
     * Show the text notification for a long period of time with a negative style.
     */
    public static final Style STYLE_ALERT = new Style(LENGTH_LONG, R.color.red);

    /**
     * Show the text notification for a short period of time with a positive style.
     */
    public static final Style STYLE_CONFIRM = new Style(LENGTH_SHORT, R.color.yellow);

    /**
     * Show the text notification for a short period of time with a neutral style.
     */
    public static final Style STYLE_INFO = new Style(LENGTH_SHORT, R.color.green);

    private final Context mContext;
    private int mDuration = LENGTH_SHORT;
    private View mView;
    private LayoutParams mLayoutParams;
    private boolean mFloating;

    public AppMsg(Context context) {
        mContext = context;
    }


    public static AppMsg makeText(Context context, CharSequence text, Style style) {
        return makeText(context, text, style, R.layout.view_appmsg);
    }


    public static AppMsg makeText(Context context, CharSequence text, Style style, float textSize) {
        return makeText(context, text, style, R.layout.view_appmsg, textSize);
    }

    public static AppMsg makeText(Context context, CharSequence text, Style style, int layoutId) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(layoutId, null);

        return makeText(context, text, style, v, true);
    }


    public static AppMsg makeText(Context context, CharSequence text, Style style, int layoutId, float textSize) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(layoutId, null);

        return makeText(context, text, style, v, true, textSize);
    }


    public static AppMsg makeText(Context context, CharSequence text, Style style, View customView) {
        return makeText(context, text, style, customView, false);
    }


    private static AppMsg makeText(Context context, CharSequence text, Style style, View view, boolean floating) {
        return makeText(context, text, style, view, floating, 0);
    }


    private static AppMsg makeText(Context context, CharSequence text, Style style, View view, boolean floating, float textSize) {
        AppMsg result = new AppMsg(context);

        view.setBackgroundResource(style.background);

        TextView tv = (TextView) view.findViewById(android.R.id.message);
        if(textSize > 0) tv.setTextSize(textSize);
        tv.setText(text);

        result.mView = view;
        result.mDuration = style.duration;
        result.mFloating = floating;

        return result;
    }


    public static AppMsg makeText(Context context, int resId, Style style, View customView, boolean floating) {
        return makeText(context, context.getResources().getText(resId), style, customView, floating);
    }


    public static AppMsg makeText(Context context, int resId, Style style)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), style);
    }


    public static AppMsg makeText(Context context, int resId, Style style, int layoutId)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), style, layoutId);
    }


    public void show() {
        MsgManager manager = MsgManager.getInstance();
        manager.add(this);
    }


    public boolean isShowing() {
        if (mFloating) {
            return mView != null && mView.getParent() != null;
        } else {
            return mView.getVisibility() == View.VISIBLE;
        }
    }


    public void cancel() {
        MsgManager.getInstance().clearMsg(this);

    }

    public static void cancelAll() {
        MsgManager.getInstance().clearAllMsg();
    }


    public Context getContext() {
        return mContext;
    }


    public void setView(View view) {
        mView = view;
    }


    public View getView() {
        return mView;
    }


    public void setDuration(int duration) {
        mDuration = duration;
    }


    public int getDuration() {
        return mDuration;
    }


    public void setText(int resId) {
        setText(mContext.getText(resId));
    }


    public void setText(CharSequence s) {
        if (mView == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        TextView tv = (TextView) mView.findViewById(android.R.id.message);
        if (tv == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        tv.setText(s);
    }


    public LayoutParams getLayoutParams() {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return mLayoutParams;
    }


    public AppMsg setLayoutParams(LayoutParams layoutParams) {
        mLayoutParams = layoutParams;
        return this;
    }


    public AppMsg setLayoutGravity(int gravity) {
        mLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, gravity);
        return this;
    }


    public boolean isFloating() {
        return mFloating;
    }


    public void setFloating(boolean mFloating) {
        this.mFloating = mFloating;
    }


    public static class Style {

        private final int duration;
        private final int background;


        public Style(int duration, int resId) {
            this.duration = duration;
            this.background = resId;
        }


        public int getDuration() {
            return duration;
        }


        public int getBackground() {
            return background;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Style)) {
                return false;
            }
            Style style = (Style) o;
            return style.duration == duration
                    && style.background == background;
        }

    }

}