package me.laudoak.oakpark.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.activity.OakParkActivity;
import me.laudoak.oakpark.entity.XVerse;

/**
 * Created by LaudOak on 2015-10-22 at 20:32.
 */
public class SUPWhisperFragment extends XBaseFragment implements OakParkActivity.NXVUCallback{
    private static final String TAG = "SUPWhisperFragment";

    private TextView whisper;
    private SimpleDraweeView avatar;
    private TextView nick;

    public static SUPWhisperFragment newInstance()
    {
        SUPWhisperFragment fragment = new SUPWhisperFragment();

        return fragment;
    }

    /*first*/

    @Override
    public void initData()
    {

    }

    /*second*/
    @Override
    public View callView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.view_sup_whisper,null);
    }

    /*third*/

    @Override
    public void buildViews(View view) {

        whisper = (TextView) view.findViewById(R.id.sup_whisper_whisper);
        avatar = (SimpleDraweeView) view.findViewById(R.id.sup_whisper_avatar);
        nick = (TextView) view.findViewById(R.id.sup_whisper_nick);
    }

    /*fourth*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onUpdateXV(XVerse xv) {
        whisper.setText(xv.getWhisper());
        nick.setText(xv.getPoet().getUsername());

        if (null != xv.getPoet().getAvatarURL())
        {
            Uri uri = Uri.parse(xv.getPoet().getAvatarURL());
            avatar.setImageURI(uri);
        }
    }
}
