package me.laudoak.oakpark.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by LaudOak on 2015-11-7 at 15:41.
 */
public class FileUtil {

    public static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();

        File oakDir = new File(root + "/OakPark");

        if (!oakDir.exists())
        {
            oakDir.mkdirs();
        }

        String timaStamp = TimeUtil.genFileName();
        String fname = "verse_"+ timaStamp +".jpg";
        File file = new File (oakDir, fname);

        if (file.exists ()) file.delete ();

        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String saveImageToExternalStorage(Context context,Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File oakDir = new File(root + "/OakPark");

        if (!oakDir.exists())
        {
            oakDir.mkdirs();
        }

        String timaStamp = TimeUtil.genFileName();
        String fname = "verse_" + timaStamp + ".jpg";


        File destFile = new File(oakDir, fname);

        if (destFile.exists())
            destFile.delete();
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(context, new String[]{destFile.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

        return destFile.getAbsolutePath();
    }

    public static String readTxtFromAssets(Context context, String target) throws IOException {
        AssetManager as = context.getApplicationContext().getResources().getAssets();

        StringBuilder sb = new StringBuilder();

        InputStream is = null;
        BufferedReader br = null;
        try
        {
            is = as.open(target);
            br = new BufferedReader(new InputStreamReader(is));

            String s;
            while ((s = br.readLine()) != null)
            {
                sb.append(s).append("\n");
            }
        } finally
        {
            closeQuietly(br);
            closeQuietly(is);
        }

        return sb.toString();
    }

    private static void closeQuietly(Closeable closeable)
    {
        if (closeable == null)
        {
            return;
        }
        try
        {
            closeable.close();
        } catch (IOException ignore) {
            //Do nothing
        }
    }

}
