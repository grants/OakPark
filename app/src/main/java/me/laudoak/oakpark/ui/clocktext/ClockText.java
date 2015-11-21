package me.laudoak.oakpark.ui.clocktext;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.laudoak.oakpark.utils.TimeUtil;

/**
 * Created by Oliver on 2015/7/3.
 */
public class ClockText extends TextView {


    private long mPublishTime;
    private Handler mHandler = new Handler();


    public ClockText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
    }

    public ClockText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        try {
            mPublishTime = Long.valueOf((String) getText());
        } catch (NumberFormatException nfe) {
            mPublishTime = -1L;
        }

    }


    private Runnable mUpdateTimeRunnable = new Runnable() {
        @Override
        public void run()
        {
            long timeDifference = Math.abs(System.currentTimeMillis()-mPublishTime);

            long delayTime = DateUtils.MINUTE_IN_MILLIS;

            if(timeDifference>DateUtils.WEEK_IN_MILLIS)
            {
                delayTime = DateUtils.WEEK_IN_MILLIS;
            }else if(timeDifference>DateUtils.DAY_IN_MILLIS)
            {
                delayTime = DateUtils.DAY_IN_MILLIS;
            }
            else if(timeDifference> DateUtils.HOUR_IN_MILLIS)
            {
                delayTime = DateUtils.HOUR_IN_MILLIS;
            }

            updateTextView();

            mHandler.postDelayed(this,delayTime);
        }
    };


    private void updateTextView()
    {
        if(mPublishTime == -1)
        {
            return ;
        }

        setText(TimeUtil.getTimeTextViewTimeDifference(getContext(), mPublishTime));
    }

    public void setPushTime(long mpt)
    {
        this.mPublishTime = mpt;

        updateTextView();
    }


    private void startContinuouslyUpdateTimeTextView()
    {
        mHandler.post(mUpdateTimeRunnable);
    }

    private void stopContinuouslyUpdateTimeTextView()
    {
        mHandler.removeCallbacks(mUpdateTimeRunnable);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        startContinuouslyUpdateTimeTextView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        stopContinuouslyUpdateTimeTextView();
    }


    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if(visibility==INVISIBLE||visibility==GONE)
        {
            stopContinuouslyUpdateTimeTextView();
        }

        startContinuouslyUpdateTimeTextView();
    }


    @Override
    public Parcelable onSaveInstanceState() {

        Parcelable state = super.onSaveInstanceState();

        SavedState curState = new SavedState(state);
        curState.publishTime = mPublishTime;

        return curState;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

        if(!(state instanceof SavedState))
        {
            super.onRestoreInstanceState(state);
            return ;
        }


        SavedState curState = (SavedState) state;
        mPublishTime = curState.publishTime;

        super.onRestoreInstanceState(curState.getSuperState());
    }




    private static class SavedState extends BaseSavedState {

        private long publishTime;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(publishTime);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel in) {
            super(in);
            publishTime = in.readLong();
        }
    }
}
