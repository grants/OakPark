package me.laudoak.oakpark.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.message = getArguments().getString(BUNDLE_MESSAGE);
    }

    @Override
    public Dialog callDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog);
        builder.setMessage(message)
                .setPositiveButton("确定",null)
                .setTitle(null);

        return builder.create();
    }


}
