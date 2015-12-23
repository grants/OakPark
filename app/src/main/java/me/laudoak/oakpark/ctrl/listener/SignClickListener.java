package me.laudoak.oakpark.ctrl.listener;

import android.content.Context;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.net.bmob.update.AbUpdate;
import me.laudoak.oakpark.net.bmob.update.UpdateSign;
import me.laudoak.oakpark.ui.dialog.EditDialog;

/**
 * Created by LaudOak on 2015-12-23 at 14:34.
 */
public class SignClickListener implements
        View.OnClickListener,EditDialog.EditCallback
{

    private Context context;
    private SignUpdateCallback callback;

    public SignClickListener(Context context ,SignUpdateCallback callback)
    {
        this.context = context;
        this.callback = callback;
    }

    private EditDialog dialog;

    @Override
    public void onClick(final View view)
    {
        if (view.getId() == R.id.drawer_sign)
        {
            dialog = EditDialog.newInstance();
            dialog.setCallback(this);
            dialog.show(((OakParkActivity)context).getSupportFragmentManager(),"editdialog");
        }
    }

    @Override
    public void onEdit(String content)
    {
        dialog.dismiss();

        new UpdateSign(context,content)
                .update(new AbUpdate.UpdateCallback(){
                    @Override
                    public void onSuccess()
                    {
                        if (null != callback)
                        {
                            callback.onSuccess();
                        }
                    }

                    @Override
                    public void onFailure(String why)
                    {
                        if (null != callback)
                        {
                            callback.onFailure(why);
                        }
                    }
                });
    }

    public interface SignUpdateCallback
    {
        void onSuccess();
        void onFailure(String why);
    }
}
