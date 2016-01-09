package me.laudoak.oakpark.ui.settinglv.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import me.laudoak.oakpark.PreferConstants;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;
import me.laudoak.oakpark.ui.swbutton.SwitchButton;

/**
 * Created by LaudOak on 2015-11-30 at 23:15.
 */
public class ToggleViewHolder extends AbViewHolder {

    private TextView title;
    private SwitchButton switchButton;

    public ToggleViewHolder(Context context ,View view)
    {
        super(context ,view);

        title = (TextView) view.findViewById(R.id.item_stv_toggle_tv);
        switchButton = (SwitchButton) view.findViewById(R.id.item_stv_toggle);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useDefaultFont = preferences.getBoolean(PreferConstants.USE_DEFAULT_FONT,true);

        switchButton.setChecked(useDefaultFont);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                compoundButton.setChecked(b);
                preferences.edit().putBoolean(PreferConstants.USE_DEFAULT_FONT,b).apply();
            }
        });
    }

    @Override
    public void bind(AbFilling filling)
    {
        title.setText(filling.getTitle());
    }
}
