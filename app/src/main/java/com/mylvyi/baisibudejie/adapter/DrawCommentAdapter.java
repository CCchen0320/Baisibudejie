package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.PingLunBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/7.
 */
public class DrawCommentAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private List<PingLunBean.NormalBean.ListBean> data;

    public DrawCommentAdapter(Context context, List<PingLunBean.NormalBean.ListBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        PingLunBean.NormalBean.ListBean listBean = data.get(position);
        holder.profileSdv.setImageURI(listBean.getUser().getProfile_image());
        holder.totalikeTv.setText(listBean.getUser().getTotal_cmt_like_count());
        if("m".equals(listBean.getUser().getSex())) {
            holder.sexIv.setImageResource(R.mipmap.sex_men);
        }else{
            holder.sexIv.setImageResource(R.mipmap.sex_women);
        }
        holder.username.setText(listBean.getUser().getUsername());
        holder.likeCountTv.setText(listBean.getLike_count()+"");
        if(holder.pinlunzanIv.isChecked()){
            holder.likeCountTv.setText(listBean.getLike_count()+1+"");
            holder.pinlunzanIv.setEnabled(false);
        }

        if(listBean.getPrecmts().size()>0 && listBean.getPrecmts()!=null){
            holder.huifuRll.setVisibility(View.VISIBLE);
            holder.usertextTv.setVisibility(View.GONE);
            holder.floorTv.setText(listBean.getPrecmts().get(0).getFloor()+" ");
            holder.unameTv.setText(listBean.getPrecmts().get(0).getUser().getUsername());
            holder.uLikeCountTv.setText(listBean.getPrecmts().get(0).getLike_count()+" ");
            if(holder.uPinlunIv.isChecked()){
                holder.uLikeCountTv.setText(listBean.getPrecmts().get(0).getLike_count()+1+" ");
                holder.uPinlunIv.setEnabled(false);
            }
            holder.uTextTv.setText(listBean.getPrecmts().get(0).getContent());
            holder.utextTv.setText(listBean.getContent());
        }else{
            holder.huifuRll.setVisibility(View.GONE);
            holder.usertextTv.setVisibility(View.VISIBLE);
            holder.usertextTv.setText(listBean.getContent());
        }


        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.profile_sdv)
        SimpleDraweeView profileSdv;
        @Bind(R.id.totalike_tv)
        TextView totalikeTv;
        @Bind(R.id.sex_iv)
        ImageView sexIv;
        @Bind(R.id.username)
        TextView username;
        @Bind(R.id.pinlunzan_iv)
        CheckBox pinlunzanIv;
        @Bind(R.id.like_count_tv)
        TextView likeCountTv;
        @Bind(R.id.floor_tv)
        TextView floorTv;
        @Bind(R.id.uname_tv)
        TextView unameTv;
        @Bind(R.id.u_pinlun_iv)
        CheckBox uPinlunIv;
        @Bind(R.id.u_like_count_tv)
        TextView uLikeCountTv;
        @Bind(R.id.u_text_tv)
        TextView uTextTv;
        @Bind(R.id.utext_tv)
        TextView utextTv;
        @Bind(R.id.huifu_rll)
        RelativeLayout huifuRll;
        @Bind(R.id.usertext_tv)
        TextView usertextTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
