package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import me.laudoak.oakpark.net.push.VersePush;
import me.laudoak.oakpark.view.NormalEditorView;
import me.laudoak.oakpark.widget.dialog.MessageDialog;
import me.laudoak.oakpark.widget.message.AppMsg;
import me.laudoak.oakpark.widget.panel.XBasePanelView;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class NormalEditorFragment extends XBaseFragment implements
        EditorActivity.PushCallback ,
        XBasePanelView.OnPanelClickListener ,
        VersePush.PushCallBack{

    private static final String TAG = "NormalEditorFragment";

    private static final String TAG_DIALOG_NOTICE = "TAG_DIALOG_NOTICE";

    private static final int REQUEST_PICKER = 10086;

    private Context context;
    private FragmentManager fragmentManager;
    private NormalEditorView normalEditorView;
    private NormalEditorView.Holder holder;
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
        normalEditorView = new NormalEditorView(context);
        this.holder = normalEditorView.getHolder();
        imagePath = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        buildActivePanelView();

        return normalEditorView;
    }


    private void buildActivePanelView()
    {
        holder.panel.setListener(this);
    }

    /*XPanelView callback*/
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.edit_panel_preview:
            {
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

    /*Get bitmap path*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*handle crop result*/
        if(null==data||resultCode!= Activity.RESULT_OK)
        {
            //!!!
            Log.d(TAG,"onActivityResult:null==data||resultCode!= Activity.RESULT_OK");
            holder.image.setVisibility(View.GONE);
            imagePath = null;
            return ;
        }

        if(requestCode==REQUEST_PICKER)
        {
            this.imagePath = data.getStringExtra(MultiImageSelectorActivity.EXTRA_CROPPER_RESULT);
            Log.d(TAG,"requestCode==REQUEST_CROPPER"+"imagePath"+imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageBitmap(bitmap);
        }

    }

    /*EditorActivity callback*/
    @Override
    public void onPush() {

        StringBuilder noticeBuilder = new StringBuilder();

        if (holder.title.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("题目不能为空").append(System.getProperty("line.separator"));
        }
        if (holder.author.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("作者/译者不能为空").append(System.getProperty("line.separator"));
        }
        if (holder.verse.getText().toString().trim().length()<10)
        {
            noticeBuilder.append("主体过短").append(System.getProperty("line.separator"));
        }

        String notice = noticeBuilder.toString();
        if (!notice.equals(""))
        {
            MessageDialog dialog = MessageDialog.newInstance(noticeBuilder.toString());
            dialog.show(fragmentManager,TAG_DIALOG_NOTICE);
            return;
        }

        ld = new ProgressDialog(context);
        ld.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ld.setMessage("正在保存...");
        ld.setTitle(null);
        ld.setCanceledOnTouchOutside(false);
        ld.show();

        VersePush.Builder builder = new VersePush.Builder(context);
        builder.title(holder.title.getText().toString().trim())
                .author(holder.author.getText().toString().trim())
                .verse(holder.verse.getText().toString())
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

        AppMsg.makeText(context,"保存成功",AppMsg.STYLE_INFO).show();

        delayExit();
    }

    @Override
    public void onFailure(String reason) {

        if(null!=ld)
        {
            ld.dismiss();
        }

        AppMsg.makeText(context,reason,AppMsg.STYLE_ALERT).show();
    }
}
