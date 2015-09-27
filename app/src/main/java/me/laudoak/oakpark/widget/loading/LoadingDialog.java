package me.laudoak.oakpark.widget.loading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-9-27.
 */
public class LoadingDialog extends ProgressDialog {

    private RotateLoading rotateLoading;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_loading);
        rotateLoading = (RotateLoading) findViewById(R.id.loading_dialog_rotateloading);
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        rotateLoading.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        rotateLoading.stop();
    }
}
