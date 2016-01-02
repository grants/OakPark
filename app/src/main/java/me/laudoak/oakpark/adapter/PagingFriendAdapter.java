package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.core.Verse;
import me.laudoak.oakpark.ui.fittext.ExpandableLinear;
import me.laudoak.oakpark.ui.paging.XBasePagingAdapter;
import me.laudoak.oakpark.view.AvatarView;

/**
 * Created by LaudOak on 2016-1-1 at 22:10.
 */
public class PagingFriendAdapter extends XBasePagingAdapter<Verse>
{

    private LayoutInflater inflater;

    public PagingFriendAdapter(Context context)
    {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return datas.size();
    }

    @Override
    public Object getItem(int i)
    {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (null==convertView)
        {
            convertView = inflater.inflate(R.layout.view_item_friend,parent,false);
            holder = new ViewHolder(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != holder)
        {
            holder.bindData(datas.get(position));
        }

        return convertView;
    }

    public class ViewHolder
    {
        @Bind(R.id.item_comment_avatar) AvatarView avatar;
        @Bind(R.id.item_overse_image) SimpleDraweeView image;
        @Bind(R.id.item_overse_title) TextView title;
        @Bind(R.id.item_overse_author) TextView author;
        @Bind(R.id.item_verse_linear_expand) ExpandableLinear expandVerse;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this,view);
            view.setTag(this);
        }

        void bindData(Verse verse)
        {

            avatar.setPoet(verse.getPoet());
            title.setText(verse.getTitle());
            author.setText(verse.getAuthor());
            expandVerse.setText(verse.getVerse());

            if (null !=verse.getPoet().getAvatarURL())
            {
                Uri uri = Uri.parse(verse.getPoet().getAvatarURL());
                avatar.setImageURI(uri);
            }

            if (null != verse.getImageURL())
            {
                Uri uri = Uri.parse(verse.getImageURL());
                image.setAspectRatio(1.67f);
                image.setImageURI(uri);
            }
        }
    }

}
