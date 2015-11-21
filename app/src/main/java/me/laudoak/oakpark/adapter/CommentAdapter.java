package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.ui.clocktext.ClockText;

/**
 * Created by LaudOak on 2015-10-23 at 10:52.
 */
public class CommentAdapter extends XBaseAdapter<Comment> {


    public CommentAdapter(List dataList, Context context) {
        super(dataList, context);
    }


    @Override
    View callView(int position, View convertView, ViewGroup parent) {

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
        SimpleDraweeView avatar;
        TextView nick;
        ClockText commentTime;
        TextView commentContent;

        public ViewHolder(View v)
        {
            avatar = (SimpleDraweeView) v.findViewById(R.id.item_comment_avatar);
            nick = (TextView) v.findViewById(R.id.item_comment_nick);
            commentTime = (ClockText) v.findViewById(R.id.item_comment_time);
            commentContent = (TextView) v.findViewById(R.id.item_comment_content);
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
