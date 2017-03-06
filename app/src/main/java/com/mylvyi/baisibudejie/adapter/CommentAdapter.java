package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.CommentBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/8.
 */
public class CommentAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    List<CommentBean.NormalBean.ListBean> data;
    Context context;
    public static final String TAG = "testVoice";

    public CommentAdapter(List<CommentBean.NormalBean.ListBean> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.e(TAG, "getCount: "+ data.size());
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView =inflater.inflate(R.layout.comment_item_layout, parent, false);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (Holder) convertView.getTag();
        }
        CommentBean.NormalBean.ListBean bean = data.get(position);
        showComments(holder, bean);

        return convertView;
    }

    private void showComments(final Holder holder, final CommentBean.NormalBean.ListBean bean) {
        holder.tvZan.setText(bean.getLike_count()+"");
        holder.sdvUserHead.setImageURI(Uri.parse(bean.getUser().getProfile_image()));
        holder.tvUserName.setText(bean.getUser().getUsername());
        holder.tvUserTotalCommentLike.setText(bean.getUser().getTotal_cmt_like_count());
        holder.tvCommentText.setText(bean.getContent());
        if(!"m".equals(bean.getUser().getSex())){
            holder.tvUserSex.setBackgroundResource(R.mipmap.sex_women);
        }
        holder.cbZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bean.setIsZan(true);notifyDataSetChanged();
            }
        });
        if(bean.getIsZan()){
            holder.tvZan.setTextColor(context.getResources().getColor(R.color.top_color));
            holder.tvZan.setEnabled(false);
            holder.cbZan.setChecked(true);
            holder.cbZan.setClickable(false);
        }
    }

    static class Holder {
        @Bind(R.id.sdvUserHead)
        SimpleDraweeView sdvUserHead;
        @Bind(R.id.tvUserSex)
        TextView tvUserSex;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.tvZan)
        TextView tvZan;
        @Bind(R.id.cbZan)
        CheckBox cbZan;
        @Bind(R.id.levelA)
        LinearLayout levelA;
        @Bind(R.id.tvUserTotalCommentLike)
        TextView tvUserTotalCommentLike;
        @Bind(R.id.tvCommentText)
        TextView tvCommentText;
        @Bind(R.id.tvCommentImage)
        SimpleDraweeView tvCommentImage;
        @Bind(R.id.levelB)
        LinearLayout levelB;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
