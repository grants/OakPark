package me.laudoak.oakpark.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.laudoak.oakpark.R;
import me.laudoak.oakpark.entity.Comment;
import me.laudoak.oakpark.widget.clocktext.ClockText;
import me.laudoak.oakpark.widget.paging.XBasePagingAdapter;

/**
 * Created by LaudOak on 2015-10-23 at 10:52.
 */
public class CommentAdapter extends XBasePagingAdapter<Comment> {


    private LayoutInflater inflater;

    public CommentAdapter(Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
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

            if (null!=cm.getPoet().getAvatarURL())
            {
                Uri uri = Uri.parse(cm.getPoet().getAvatarURL());
                avatar.setImageURI(uri);
            }

        }

    }
}
