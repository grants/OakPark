package me.laudoak.oakpark.ui.settinglv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import me.laudoak.oakpark.ui.settinglv.fill.AbFilling;
import me.laudoak.oakpark.ui.settinglv.fill.NormalFill;
import me.laudoak.oakpark.ui.settinglv.fill.ToggleFill;

/**
 * Created by LaudOak on 2015-11-30 at 23:18.
 */
public class SettingLvAdapter extends BaseAdapter
{

    private static final String TAG = "SettingLvAdapter";

    private ViewHolderPool holderPool;
    private List<AbFilling> fillings;


    public SettingLvAdapter(Context context)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        fillings = new ArrayList<AbFilling>();
        genFillings();

        holderPool = new ViewHolderPool(inflater,fillings);
    }

    private void genFillings()
    {
        fillings.add(0,new NormalFill("个人信息"));
        fillings.add(1,new ToggleFill("使用系统字体",false));
        fillings.add(2,new NormalFill("开源协议"));
        fillings.add(3,new NormalFill("有关诗歌的网站"));
        fillings.add(4,new NormalFill("版本更新"));
        fillings.add(5,new NormalFill("关于"));

    }

    /**necessarily method*/
    @Override
    public int getCount()
    {
        return fillings.size();
    }

    @Override
    public Object getItem(int position)
    {
        return fillings.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return holderPool.getView(position,convertView,parent);
    }
    /***/


    /**ListView with multi layout item*/

    @Override
    public int getViewTypeCount()
    {
        return holderPool.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        return holderPool.getItemViewType(position);
    }
    /***/
}
