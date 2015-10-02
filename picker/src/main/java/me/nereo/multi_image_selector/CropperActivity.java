package me.nereo.multi_image_selector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;

import me.nereo.multi_image_selector.utils.FileUtils;

/**
 * Created by LaudOak on 2015-10-1.
 */
public class CropperActivity extends FragmentActivity {

    public static final String FROM_BITMAP_PATH = "image path from activity.";
    public static final String RETURN_BITMAP_PATH = "image path return activity.";

    /***/
    public static final String EXTRA_CROP_MODE = "crop_mode";
    public static final int CROP_MODE_NORMAL = 101;
    public static final int CROP_MODE_AVATAR = 102;

    /***/
    private CropImageView cropImageView ;
    private int mode;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);

        /***/
        Intent intent = getIntent();
        path = intent.getStringExtra(FROM_BITMAP_PATH);
        mode = intent.getIntExtra(EXTRA_CROP_MODE,CROP_MODE_NORMAL);

        /***/
        initView();

    }

    private void initView() {

        cropImageView = (CropImageView) findViewById(R.id.cropper_image);

        switch (mode)
        {
            case CROP_MODE_NORMAL:
            {
                cropImageView.setAspectRatio(5,3);
                break;
            }
            case CROP_MODE_AVATAR:
            {
                cropImageView.setAspectRatio(1,1);
                break;
            }
        }

        Bitmap bitmap = getBitmap(path);
        cropImageView.setImageBitmap(bitmap);

        /***/
        TextView title = (TextView) findViewById(R.id.custom_actionbar_title);
        title.setText("裁剪图片");

        /***/
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.custom_actionbar_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCrop();
            }
        });

    }

    private Bitmap getBitmap(String src)
    {
        Bitmap result = BitmapFactory.decodeFile(src);
        return result;
    }

    private void doCrop()
    {
        Bitmap bitmap = cropImageView.getCroppedImage();
        String result = FileUtils.saveImageToCache(getApplicationContext(), bitmap, mode);

        Intent intent = new Intent();
        intent.putExtra(RETURN_BITMAP_PATH,result);
        setResult(RESULT_OK, intent);

        this.finish();
    }
}
