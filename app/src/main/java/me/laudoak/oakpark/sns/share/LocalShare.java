package me.laudoak.oakpark.sns.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by LaudOak on 2015-12-18 at 22:50.
 */
public class LocalShare
{
    public void share(String imgPath, String content, Context context)
    {
        File f = new File(imgPath);
        Uri uri = Uri.fromFile(f);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null && !content.equals(""))
        {
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            // 当用户选择短信时使用sms_body取得文字
            shareIntent.putExtra("sms_body", content);
        } else {
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        // 自定义选择框的标题
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
        // 系统默认标题
        // context.startActivity(shareIntent);
    }

}
