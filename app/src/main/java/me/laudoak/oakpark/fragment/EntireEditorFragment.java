package me.laudoak.oakpark.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.EditorActivity;
import me.laudoak.oakpark.net.push.XVersePush;
import me.laudoak.oakpark.view.EntireEditorView;
import me.laudoak.oakpark.widget.panel.XBasePanelView;
import me.nereo.multi_image_selector.CropperActivity;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by LaudOak on 2015-9-29.
 */
public class EntireEditorFragment extends XBaseFragment implements
        EditorActivity.PushCallback,
        XBasePanelView.OnPanelClickListener,
        XVersePush.PushCallBack {

    private static final String TAG = "EntireEditorFragment";

    private static final int REQUEST_PICKER = 1008611;

    private Context context;
    private FragmentManager fragmentManager;
    private EntireEditorView entireEditorView;
    private EntireEditorView.Holder holder;

    private String imagePath;

    /*XBaseFragment callback*/
    @Override
    public void initData() {
        this.context = getActivity();
        fragmentManager = getFragmentManager();
        entireEditorView = new EntireEditorView(context);
        this.holder = entireEditorView.getHolder();
        imagePath = null;
    }

    /*XBaseFragment callback*/
    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return entireEditorView;
    }

    /*XBaseFragment callback*/
    @Override
    public void buildViews(View view) {

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
                break;
            }
        }
    }

    /*EditorActivity callback*/
    @Override
    public void onPush() {

    }

    /*push callback*/
    @Override
    public void onSuccess() {

    }

    /*push callback*/
    @Override
    public void onFailure(String reason) {

    }

    /*Get bitmap path*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*handle crop result*/
        if(null==data||resultCode!= Activity.RESULT_OK)
        {
            //!!!
            Log.d(TAG, "onActivityResult:null==data||resultCode!= Activity.RESULT_OK");
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


}
