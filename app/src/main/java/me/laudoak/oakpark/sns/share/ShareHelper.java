package me.laudoak.oakpark.sns.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.utils.FileUtil;

/**
 * Created by LaudOak on 2015-12-17 at 22:21.
 */
public class ShareHelper implements
        View.OnClickListener ,
        PlatformActionListener
{

    private static String TAG = ShareHelper.class.getName();

    private Context context;
    private ShareCallBack callBack;

    private Bitmap image;
    private String title;
    private String author;
    private String poem;

    private ShareHelper(Builder builder)
    {
        this.context = builder.context;

        this.image = builder.image;
        this.title = builder.title;
        this.author = builder.author;
        this.poem = builder.poem;
    }

    AlertDialog dialog;

    public void doShare(ShareCallBack callBack)
    {
        this.callBack = callBack;

        ShareSDK.initSDK(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_share, null, false);
        initViews(view);
        dialog = new AlertDialog.Builder(context,R.style.CustomDialog)
                .setView(view)
                .setTitle("分享到")
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }


    private void initViews(View view)
    {
        LinearLayout weibo,qq,qzone,wechatFriend,wechatFavorites,wechat,email,more;

        weibo = (LinearLayout) view.findViewById(R.id.shareend_weibo);
        qq = (LinearLayout) view.findViewById(R.id.shareend_qq);
        qzone = (LinearLayout) view.findViewById(R.id.shareend_qzone);
        wechatFriend = (LinearLayout) view.findViewById(R.id.shareend_wechatfriend);
        wechatFavorites = (LinearLayout) view.findViewById(R.id.shareend_wechatfavorite);
        wechat = (LinearLayout) view.findViewById(R.id.shareend_wechat);
        email = (LinearLayout) view.findViewById(R.id.shareend_email);
        more = (LinearLayout) view.findViewById(R.id.shareend_more);

        weibo.setOnClickListener(this);
        qq.setOnClickListener(this);
        qzone.setOnClickListener(this);
        wechatFriend.setOnClickListener(this);
        wechatFavorites.setOnClickListener(this);
        wechat.setOnClickListener(this);
        email.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.shareend_weibo:
            {
                SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                Platform pf = ShareSDK.getPlatform(context, SinaWeibo.NAME);
                sp.imagePath = FileUtil.saveImageToExternalStorage(context, image);
                sp.text = title;
                pf.setPlatformActionListener(this);
                pf.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_qq:
            {
                Platform plat = ShareSDK.getPlatform(context, QQ.NAME);
                QQ.ShareParams sp = new QQ.ShareParams();
                //sp.setTitle(title);
                //sp.setText(poem);
                sp.setImagePath(FileUtil.saveImageToExternalStorage(context, image));
                sp.setShareType(QQ.SHARE_IMAGE);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_qzone:
            {
                Platform plat = ShareSDK.getPlatform(context, QZone.NAME);
                QZone.ShareParams sp = new QZone.ShareParams();
                sp.setTitle(title);
                sp.setText(poem);
                sp.setImageData(image);
                sp.setImagePath(FileUtil.saveImageToExternalStorage(context, image));
                sp.setShareType(QZone.SHARE_IMAGE);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_wechat:
            {

                Platform plat = ShareSDK.getPlatform(context, WechatMoments.NAME);
                WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                sp.title = title;
                sp.text = poem;
                sp.imageData = image;
                sp.imagePath = FileUtil.saveImageToExternalStorage(context, image);
                sp.shareType = Platform.SHARE_IMAGE;
                plat.setPlatformActionListener(this);
                plat.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_wechatfavorite:
            {
                Platform plat = ShareSDK.getPlatform(context, WechatFavorite.NAME);
                WechatFavorite.ShareParams sp = new WechatFavorite.ShareParams();
                sp.title = title;
                sp.text = poem;
                sp.imageData = image;
                sp.imagePath = FileUtil.saveImageToExternalStorage(context, image);
                sp.shareType = Platform.SHARE_IMAGE;
                plat.setPlatformActionListener(this);
                plat.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_wechatfriend:
            {
                Platform plat = ShareSDK.getPlatform(context, Wechat.NAME);
                Wechat.ShareParams sp = new Wechat.ShareParams();
                sp.setTitle(title);
                sp.setText(poem);
                sp.setImageData(image);
                sp.setImagePath(FileUtil.saveImageToExternalStorage(context, image));
                sp.setShareType(Platform.SHARE_IMAGE);
                plat.setPlatformActionListener(this);
                plat.share(sp);
                dialog.dismiss();
                break;
            }
            case R.id.shareend_email:
            {
                break;
            }

            case R.id.shareend_more:
            {
                new LocalShare().share(FileUtil.saveImageToExternalStorage(context, image),title,context);
                dialog.dismiss();
                break;
            }

        }
    }


    /**Share Platform Callback*/
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap)
    {
        if (null != callBack)
        {
            callBack.onSuccess();
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable)
    {
        if (null != callBack)
        {
            callBack.onFailure("分享失败");
        }
    }

    @Override
    public void onCancel(Platform platform, int i)
    {
        if (null != callBack)
        {
            callBack.onCalcle("分享被取消");
        }
    }
    /**end of Share Platform Callback*/


    public static class Builder
    {
        private Context context;
        private Bitmap image;
        private String title;
        private String author;
        private String poem;


        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder image(Bitmap image)
        {
            this.image = image;
            return this;
        }

        public Builder title(String title)
        {
            this.title = title;
            return this;
        }

        public Builder author(String author)
        {
            this.author = author;
            return this;
        }

        public Builder poem(String poem)
        {
            this.poem = poem;
            return this;
        }

        public ShareHelper build()
        {
            return new ShareHelper(this);
        }

    }

    public interface ShareCallBack
    {
        void onSuccess();
        void onFailure(String why);
        void onCalcle(String des);
    }
}
