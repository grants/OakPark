package me.laudoak.oakpark.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by LaudOak on 2015-9-29.
 */
public abstract class XBaseDialog extends DialogFragment
{
    private static final String TAG = XBaseDialog.class.getName();

    protected Context context;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.context = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return callDialog();
    }

    public abstract Dialog callDialog();

}
