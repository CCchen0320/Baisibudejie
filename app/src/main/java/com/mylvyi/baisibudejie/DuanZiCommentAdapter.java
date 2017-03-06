package com.mylvyi.baisibudejie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.bean.PingLunBean;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 11/08 0008.
 */
public class DuanZiCommentAdapter extends BaseAdapter {
    private static final String TAG = "test";
    Context context;
    List<PingLunBean.NormalBean.ListBean> data;

    public DuanZiCommentAdapter(Context context, List<PingLunBean.NormalBean.ListBean> data) {
        this.context = context;
        this.data = data;
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
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.duanzipinglun_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Log.e(TAG, "getView: " + data.size());
        PingLunBean.NormalBean.ListBean listBean = data.get(position);
        holder.sdv1.setImageURI(listBean.getUser().getProfile_image());
        holder.tvName.setText(listBean.getUser().getUsername());
        holder.likeCountTv.setText(Integer.parseInt(listBean.getUser().getTotal_cmt_like_count()) / 1000 >= 1 ? new DecimalFormat("0.00").format(Integer.parseInt(listBean.getUser().getTotal_cmt_like_count()) / 1000.00) + "k" : listBean.getUser().getTotal_cmt_like_count());
        holder.content.setText(listBean.getContent());
        return convertView;
    }


    static class Holder {
        @Bind(R.id.sdv1)
        SimpleDraweeView sdv1;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.u_pinlun_iv)
        CheckBox uPinlunIv;
        @Bind(R.id.u_like_count_tv)
        TextView uLikeCountTv;
        @Bind(R.id.like_count_tv)
        TextView likeCountTv;
        @Bind(R.id.content)
        TextView content;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
