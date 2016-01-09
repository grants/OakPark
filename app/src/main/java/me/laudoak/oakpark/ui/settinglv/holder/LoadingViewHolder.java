package me.laudoak.oakpark.ui.settinglv.holder;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;
import me.laudoak.oakpark.ui.settinglv.fill.LoadingFill;

/**
 * Created by LaudOak on 2015-11-30 at 23:15.
 */
public class LoadingViewHolder extends AbViewHolder{

    private TextView title;
    private ProgressBar progressBar;


    public LoadingViewHolder(Context context ,View view) {
        super(context,view);
        title = (TextView) view.findViewById(R.id.item_stv_load_tv);
        progressBar = (ProgressBar) view.findViewById(R.id.item_stv_load);
    }

    @Override
    public void bind(AbFilling filling) {
        title.setText(filling.getTitle());
        if (((LoadingFill) filling).isLoading())
        {
            progressBar.setVisibility(View.VISIBLE);
        }else
        {
            progressBar.setVisibility(View.GONE);
        }
    }
}
