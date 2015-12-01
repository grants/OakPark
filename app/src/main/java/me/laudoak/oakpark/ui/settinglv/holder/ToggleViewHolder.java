package me.laudoak.oakpark.ui.settinglv.holder;

import android.view.View;
import android.widget.TextView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;
import me.laudoak.oakpark.ui.settinglv.fill.ToggleFill;
import me.laudoak.oakpark.ui.swbutton.SwitchButton;

/**
 * Created by LaudOak on 2015-11-30 at 23:15.
 */
public class ToggleViewHolder extends AbViewHolder {

    private TextView title;
    private SwitchButton switchButton;

    public ToggleViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.item_stv_toggle_tv);
        switchButton = (SwitchButton) view.findViewById(R.id.item_stv_toggle);
    }

    @Override
    public void bind(AbFilling filling) {
        title.setText(filling.getTitle());
        switchButton.setChecked(((ToggleFill)filling).isSelected());
    }
}
