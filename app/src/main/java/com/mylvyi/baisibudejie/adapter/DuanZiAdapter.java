package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.DuanZiCommentActivity;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.DuanZi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 11/04 0004.
 */
public class DuanZiAdapter extends BaseAdapter {
    private static final String TAG = "lzy";
    Context context;
    List<DuanZi.ListBean> data;
    private DuanZi.ListBean listBean;

    public DuanZiAdapter(Context context, List<DuanZi.ListBean> data) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.duanzi_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        listBean = data.get(position);
        holder.sdv.setImageURI(listBean.getU().getHeader().get(0));
        if (listBean.getU().isIs_v()) {
            holder.ivV.setVisibility(View.VISIBLE);
        } else {
            holder.ivV.setVisibility(View.GONE);
        }
        if (listBean.getU().isIs_vip()) {
            holder.ivVIP.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(Color.RED);
        } else {
            holder.ivVIP.setVisibility(View.GONE);
            holder.tvName.setTextColor(Color.BLACK);
        }
        holder.tvName.setText(listBean.getU().getName());
        holder.tvDate.setText(listBean.getPasstime().substring(0, 16));
        holder.tvContent.setText(listBean.getText());
        holder.tvZan.setText(listBean.getUp());
        holder.tvCai.setText(listBean.getDown() + "");
        holder.tvZhuangFa.setText(listBean.getForward() + "");
        holder.tvPingLun.setText(listBean.getComment());
        String str = "";
        if (listBean.getTop_comments() != null) {
            for (int i = 0; i < listBean.getTop_comments().size(); i++) {
                str += listBean.getTop_comments().get(i).getU().getName() + "：" +
                        listBean.getTop_comments().get(i).getContent();
                if (i != listBean.getTop_comments().size() - 1) {
                    str += "\n\n";
                }
            }
        }
        if (listBean.getTop_comments() == null || listBean.getTop_comments().size() == 0) {
            holder.tvRePing.setVisibility(View.GONE);
        }
        holder.tvRePing.setText(str);


        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.sdv)
        SimpleDraweeView sdv;
        @Bind(R.id.ivV)
        ImageView ivV;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.ivVIP)
        ImageView ivVIP;
        @Bind(R.id.tvDate)
        TextView tvDate;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.ivZan)
        ImageView ivZan;
        @Bind(R.id.tvZan)
        TextView tvZan;
        @Bind(R.id.ivCai)
        ImageView ivCai;
        @Bind(R.id.tvCai)
        TextView tvCai;
        @Bind(R.id.ivZhuangFa)
        ImageView ivZhuangFa;
        @Bind(R.id.tvZhuangFa)
        TextView tvZhuangFa;
        @Bind(R.id.ivPinLun)
        ImageView ivPinLun;
        @Bind(R.id.tvPingLun)
        TextView tvPingLun;
        @Bind(R.id.tvRePing)
        TextView tvRePing;

        @OnClick({R.id.ivZan, R.id.ivCai, R.id.ivZhuangFa, R.id.ivPinLun})
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivZan:
                    Toast.makeText(context, "功能暂无", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ivCai:
                    Toast.makeText(context, "功能暂无", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ivZhuangFa:
                    Toast.makeText(context, "功能暂无", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ivPinLun:
                    Intent intent = new Intent(context, DuanZiCommentActivity.class);
                    intent.putExtra("id",listBean.getId());
                    context.startActivity(intent);
                    break;
            }
        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
