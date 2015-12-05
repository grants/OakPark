package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.activity.PrinterActivity;
import me.laudoak.oakpark.activity.WhisperActivity;
import me.laudoak.oakpark.entity.XVerse;
import me.laudoak.oakpark.net.bmob.push.PushXVerse;
import me.laudoak.oakpark.view.EntireEditorView;
import me.laudoak.oakpark.ui.dialog.MessageDialog;
import me.laudoak.oakpark.ui.message.AppMsg;
import me.laudoak.oakpark.ui.panel.XBasePanelView;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EntireEditorFragment extends XBaseFragment implements
        EditorActivity.PushCallback,
        XBasePanelView.OnPanelClickListener,
        PushXVerse.PushCallBack{

    private static final String TAG = "EntireEditorFragment";

    private static final String TAG_DIALOG_NOTICE = "TAG_DIALOG_NOTICE";

    public static final String EXTRA_WHISPER = "EXTRA_WHISPER";
    private static final int REQUEST_WHISPER = 1114;

    //request code should < 16bit;
    private static final int REQUEST_PICKER = 10010;

    private Context context;
    private FragmentManager fragmentManager;
    private EntireEditorView entireEditorView;
    private EntireEditorView.Holder holder;

    private String imagePath;

    private ProgressDialog ld;


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        fragmentManager = getFragmentManager();
        entireEditorView = new EntireEditorView(context);
        this.holder = entireEditorView.getHolder();
        imagePath = null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        buildViews();
        return entireEditorView;
    }


    private void buildViews() {
        buildPanelView();
        buildEditorView();
    }

    private void buildEditorView() {

        holder.whisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String whisper = holder.whisper.getText().toString();
                Intent intent = new Intent();
                intent.setClass(context, WhisperActivity.class);
                intent.putExtra(EXTRA_WHISPER, whisper);

                startActivityForResult(intent, REQUEST_WHISPER);
            }
        });
    }

    private void buildPanelView() {
        holder.panel.setListener(this);
    }


    /*XPanelView callback*/
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.edit_panel_preview:
            {
                doPreview();
                break;
            }

            case R.id.edit_panel_camera:
            {
                //!!!
                //this -> MultiImageSelectorActivity -> CropperActivity
                //CropperActivity -> MultiImageSelectorActivity -> this
                Intent intent = new Intent();
                intent.setClass(getActivity(), MultiImageSelectorActivity.class);
                intent.putExtra(CropperActivity.EXTRA_CROP_MODE, CropperActivity.CROP_MODE_NORMAL);
                startActivityForResult(intent,REQUEST_PICKER);
                break;

            }
        }
    }

    /**go to PinterActivity*/
    private void doPreview()
    {

        XVerse shareXVerse = genTempXVerse();
        if (null == shareXVerse)
        {
            showUncompleteDlg(checkShareAccord());
            return ;
        }

        Intent intent = new Intent(getActivity(), PrinterActivity.class);
        /**/
        PrinterActivity.VerseTag tag = PrinterActivity.VerseTag.XVERSE;
        tag.attachTo(intent);
        /**/
        intent.putExtra(PrinterActivity.EXTRA_VERSE_CONT, shareXVerse);
        /**/
        if (null != imagePath)
        {
            Uri uri = Uri.parse(imagePath);
            intent.putExtra(PrinterActivity.EXTRA_VERSE_URI_STR,uri.toString());
        }

        startActivity(intent);

    }

    private XVerse genTempXVerse()
    {
        XVerse xv = null;

        if (checkShareAccord().equals(""))
        {
            xv = new XVerse();
            xv.setTitle(holder.title.getText().toString().trim());
            xv.setAuthor(holder.author.getText().toString().trim());
            xv.setVerse(holder.verse.getText().toString());

        }

        return xv;
    }


    private String checkCompleteAccord()
    {

        StringBuilder noticeBuilder = new StringBuilder();

        if (holder.title.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("题目不能为空").append(System.getProperty("line.separator"));
        }
        if (holder.author.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("作者/译者不能为空").append(System.getProperty("line.separator"));
        }
        if (holder.verse.getText().toString().trim().length()<6)
        {
            noticeBuilder.append("主体部分过短").append(System.getProperty("line.separator"));
        }
        if (holder.whisper.getText().toString().trim().length()<6)
        {
            noticeBuilder.append("耳语过短").append(System.getProperty("line.separator"));
        }

        String notice = noticeBuilder.toString();

        if (!notice.equals(""))
        {
            return notice;
        }

        return "";
    }

    private String checkShareAccord()
    {

        StringBuilder noticeBuilder = new StringBuilder();

        if (holder.title.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("题目不能为空").append(System.getProperty("line.separator"));
        }
        if (holder.verse.getText().toString().trim().length()<6)
        {
            noticeBuilder.append("主体部分过短").append(System.getProperty("line.separator"));
        }

        String notice = noticeBuilder.toString();

        if (!notice.equals(""))
        {
            return notice;
        }

        return "";
    }

    private void showUncompleteDlg(String what)
    {
        MessageDialog dialog = MessageDialog.newInstance(what);
        dialog.show(fragmentManager,TAG_DIALOG_NOTICE);
    }

    /*EditorActivity callback*/
    @Override
    public void onPush() {

        String notice = checkCompleteAccord();
        if (!notice.equals(""))
        {
            showUncompleteDlg(notice);
            return;
        }

        ld = new ProgressDialog(context);
        ld.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ld.setMessage("正在保存...");
        ld.setTitle(null);
        ld.setCanceledOnTouchOutside(false);
        ld.show();

        PushXVerse.Builder builder = new PushXVerse.Builder(context);
        builder.title(holder.title.getText().toString().trim())
                .author(holder.author.getText().toString().trim())
                .verse(holder.verse.getText().toString())
                .whisper(holder.whisper.getText().toString())
                .imagePath(imagePath);

        builder.build().push(this);

    }

    /*push callback*/
    @Override
    public void onSuccess() {
        if(null!=ld)
        {
            ld.dismiss();
        }

        AppMsg.makeText(context, "已保存", AppMsg.STYLE_INFO).show();

        delayExit();
    }

    /*push callback*/
    @Override
    public void onFailure(String reason) {
        if(null!=ld)
        {
            ld.dismiss();
        }

        AppMsg.makeText(context,reason,AppMsg.STYLE_ALERT).show();
    }

    /*Get bitmap path*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*handle pick image & crop image result;*/
        if(requestCode==REQUEST_PICKER&&resultCode==Activity.RESULT_OK&&null!=data)
        {
            this.imagePath = data.getStringExtra(MultiImageSelectorActivity.EXTRA_CROPPER_RESULT);
            Log.d(TAG,"requestCode==REQUEST_CROPPER"+"imagePath"+imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageBitmap(bitmap);
        }

        /*handle whisper editor result;*/
        if (requestCode==REQUEST_WHISPER&&resultCode==Activity.RESULT_OK&&null!=data)
        {
            String whisper = data.getStringExtra(WhisperActivity.RESULT_WHISPER);
            if (null != whisper)
            {
                holder.whisper.setText(whisper);
            }
        }

    }
}
