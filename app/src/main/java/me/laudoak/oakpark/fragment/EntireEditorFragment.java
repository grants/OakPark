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

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.activity.WhisperActivity;
import me.laudoak.oakpark.net.push.XVersePush;
import me.laudoak.oakpark.view.CalPicker;
import me.laudoak.oakpark.view.EntireEditorView;
import me.laudoak.oakpark.widget.dialog.MessageDialog;
import me.laudoak.oakpark.widget.message.AppMsg;
import me.laudoak.oakpark.widget.panel.XBasePanelView;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EntireEditorFragment extends XBaseFragment implements
        EditorActivity.PushCallback,
        XBasePanelView.OnPanelClickListener,
        XVersePush.PushCallBack,
        CalPicker.Callback{

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

    private View rootView;

    private String imagePath;
    private int dateCode;

    private ProgressDialog ld;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        fragmentManager = getFragmentManager();
        entireEditorView = new EntireEditorView(context);
        this.holder = entireEditorView.getHolder();
        imagePath = null;
        dateCode = holder.dateCodeText.getDateCode();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootView)
        {
            rootView = inflater.inflate(R.layout.view_editor,container,false);
        }else if (null != (rootView.getParent())){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }

        buildViews(rootView);

        return rootView;
    }


    private void buildViews(View view) {
        buildPanelView();
        buildEditorView();
    }

    private void buildEditorView() {

        holder.dateCodeText.setVisibility(View.VISIBLE);
        holder.whisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String whisper = holder.whisper.getText().toString();
                Intent intent = new Intent();
                intent.setClass(context, WhisperActivity.class);
                intent.putExtra(EXTRA_WHISPER, whisper);

                startActivityForResult(intent,REQUEST_WHISPER);
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

            case R.id.edit_panel_calendar:
            {
                new CalPicker(context,this);
                break;
            }
        }
    }

    /*EditorActivity callback*/
    @Override
    public void onPush() {

        StringBuilder noticeBuilder = new StringBuilder();

        if (holder.title.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("完善题目").append(System.getProperty("line.separator"));
        }
        if (holder.author.getText().toString().trim().equals(""))
        {
            noticeBuilder.append("完善作者/译者").append(System.getProperty("line.separator"));
        }
        if (holder.verse.getText().toString().trim().length()<10)
        {
            noticeBuilder.append("完善主体").append(System.getProperty("line.separator"));
        }
        if (holder.whisper.getText().toString().trim().length()<10)
        {
            noticeBuilder.append("完善诗语").append(System.getProperty("line.separator"));
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

        XVersePush.Builder builder = new XVersePush.Builder(context);
        builder.title(holder.title.getText().toString().trim())
                .author(holder.author.getText().toString().trim())
                .verse(holder.verse.getText().toString())
                .whisper(holder.whisper.getText().toString())
                .dateCode(dateCode)
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


    /*CalPicker callback*/
    @Override
    public void onPick(int dc) {
        dateCode = dc;
        holder.dateCodeText.setDateCode(dc);
    }
}
