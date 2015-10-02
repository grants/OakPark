package me.nereo.multi_image_selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends FragmentActivity implements MultiImageSelectorFragment.Callback{

    private static final String TAG = "MultiImageSelectorActivity";

    public static final String EXTRA_CROPPER_RESULT = "EXTRA_CROPPER_RESULT_RETURN_TO_REQUEST";

    private static final int REQUEST_CROPPER = 10010;

    private int mCropMode;

    /***/
    private static final int DEFAULT_COUNT = 1;
    private static final int DEFAULT_MODE = 0;
    private static final boolean DEFAULT_CAMERA = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        initView();

        /***/
        Intent intent = getIntent();
        mCropMode = intent.getIntExtra(CropperActivity.EXTRA_CROP_MODE,CropperActivity.CROP_MODE_NORMAL);

        /***/
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, DEFAULT_COUNT);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, DEFAULT_MODE);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, DEFAULT_CAMERA);

        /***/
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();

    }

    private void initView() {

        findViewById(R.id.custom_actionbar_done).setVisibility(View.GONE);

        TextView title = (TextView) findViewById(R.id.custom_actionbar_title);
        title.setText("选择图片");

        /***/
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    /***/
    @Override
    public void onSingleImageSelected(String path) {

        if(null!=path)
        {
            Intent intent = new Intent();
            intent.setClass(this,CropperActivity.class);
            intent.putExtra(CropperActivity.EXTRA_CROP_MODE,mCropMode);
            intent.putExtra(CropperActivity.FROM_BITMAP_PATH, path);
            startActivityForResult(intent, REQUEST_CROPPER);
        }
        //this.finish();

    }

    @Override
    public void onCameraShot(File imageFile) {

        if(null!=imageFile)
        {
            Intent intent = new Intent();
            intent.setClass(this,CropperActivity.class);
            intent.putExtra(CropperActivity.EXTRA_CROP_MODE, mCropMode);
            intent.putExtra(CropperActivity.FROM_BITMAP_PATH, imageFile.getAbsolutePath());
            startActivityForResult(intent, REQUEST_CROPPER);
        }
        //this.finish();

    }

    @Override
    public void onImageSelected(String path) {
        //nothing to do
    }

    @Override
    public void onImageUnselected(String path) {
        //nothing to do
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(null==data||resultCode!= Activity.RESULT_OK)
        {
            Log.d(TAG,"null==data||resultCode!= Activity.RESULT_OK");
            return ;
        }

        if(requestCode == REQUEST_CROPPER)
        {
            String result = data.getStringExtra(CropperActivity.RETURN_BITMAP_PATH);
            Log.d(TAG,"requestCode == REQUEST_CROPPER"+"result"+result);

            if(null!=result)
            {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CROPPER_RESULT,result);
                setResult(RESULT_OK,intent);
            }
        }

        finish();

    }
}
