package me.laudoak.oakpark.adapter.vholder;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.ctrl.listener.XVFontListener;
import me.laudoak.oakpark.entity.core.XVerse;
import me.laudoak.oakpark.ui.fittext.AutofitTextView;
import me.laudoak.oakpark.utils.font.FontsHelper;
import me.laudoak.oakpark.utils.font.FontsManager;

/**
 * Created by LaudOak on 2016-1-9 at 16:44.
 */
public class XVerseViewHolder extends RecyclerView.ViewHolder
{

    private Context context;
    private View mainView;

    public SimpleDraweeView image;
    public AutofitTextView title;
    public AutofitTextView author;
    public TextView verse;

    boolean useDefaultFont ;

    public XVerseViewHolder(Context context,View view)
    {
        super(view);
        this.context = context;
        this.mainView = view;
        this.useDefaultFont = new XVFontListener(context).useDefaultFont();

        if (!useDefaultFont)
        {
            FontsManager.init(FontsHelper.fang(context)); //初始化
            FontsManager.changeFonts(view);
        }

        image = (SimpleDraweeView) view.findViewById(R.id.item_xverse_image);
        title = (AutofitTextView) view.findViewById(R.id.item_xverse_title);
        author = (AutofitTextView) view.findViewById(R.id.item_xverse_author);
        verse = (TextView) view.findViewById(R.id.item_xverse_verse);
    }

    public void bindData(XVerse vs)
    {
        this.title.setText(vs.getTitle());
        this.author.setText(vs.getAuthor());
        this.verse.setText(vs.getVerse());

        if (null != vs.getImageURL())
        {
            Uri uri = Uri.parse(vs.getImageURL());
            this.image.setAspectRatio(1.67f);
            this.image.setImageURI(uri);
        }
    }

}
