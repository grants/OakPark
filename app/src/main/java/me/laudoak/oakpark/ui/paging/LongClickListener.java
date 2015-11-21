package me.laudoak.oakpark.ui.paging;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by LaudOak on 2015-11-7 at 13:40.
 */
public class LongClickListener implements AdapterView.OnItemLongClickListener {

    public LongClickListener()
    {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
