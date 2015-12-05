package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.ArrayList;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;
import me.laudoak.oakpark.ui.loop.LoopListener;
import me.laudoak.oakpark.ui.loop.LoopView;

/**
 * Created by LaudOak on 2015-11-7 at 10:10.
 */
public class FontPickerFragment extends XBaseDialog
{

    private static final String TAG = "FontPickerFragment";

    private View rootView;

    private int fontId = 0;

    private CallBack callBack;

    private static class ClassHolder
    {
        private static final FontPickerFragment fragment = new FontPickerFragment();
    }

    public static FontPickerFragment newInstance()
    {
        return ClassHolder.fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        callBack = (CallBack) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {

        rootView = getActivity().getLayoutInflater().inflate(R.layout.view_pinter_loop,null);
        LoopView loopView = (LoopView) rootView.findViewById(R.id.pinter_font_loop);

        ArrayList<String> list = new ArrayList<String>();

        list.add("系统字体");
        list.add("方正宋刻");

        loopView.setNotLoop();

        loopView.setListener(new LoopListener()
        {
            @Override
            public void onItemSelect(int item)
            {
                if (item != fontId)
                {
                    fontId = item;
                }
            }
        });
        loopView.setArrayList(list);
        loopView.setPosition(0);
        loopView.setTextSize(20);
    }

    @Override
    public Dialog callDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomDialog)
                .setTitle("选择字体")
                .setView(rootView)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Typeface typeface = null;

                        switch (fontId)
                        {
                            case 0:
                            {
                                typeface = Typeface.DEFAULT;
                                break;
                            }
                            case 1:
                            {
                                typeface = Typeface.createFromAsset(context.getAssets(),"fonts/fang.TTF");
                                break;
                            }
                        }

                        if (null != typeface)
                        {
                            callBack.onSelected(typeface);
                        }
                    }
                });

        return builder.create();
    }


    public interface CallBack
    {
        void onSelected(Typeface typeface);
    }

}
