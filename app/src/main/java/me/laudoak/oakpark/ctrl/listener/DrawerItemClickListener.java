package me.laudoak.oakpark.ctrl.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.BulbulActivity;
import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.activity.FriendActivity;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.ui.message.AppMsg;

/**
 * Created by LaudOak on 2015-12-14 at 16:37.
 */
public class DrawerItemClickListener implements
        View.OnClickListener,View.OnLongClickListener
{

    private static final String TAG = DrawerItemClickListener.class.getName();

    private Context context;

    public DrawerItemClickListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.drawer_item_newpoem:
            {
                goNormalEditor();
                break;
            }
            case R.id.drawer_item_personal:
            {
                goPersonal();
                break;
            }
            case R.id.drawer_item_calendar:
            {
                break;
            }
            case R.id.drawer_item_friend:
            {
                goFriend();
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        switch (view.getId())
        {
            case R.id.drawer_item_newpoem:
            {
                goEntireEditor();
                break;
            }
        }
        return true;
    }

    private void goPersonal()
    {
        if (UserProxy.ifLogin(context))
        {
            context.startActivity(new Intent(context, BulbulActivity.class));
        } else
        {
            AppMsg.makeText(context, "未登录", AppMsg.STYLE_CONFIRM).show();
        }
    }

    private void goEntireEditor()
    {
         /*LongClick begin Entire Editor*/
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog)
                .setMessage("进入完全编辑模式?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent intent = new Intent();
                        intent.putExtra(EditorActivity.EXTRA_FRAGMENT_FLAG, 1);
                        intent.setClass(context, EditorActivity.class);
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton("取消", null);

        builder.create().show();
    }

    private void goNormalEditor()
    {
        Intent intent = new Intent();
        intent.putExtra(EditorActivity.EXTRA_FRAGMENT_FLAG, 0);
        intent.setClass(context, EditorActivity.class);
        context.startActivity(intent);
    }

    private void goFriend()
    {
        if (UserProxy.ifLogin(context))
        {
            context.startActivity(new Intent(context,FriendActivity.class));
        }else
        {
            AppMsg.makeText(context, "请先登录", AppMsg.STYLE_CONFIRM).show();
        }


    }
}
