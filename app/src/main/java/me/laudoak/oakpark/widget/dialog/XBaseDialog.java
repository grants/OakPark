package me.laudoak.oakpark.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import me.laudoak.oakpark.R;


/**
 * Created by LaudOak on 2015-9-29.
 */
public abstract class XBaseDialog extends DialogFragment {

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        setStyle(DialogFragment.STYLE_NORMAL,R.style.CustomDialog);
        initData();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return callDialog();
    }

    protected abstract void initData();
    public abstract Dialog callDialog();
}
