package me.laudoak.oakpark.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import me.laudoak.oakpark.R;

/**
 * Created by LaudOak on 2015-12-23 at 15:49.
 */
public class EditDialog extends XBaseDialog
{

    private static class ClassHolder
    {
        private static final EditDialog fragment = new EditDialog();
    }

    public static EditDialog newInstance()
    {
        return ClassHolder.fragment;
    }

    private EditCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    private View rootView;
    private EditText editText;

    @Override
    public Dialog callDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog)
                .setTitle("编辑签名")
                .setView(rootView)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (null != callback )
                        {
                            callback.onEdit(editText.getText().toString());
                        }
                    }
                });

        return builder.create();
    }

    private void init()
    {
        rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_editdialog,null);
        editText = (EditText) rootView.findViewById(R.id.editdlg_edittext);
    }

    public void setCallback(EditCallback callback)
    {
        this.callback = callback;
    }

    public interface EditCallback
    {
        void onEdit(String content);
    }

}
