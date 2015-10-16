package me.laudoak.oakpark.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;

import me.laudoak.oakpark.utils.CalUtil;
import me.laudoak.oakpark.widget.calendar.cons.DPMode;
import me.laudoak.oakpark.widget.calendar.views.DatePicker;

/**
 * Created by LaudOak on 2015-10-16 at 13:04.
 */
public class CalPicker {

    private static final String TAG = "CalPicker";

    private Context context;
    private Callback callback;

    public CalPicker(Context context,Callback callback)
    {
        this.context = context;
        this.callback = callback;
        showCal();
    }

    private void showCal()
    {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        DatePicker picker = new DatePicker(context);

        picker.setDate(CalUtil.getYM()[0],CalUtil.getYM()[1]);
        picker.setMode(DPMode.SINGLE);

        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Log.d(TAG, "onDatePicked(String date)" + date);
                sendResult(date);
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);

    }

    private void sendResult(String date)
    {
        int result = CalUtil.dateStringToDateCode(date);
        this.callback.onPick(result);
    }

    public interface Callback
    {
        void onPick(int dc);
    }

}
