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
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.ui.clocktext.ClockText;
import me.laudoak.oakpark.ui.paging.XBasePagingAdapter;

/**
 * Created by LaudOak on 2015-12-4 at 22:58.
 */
public class PagingComAdapter extends XBasePagingAdapter<Comment> {

    private static final String TAG = PagingComAdapter.class.getName();

    private LayoutInflater inflater;


    public PagingComAdapter(Context context)
    {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (null==convertView)
        {
            convertView = inflater.inflate(R.layout.view_item_comment,parent,false);
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

    public static class ViewHolder
    {

        @Bind(R.id.item_comment_avatar) SimpleDraweeView avatar;
        @Bind(R.id.item_comment_nick) TextView nick;
        @Bind(R.id.item_comment_time) ClockText commentTime;
        @Bind(R.id.item_comment_content) TextView commentContent;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this,view);
        }

        void bindData(Comment cm)
        {
            commentTime.setPushTime(cm.getCommentTime());
            nick.setText(cm.getPoet().getUsername());
            commentTime.setText(cm.getContent());
            commentContent.setText(cm.getContent());

            if (null!=cm.getPoet().getAvatarURL())
            {
                Uri uri = Uri.parse(cm.getPoet().getAvatarURL());
                avatar.setImageURI(uri);
            }

        }

    }

}