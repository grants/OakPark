package me.laudoak.oakpark.ctrl.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.umeng.fb.FeedbackAgent;

import me.laudoak.oakpark.activity.AboutActivity;
import me.laudoak.oakpark.activity.BulbulActivity;
import me.laudoak.oakpark.activity.LicenseActivity;
import me.laudoak.oakpark.activity.SettingActivity;
import me.laudoak.oakpark.fragment.PoetryPickerFragment;
import me.laudoak.oakpark.fragment.VersionDlgFragment;
import me.laudoak.oakpark.net.bmob.UserProxy;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.settinglv.SettingLvAdapter;

/**
 * Created by LaudOak on 2015-12-1 at 14:27.
 */
public class SettingLvListener implements AdapterView.OnItemClickListener
{
    private static final String TAG = "SettingLvListener";

    private static final String FLAG_DLG_LICENSE = "license dlg flag";
    private static final String FLAG_DLG_WEBSITES = "about poetry websites";
    private static final String FLAG_DLG_VERSION = "version dialog";

    private Context context;
    private SettingLvAdapter adapter;


    public SettingLvListener(Context context,SettingLvAdapter adapter)
    {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            /**Personal information*/
            case 0:
            {
                if (UserProxy.ifLogin(context))
                {
                    context.startActivity(new Intent(context, BulbulActivity.class));
                } else
                {
                    AppMsg.makeText(context, "未登录", AppMsg.STYLE_CONFIRM).show();
                }
                break;
            }

            /**Using system font*/
            case 1:
            {
                break;
            }

            /**Open Source License*/
            case 2:
            {
                Intent intent = new Intent(context, LicenseActivity.class);
                context.startActivity(intent);
                break;
            }

            /**about poem website*/
            case 3:
            {
                PoetryPickerFragment poetryPickerFragment = PoetryPickerFragment.newInstance();
                poetryPickerFragment.show(((SettingActivity)context).getSupportFragmentManager(),FLAG_DLG_WEBSITES);
                break;
            }

            /**Version*/
            case 4:
            {
                VersionDlgFragment versionDlgFragment = VersionDlgFragment.newInstance();
                versionDlgFragment.show(((SettingActivity)context).getSupportFragmentManager(),FLAG_DLG_VERSION);
                break;
            }

            /**About*/
            case 5:
            {
                context.startActivity(new Intent(context, AboutActivity.class));
                break;
            }

            /**FeedBack*/
            case 6:
            {
                FeedbackAgent agent = new FeedbackAgent(context);
                agent.setWelcomeInfo("感谢使用和建议\uD83D\uDE3A \n你可以在此反馈,也可以发送邮件到1047627381@qq.com具体说明。");
                agent.startFeedbackActivity();
                break;
            }

        }
    }
}
