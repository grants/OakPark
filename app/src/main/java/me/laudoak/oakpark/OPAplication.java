package me.laudoak.oakpark;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

/**
 * Created by LaudOak on 2015-10-8 at 20:46.
 */
public class OPAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initFresco();

    }

    private void initFresco() {

        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, imagePipelineConfig);

    }
}
