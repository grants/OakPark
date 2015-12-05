package me.laudoak.oakpark.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.dialog.XBaseDialog;

/**
 * Created by LaudOak on 2015-12-5 at 22:37.
 */
public class VersionDlgFragment extends XBaseDialog
{

    private static final String TAG = VersionDlgFragment.class.getName();


    private View contentView;

    public static VersionDlgFragment newInstance()
    {
        return new VersionDlgFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    @SuppressLint("InflateParams")
    private void init()
    {
        contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_version,null);

    }


    @Override
    public Dialog callDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog)
                .setTitle("版本")
                .setNegativeButton("确定",null)
                .setView(contentView);

        return builder.create();
    }
}
