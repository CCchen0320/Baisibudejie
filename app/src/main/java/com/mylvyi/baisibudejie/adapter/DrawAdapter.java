package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mylvyi.baisibudejie.DrawActivity;
import com.mylvyi.baisibudejie.DrawCommentActivity;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.DrawBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/10/31.
 */
public class DrawAdapter extends BaseAdapter {
    private final LayoutInflater inflater;

    private Context context;
    private List<DrawBean.ListBean> data;
    private DrawBean.ListBean listBean;


    public DrawAdapter(Context context, List<DrawBean.ListBean> data) {
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
            convertView = inflater.inflate(R.layout.draw_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        listBean = data.get(position);


        holder.headerSdv.setImageURI(listBean.getU().getHeader().get(0));
        holder.titleTv.setText(listBean.getText());
        if ("image".equals(listBean.getType())) {
            holder.lookTv.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = holder.downloadSdv.getLayoutParams();//得到控件大小
            layoutParams.height = 500 * 3;//设置高度
            holder.downloadSdv.setScaleType(ImageView.ScaleType.MATRIX);
            //图片可能为空
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(listBean.getImage().getBig().get(0)))//url
                    .setResizeOptions(new ResizeOptions(1000, 1000))//图像质量，可以缩小大图片体积，提升UI的流畅程度
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(listBean.getImage().getThumbnail_small().get(0)))
                    .setImageRequest(request)
                    .setOldController(holder.downloadSdv.getController())
                    .build();
            holder.downloadSdv.setEnabled(true);
            holder.downloadSdv.setController(draweeController);
            Log.e("test", "getView: " + listBean.getImage().getBig().get(0));
            Log.e("ggg", "position: " + position + "user:" + listBean.getU().getName() + ",getImage:" + listBean.getImage().getBig().get(0));
            holder.downloadSdv.setLayoutParams(layoutParams);
        } else {
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(listBean.getGif().getImages().get(0)))
                    .setAutoPlayAnimations(true)
                    .build();
            holder.lookTv.setVisibility(View.GONE);
            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = systemService.getDefaultDisplay().getWidth() / listBean.getGif().getWidth();
            ViewGroup.LayoutParams layoutParams = holder.downloadSdv.getLayoutParams();
            Log.e("dddd", "getView: " + width);
            layoutParams.height = listBean.getGif().getHeight() * width;
            holder.downloadSdv.setEnabled(false);
            holder.downloadSdv.setImageURI(listBean.getGif().getGif_thumbnail().get(0));
            holder.downloadSdv.setController(draweeController);
            Log.e("ggg", "position: " + position + "user:" + listBean.getU().getName() + ",getImage:" + listBean.getGif().getImages().get(0));
            holder.downloadSdv.setLayoutParams(layoutParams);
        }
        holder.nameTv.setText(listBean.getU().getName());
        if (!listBean.getU().isIs_v()) {
            holder.headerVipIv.setVisibility(View.GONE);
        }
        if (!listBean.getU().isIs_vip()) {
            holder.isVipIv.setVisibility(View.GONE);
        }
        holder.passtimeTv.setText(listBean.getPasstime());
        holder.upCb.setText(listBean.getUp());
        holder.dowmCb.setText(listBean.getDown() + " ");
        holder.forwardTv.setText(listBean.getForward() + "");
        holder.commentTv.setText(listBean.getComment() + "");
        if (listBean.getTop_comments() != null && listBean.getTop_comments().size() > 0) {
            String names = null;
            for (int i = 0; i < listBean.getTop_comments().size(); i++) {
                holder.topCommentsTv.setVisibility(View.VISIBLE);
                String name = listBean.getTop_comments().get(i).getU().getName();
                String text = listBean.getTop_comments().get(i).getContent();
                names += name + ": " + text + "\n";
                holder.topCommentsTv.setText(names);
                Log.e("test", "评论" + names);
            }
            names = null;
        } else {
            holder.topCommentsTv.setVisibility(View.INVISIBLE);
            holder.topCommentsTv.setText("暂时没有人评论");
            Log.e("test", "评论无");
        }
        //点击进入大图
        Lookonclick(holder, position);
        //进入评论
        initComment(holder, position);
        //分享
        initShare(holder, position);
        return convertView;
    }

    private void Lookonclick(ViewHolder holder, final int position) {
        //点击图片进入大图
        holder.lookTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initIntent(position);
            }
        });
        holder.downloadSdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initIntent(position);
            }
        });
    }

    private void initIntent(int position) {
        Intent intent = new Intent(context, DrawActivity.class);
        Bundle bundle = new Bundle();
        DrawBean.ListBean bean = data.get(position);
        try {
            bundle.putString("BigDraw", bean.getImage().getBig().get(0));
            bundle.putString("DownDraw", bean.getImage().getDownload_url().get(0));
            bundle.putString("Text", bean.getText());
            bundle.putString("Image", bean.getImage().getThumbnail_small().get(0));
            bundle.putString("Uri", bean.getShare_url());
            bundle.putString("CommentId",bean.getId());
            if("image".equals(bean.getType())){
                bundle.putInt("Height",bean.getImage().getHeight());
                bundle.putInt("Width",bean.getImage().getWidth());
            }
            Log.e("ggg", "bundle.putString:position= " + position + "," + bean.getU().getName() + ",putString:" + bean.getImage().getBig().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.putExtras(bundle);
        Toast.makeText(context, "进入详情页", Toast.LENGTH_SHORT).show();
        Log.e("ggg", "onlookClicl: ");
        context.startActivity(intent);
    }

    //评论
    public void initComment(ViewHolder holder, final int position) {
        Log.e("llllll", "initComm");
        holder.commentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DrawCommentActivity.class);
                    DrawBean.ListBean bean = data.get(position);
                    intent.putExtra("commentId", bean.getId());
                    Log.e("gggggg", "initComment: " + bean.getId());
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //分享监听
    public void initShare(ViewHolder holder, final int position) {
        holder.shareLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawBean.ListBean bean = data.get(position);
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle(bean.getText());
                // titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl(bean.getShare_url());
                // text是分享文本，所有平台都需要这个字段
                oks.setText(bean.getText());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                oks.setImageUrl(bean.getImage().getThumbnail_small().get(0));
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl(bean.getShare_url());
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment(bean.getText());
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(context.getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(bean.getShare_url());
                // 启动分享GUI
                oks.show(context);

            }
        });
    }


    static class ViewHolder {
        @Bind(R.id.header_sdv)
        SimpleDraweeView headerSdv;
        @Bind(R.id.header_vip_iv)
        ImageView headerVipIv;
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.passtime_tv)
        TextView passtimeTv;
        @Bind(R.id.is_vip_iv)
        ImageView isVipIv;
        @Bind(R.id.title_tv)
        TextView titleTv;
        @Bind(R.id.download_sdv)
        SimpleDraweeView downloadSdv;
        @Bind(R.id.look_tv)
        TextView lookTv;
        @Bind(R.id.up_cb)
        CheckBox upCb;
        @Bind(R.id.dowm_cb)
        CheckBox dowmCb;
        @Bind(R.id.forward_tv)
        TextView forwardTv;
        @Bind(R.id.comment_tv)
        TextView commentTv;
        @Bind(R.id.top_comments_tv)
        TextView topCommentsTv;
        @Bind(R.id.comment_ll)
        LinearLayout commentLl;
        @Bind(R.id.share_ll)
        LinearLayout shareLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
