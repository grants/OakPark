package me.laudoak.oakpark;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by LaudOak on 2015-10-8 at 20:46.
 */
public class OPlication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        initFresco();
        initImageLoderConfiguration(getImageLoderDisplayOptions());

    }

    private DisplayImageOptions getImageLoderDisplayOptions()
    {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        return defaultOptions;

    }

    private void initImageLoderConfiguration(DisplayImageOptions def)
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(def)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void initFresco() {

        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, imagePipelineConfig);

    }
}
