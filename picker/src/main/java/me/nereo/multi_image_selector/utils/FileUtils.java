package me.nereo.multi_image_selector.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.nereo.multi_image_selector.CropperActivity;

/**
 * 文件操作类
 * Created by Nereo on 2015/4/8.
 */
public class FileUtils {

    private static final String TAG = "Picker.FileUtils";

    public static File createTmpFile(Context context){

        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_"+timeStamp+"";
            File tmpFile = new File(pic, fileName+".jpg");
            return tmpFile;
        }else{
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_"+timeStamp+"";
            File tmpFile = new File(cacheDir, fileName+".jpg");
            return tmpFile;
        }
    }


    /***/
    private static File getExternalCacheDir(Context context,String dirName)
    {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File cacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        File appCacheDir = new File(cacheDir, dirName);
        if (!appCacheDir.exists())
        {
            if (!appCacheDir.mkdirs())
            {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e)
            {

            }
        }

        return appCacheDir;

    }

    public static String saveImageToCache(Context context,Bitmap bitmap,int cropMode) {

        String imageName = null;

        switch (cropMode)
        {
            case CropperActivity.CROP_MODE_AVATAR:
            {
                imageName = "avatar"+"_"+TimeUtils.getNowDateToString()+"_"+"avatar";
                break;
            }
            case CropperActivity.CROP_MODE_NORMAL:
            {
                imageName = "normal"+"_"+TimeUtils.getNowDateToString();
                break;
            }
            default:
            {
                imageName = TimeUtils.getNowDateToString();
            }
        }

        String result = getExternalCacheDir(context, imageName)+".jpg";
        Log.d(TAG,"image file path:"+result);

        try {

            FileOutputStream fos = new FileOutputStream(result);

            //public boolean compress (Bitmap.CompressFormat format, int quality, OutputStream stream)
            //format:JPEG|PNG|WEBP
            //quality:Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
            if(bitmap.compress(Bitmap.CompressFormat.JPEG,55,fos))
            {
                try {
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
