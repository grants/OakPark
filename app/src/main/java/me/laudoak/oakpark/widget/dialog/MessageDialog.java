package me.laudoak.oakpark.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-10-2.
 */
public class MessageDialog extends XBaseDialog {

    private static final String BUNDLE_MESSAGE = "BUNDLE_MESSAGE";

    private String message;

    public static MessageDialog newInstance(String msg)
    {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MESSAGE,msg);

        MessageDialog dialog = new MessageDialog();
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    void initData() {
        this.message = getArguments().getString(BUNDLE_MESSAGE);
    }

    @Override
    Dialog callDialog() {

        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog);
        builder.setMessage(message)
                .setPositiveButton("OK",null)
                .setTitle(null);

        return builder.create();
    }


}
