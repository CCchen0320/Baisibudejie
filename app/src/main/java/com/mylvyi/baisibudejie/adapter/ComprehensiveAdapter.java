package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mylvyi.baisibudejie.ComprehensiveCommentActivity;
import com.mylvyi.baisibudejie.DrawActivity;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.JHTuiJianBean;
import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by qf on 2016/11/3.
 */
public class ComprehensiveAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Handler handler;
    Context context;
    List<JHTuiJianBean.ListBean> data;
    private MediaPlayer mediaPlayer;
    private int currentPlayPosition = -1;
    private Runnable updataProgressCallback;
    public static final String TAG = "testv";

    public void updata(List<JHTuiJianBean.ListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public void resetCurrentPlayPosition(){
        currentPlayPosition=-1;
        notifyDataSetChanged();
    }

    public ComprehensiveAdapter(Handler handler, Context context, List<JHTuiJianBean.ListBean> data) {
        this.handler = handler;
        this.context = context;
        this.data = data;
        mediaPlayer = MediaUtil.getMediaPlayer();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comprehensive_item_layout, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final JHTuiJianBean.ListBean bean = data.get(position);
        JHTuiJianBean.ListBean.UBean uBean = bean.getU();
        final String type = bean.getType();
        setingShowType(holder, type);//根据类型显示对应的布局
        setBaseData(holder, bean, uBean);//获取基本信息（用户名，正文，评论，赞等）
        setingItemThumb(holder, bean, type,position);//加载内容布局的界面
        resetCurrentPlayPosition(position, holder);//离开屏幕停止播放，当前播放位置重新置为-1
        setUpOrDown(holder, bean); //点击赞或踩之后，radiogrounp设为不可点击
        setCheckBoxClickListener(holder, bean);//设置点赞checkbox点击事件监听
        setClickToShare(holder, bean);//设置分享按钮监听
        setImageShowAllClickListener(holder, bean);//设置查看大图按钮监听
        setTvCommentClickListener(holder, bean, type);//设置查看评论按钮监听
        setTvDetailClickListener(holder, bean, type);//设置描述文本点击监听
        return convertView;

    }
    //设置描述文本点击监听
    private void setTvDetailClickListener(Holder holder, final JHTuiJianBean.ListBean bean, final String type) {
        holder.tvDetil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnToCommentActivity(bean,type);
            }
        });
    }
    //设置查看评论按钮监听
    private void setTvCommentClickListener(Holder holder, final JHTuiJianBean.ListBean bean, final String type) {
        holder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnToCommentActivity(bean, type);
            }
        });
    }
    //跳到评论页面
    private void turnToCommentActivity(JHTuiJianBean.ListBean bean, String type) {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        currentPlayPosition=-1;
        Intent intent= new Intent(context, ComprehensiveCommentActivity.class);
        ;
        intent.putExtra("bean",bean);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    //设置查看大图按钮监听
    private void setImageShowAllClickListener(Holder holder, final JHTuiJianBean.ListBean bean) {
        holder.ivImageShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DrawActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("BigDraw", bean.getImage().getBig().get(0));
                    bundle.putString("DownDraw", bean.getImage().getDownload_url().get(0));
                    bundle.putString("Text",bean.getText());
                    bundle.putString("Image",bean.getImage().getThumbnail_small().get(0));
                    bundle.putString("Uri",bean.getShare_url());
                    if("image".equals(bean.getType())){
                        bundle.putInt("Height",bean.getImage().getHeight());
                        bundle.putInt("Width",bean.getImage().getWidth());
                    }
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //设置分享按钮监听
    private void setClickToShare(Holder holder, final JHTuiJianBean.ListBean bean) {
        holder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                oks.setImageUrl(bean.getShare_url());
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl(bean.getShare_url());
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("app测试");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(context.getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(bean.getShare_url());
                // 启动分享GUI
                oks.show(context);
            }
        });
    }
    //设置checkbox点击事件监听
    private void setCheckBoxClickListener(Holder holder, final JHTuiJianBean.ListBean bean) {
        holder.rbZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setIsZan(true);
                notifyDataSetChanged();
            }
        });

        holder.rbNozan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setIsCai(true);
                notifyDataSetChanged();
            }
        });
    }
    //点击赞或踩之后，radiogrounp设为不可点击
    private void setUpOrDown(final Holder holder, final JHTuiJianBean.ListBean bean) {
        if(bean.getIsZan()==true||bean.getIsCai()==true){
            if(bean.getIsZan()==true){
                holder.rbZan.setChecked(true);
                Log.e(TAG, "setUpOrDown_rbZan.setChecked: "+holder.rbZan.isChecked()+bean.getU().getName() );
                holder.rbZan.setEnabled(false);
                holder.rbNozan.setEnabled(false);
            }else{
                holder.rbNozan.setChecked(true);
                Log.e(TAG, "setUpOrDown_rbNozan.setChecked: "+holder.rbNozan.isChecked()+bean.getU().getName() );
                holder.rbZan.setEnabled(false);
                holder.rbNozan.setEnabled(false);
            }
        }else{
            holder.rbZan.setChecked(false);
            holder.rbNozan.setChecked(false);
        }
    }
    //离开屏幕停止播放，当前播放位置重新置为-1
    private void resetCurrentPlayPosition(int position, Holder holder) {
        Integer videoFormerPosition = (Integer) holder.svVideo.getTag();
        if (videoFormerPosition != null && position != currentPlayPosition && videoFormerPosition == currentPlayPosition) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            currentPlayPosition = -1;
        }
        holder.svVideo.setTag(position);
    }
    //视频类型进度条区域控件点击事件监听
    private void setVideoPlayOrPauseClickListener(final Holder holder, final String type) {
        holder.tvVideoPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                  mediaPlayer.pause();
                    holder.tvVideoPlayOrPause.setBackgroundResource(R.mipmap.video_play);
                    Log.e(TAG, "tvVideoPlayOrPause.setBackgroundResource: " );
                } else {
                    mediaPlayer.start();
                    updateSeekBar(holder,type);
                    holder.tvVideoPlayOrPause.setBackgroundResource(R.mipmap.ic_pause);
                }
            }
        });
    }
    //播放谁就显示谁的surfaceView，否则显示封面
    private void setVideoPlayOrNot(int position, Holder holder, JHTuiJianBean.ListBean bean, String type) {
        if (position == currentPlayPosition) {
            holder.svVideo.setVisibility(View.VISIBLE);
            holder.ivPlay.setVisibility(View.INVISIBLE);
            holder.ivThumb.setVisibility(View.INVISIBLE);
            holder.rlVideoProgress.setVisibility(View.INVISIBLE);
            holder.tvVideoPlayCount.setVisibility(View.INVISIBLE);
            holder.tvVideoSumtime.setVisibility(View.INVISIBLE);
            playMidea(bean.getVideo().getVideo().get(0), holder,type);//播放文件
            if (!mediaPlayer.isPlaying()) {
                holder.svVideo.setAlpha(0);
            }
        } else {
            holder.ivPlay.setVisibility(View.VISIBLE);
            holder.rlVideoProgress.setVisibility(View.INVISIBLE);
            holder.tvVideoPlayCount.setVisibility(View.VISIBLE);
            holder.ivThumb.setVisibility(View.VISIBLE);
            holder.svVideo.setVisibility(View.INVISIBLE);
            holder.tvVideoSumtime.setVisibility(View.VISIBLE);
        }

    }
    //点击surfaceview事件
    private void setSurfaceViewClickListener(final Holder holder) {
        holder.svVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()||holder.ivPlay.getVisibility()==View.GONE){
                    if(holder.rlVideoProgress.getVisibility()==View.VISIBLE){
                        holder.rlVideoProgress.setVisibility(View.GONE);
                    }else{
                        holder.rlVideoProgress.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    //点击播放按钮事件
    private void setVideoPlayClickListener(final int position, Holder holder) {
        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayPosition = position;
                notifyDataSetChanged();
            }
        });
    }
    //根据类型加载内容布局的界面和对应设置
    private void setingItemThumb(Holder holder, JHTuiJianBean.ListBean bean, String type,int position) {
        String thumbUrl;
        if("image".equals(type)){
            thumbUrl=bean.getImage().getBig().get(0);
            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            ViewGroup.LayoutParams layoutParams = holder.ivImageShow.getLayoutParams();
            int width = systemService.getDefaultDisplay().getWidth()/bean.getImage().getWidth();
            holder.ivImageShowAll.setVisibility(View.VISIBLE);
            if(bean.getImage().getHeight()>systemService.getDefaultDisplay().getHeight()){
                layoutParams.height=systemService.getDefaultDisplay().getHeight()-100;
//                thumbUrl=bean.getImage().getThumbnail_small().get(0);
            }else{
                layoutParams.height=bean.getImage().getHeight()*width;
            }
            holder.ivImageShow.setScaleType(ImageView.ScaleType.MATRIX);
            holder.ivImageShow.setLayoutParams(layoutParams);
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(thumbUrl))
                    .setResizeOptions(new ResizeOptions(1000, 1000))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(bean.getImage().getThumbnail_small().get(0)))
                    .setImageRequest(request)
                    .setOldController(holder.ivImageShow.getController())
                    .build();
            holder.ivImageShow.setController(draweeController);
        }
        else if("gif".equals(type)){
            thumbUrl=bean.getGif().getImages().get(0);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(thumbUrl))
                    .setAutoPlayAnimations(true)
                    .build();
            holder.ivImageShowAll.setVisibility(View.INVISIBLE);
            holder.ivImageShow.setController(draweeController);
            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = systemService.getDefaultDisplay().getWidth()/bean.getGif().getWidth();
            ViewGroup.LayoutParams layoutParams = holder.ivImageShow.getLayoutParams();
            layoutParams.height=bean.getGif().getHeight()*width;
            holder.ivImageShow.setLayoutParams(layoutParams);
        }
        else if("video".equals(type)){
            thumbUrl=bean.getVideo().getThumbnail().get(0);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(thumbUrl))
                    .setAutoPlayAnimations(true)
                    .build();
            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = systemService.getDefaultDisplay().getWidth()/bean.getVideo().getWidth();
            ViewGroup.LayoutParams layoutParams = holder.ivThumb.getLayoutParams();
            ViewGroup.LayoutParams videoLayoutParams = holder.svVideo.getLayoutParams();
            ViewGroup.LayoutParams bgLayoutParams = holder.ivBg.getLayoutParams();
            int videoHeight = Math.max(bean.getVideo().getWidth(),bean.getVideo().getHeight());
            layoutParams.height= videoHeight*width;
            videoLayoutParams.height= videoHeight*width;
            bgLayoutParams.height= videoHeight*width;
            holder.ivThumb.setLayoutParams(layoutParams);
            holder.svVideo.setLayoutParams(videoLayoutParams);
            holder.ivBg.setLayoutParams(bgLayoutParams);
            holder.tvVideoPlayCount.setText(bean.getVideo().getPlaycount()+"次播放");
            holder.tvVideoSumtime.setText(TimeUtil.parseDuration(bean.getVideo().getDuration()));
//            holder.ivThumb.setImageURI(Uri.parse(thumbUrl));
            holder.ivThumb.setController(draweeController);
            setVideoPlayClickListener(position, holder);
            setSurfaceViewClickListener(holder);
            setVideoPlayOrNot(position, holder, bean, type);
            setVideoPlayOrPauseClickListener(holder, type);
            setSeekBarProgress(holder,type);
        }
    }
    //获取基本信息（用户名，正文，评论，赞等）
    private void setBaseData(Holder holder, JHTuiJianBean.ListBean bean, JHTuiJianBean.ListBean.UBean uBean) {
        //获取基本信息（用户名，正文，评论，赞等）
        String headUri = uBean.getHeader().get(0);
        holder.sdvUserHead.setImageURI(Uri.parse(headUri));
        holder.tvUserName.setText(uBean.getName());
        //是否显示VIP
        if(uBean.isIs_v()){
            holder.ivV.setVisibility(View.VISIBLE);
        }else{
            holder.ivV.setVisibility(View.INVISIBLE);
        }
        if(uBean.isIs_vip()){
            holder.ivSV.setVisibility(View.VISIBLE);
        }else{
            holder.ivSV.setVisibility(View.INVISIBLE);
        }
        holder.tvDetil.setText(bean.getText());//设置详细内容
        holder.tvPastTime.setText(bean.getPasstime());
        holder.rbZan.setText(bean.getUp());//赞
        holder.rbNozan.setText(bean.getDown()+"");//踩
        holder.tvShare.setText(bean.getForward()+"");//分享
        holder.tvComment.setText(bean.getComment());//评论
        List<JHTuiJianBean.ListBean.TopCommentsBean> top_comments = bean.getTop_comments();
        if(top_comments!=null){
            holder.llComent.setVisibility(View.VISIBLE);
            holder.rlComent2.setVisibility(View.GONE);
            holder.rlComent3.setVisibility(View.GONE);
            if(top_comments.size()>=1){
                JHTuiJianBean.ListBean.TopCommentsBean commentsBean = top_comments.get(0);
                holder.rlComent1.setVisibility(View.VISIBLE);
                holder.tvComUser1.setText(commentsBean.getU().getName()+"\t:");
                String content = commentsBean.getContent();
                if(content==null||"".equals(content)){
                    holder.tvCom1.setText(commentsBean.getU().getName()+"\t:\t"+ "");
//                    holder.tvCom1.setBackgroundResource(R.mipmap.baisibudejie);
                }else{
                    holder.tvCom1.setText(commentsBean.getU().getName()+"\t:\t"+ content);
                }
            }
            if(top_comments.size()>=2){
                JHTuiJianBean.ListBean.TopCommentsBean commentsBean = top_comments.get(1);
                holder.rlComent2.setVisibility(View.VISIBLE);
                holder.tvComUser2.setText(commentsBean.getU().getName()+"\t:");
                holder.tvCom2.setText(commentsBean.getU().getName()+"\t:\t"+commentsBean.getContent());
            } if(top_comments.size()>=3){
                JHTuiJianBean.ListBean.TopCommentsBean commentsBean = top_comments.get(2);
                holder.rlComent3.setVisibility(View.VISIBLE);
                holder.tvComUser3.setText(commentsBean.getU().getName()+"\t:");
                holder.tvCom3.setText(commentsBean.getU().getName()+"\t:\t"+commentsBean.getContent());
            }

        }else{
            holder.llComent.setVisibility(View.GONE);
        }
    }
    //根据类型显示对应的布局
    private void setingShowType(Holder holder, String type) {
        //根据类型显示对应的布局
        if("text".equals(type)){
            holder.levelC.setVisibility(View.GONE);
        }else if("video".equals(type)){
            holder.levelC.setVisibility(View.VISIBLE);
            holder.videoLevelC.setVisibility(View.VISIBLE);
            holder.audioLevelC.setVisibility(View.GONE);
            holder.imageLevelC.setVisibility(View.GONE);
        }else if("image".equals(type)||"gif".equals(type)){
            holder.levelC.setVisibility(View.VISIBLE);
            holder.imageLevelC.setVisibility(View.VISIBLE);
            holder.audioLevelC.setVisibility(View.GONE);
            holder.videoLevelC.setVisibility(View.GONE);
        }else if("audio".equals(type)){
            holder.levelC.setVisibility(View.VISIBLE);
            holder.audioLevelC.setVisibility(View.VISIBLE);
            holder.videoLevelC.setVisibility(View.GONE);
            holder.imageLevelC.setVisibility(View.GONE);
        }
    }
    //更新视频或音频进度条
    private void updateSeekBar(final Holder holder, final String type) {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        //播放的为音频时更新音频进度
        if("audio".equals(type)){
            holder.tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition/1000));
            holder.voiceSeekbar.setProgress(progress);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                updataProgressCallback = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar(holder,type);
                    }
                };
                handler.postDelayed(updataProgressCallback, 1000);
            }
        }
        //播放的为视频时更新视频进度
        if("video".equals(type)){
            holder.tvVideoCurrentTime.setText(TimeUtil.parseDuration(currentPosition/1000));
            holder.tvVideoTotalTime.setText(TimeUtil.parseDuration(duration/1000));
            holder.videoSeekbar.setProgress(progress);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                updataProgressCallback = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar(holder,type);
                    }
                };
                handler.postDelayed(updataProgressCallback, 1000);
            }
        }

    }
    //播放视频或音频文件
    private void playMidea(String mideaUri, final Holder holder, final String type) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(mideaUri));
            if("video".equals(type)){
                mediaPlayer.setDisplay(holder.svVideo.getHolder());
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    updateSeekBar(holder,type);//更新进度
                    if("video".equals(type)){
                        holder.svVideo.setAlpha(1);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //视频音频进度条拖动监听
    private void setSeekBarProgress(Holder holder,String type) {
        if("video".equals(type)){
            holder.videoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    mediaPlayer.seekTo((int) (mediaPlayer.getDuration()*(progress/100f)));
                }
            });
        }
        if("audio".equals(type)){
            holder.voiceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    mediaPlayer.seekTo((int) (mediaPlayer.getDuration()*(progress/100f)));
                }
            });
        }

    }

    static class Holder {
        @Bind(R.id.sdvUserHead)
        SimpleDraweeView sdvUserHead;
        @Bind(R.id.rgZanOrCai)
        LinearLayout rgZanOrCai;
        @Bind(R.id.ivV)
        ImageView ivV;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.ivSV)
        ImageView ivSV;
        @Bind(R.id.tvPastTime)
        TextView tvPastTime;
        @Bind(R.id.LevelA)
        RelativeLayout LevelA;
        @Bind(R.id.tvDetil)
        TextView tvDetil;
        @Bind(R.id.ivBg)
        ImageView ivBg;
        @Bind(R.id.svVideo)
        SurfaceView svVideo;
        @Bind(R.id.ivThumb)
        SimpleDraweeView ivThumb;
        @Bind(R.id.ivPlay)
        ImageView ivPlay;
        @Bind(R.id.tvVideoPlayCount)
        TextView tvVideoPlayCount;
        @Bind(R.id.tvVideoSumtime)
        TextView tvVideoSumtime;
        @Bind(R.id.videoSeekbar)
        SeekBar videoSeekbar;
        @Bind(R.id.tvVideoPlayOrPause)
        TextView tvVideoPlayOrPause;
        @Bind(R.id.tvVideoCurrentTime)
        TextView tvVideoCurrentTime;
        @Bind(R.id.tvVideoTotalTime)
        TextView tvVideoTotalTime;
        @Bind(R.id.tvVideoFangDa)
        TextView tvVideoFangDa;
        @Bind(R.id.tvVideoDanmu)
        TextView tvVideoDanmu;
        @Bind(R.id.tvVideoDownload)
        TextView tvVideoDownload;
        @Bind(R.id.rlVideoProgress)
        RelativeLayout rlVideoProgress;
        @Bind(R.id.videoLevelC)
        RelativeLayout videoLevelC;
        @Bind(R.id.ivImageShow)
        SimpleDraweeView ivImageShow;
        @Bind(R.id.ivImageShowAll)
        TextView ivImageShowAll;
        @Bind(R.id.imageLevelC)
        RelativeLayout imageLevelC;
        @Bind(R.id.ivBackground)
        ImageView ivBackground;
        @Bind(R.id.tvPlayCount)
        TextView tvPlayCount;
        @Bind(R.id.tvVoiceSumtime)
        TextView tvVoiceSumtime;
        @Bind(R.id.audioBody)
        RelativeLayout audioBody;
        @Bind(R.id.levelE)
        View levelE;
        @Bind(R.id.tvPlayOrPause)
        TextView tvPlayOrPause;
        @Bind(R.id.voiceSeekbar)
        SeekBar voiceSeekbar;
        @Bind(R.id.tvCurrentTime)
        TextView tvCurrentTime;
        @Bind(R.id.tvTotalTime)
        TextView tvTotalTime;
        @Bind(R.id.rlVoiceProgress)
        RelativeLayout rlVoiceProgress;
        @Bind(R.id.tvPlayVoice)
        TextView tvPlayVoice;
        @Bind(R.id.audioLevelC)
        RelativeLayout audioLevelC;
        @Bind(R.id.levelC)
        LinearLayout levelC;
        @Bind(R.id.rbZan)
        CheckBox rbZan;
        @Bind(R.id.rbNozan)
        CheckBox rbNozan;
        @Bind(R.id.tvShare)
        TextView tvShare;
        @Bind(R.id.tvComment)
        TextView tvComment;
        @Bind(R.id.LevelD)
        LinearLayout LevelD;
        @Bind(R.id.tvComUser1)
        TextView tvComUser1;
        @Bind(R.id.tvCom1)
        TextView tvCom1;
        @Bind(R.id.rlComent1)
        RelativeLayout rlComent1;
        @Bind(R.id.tvComUser2)
        TextView tvComUser2;
        @Bind(R.id.tvCom2)
        TextView tvCom2;
        @Bind(R.id.rlComent2)
        RelativeLayout rlComent2;
        @Bind(R.id.tvComUser3)
        TextView tvComUser3;
        @Bind(R.id.tvCom3)
        TextView tvCom3;
        @Bind(R.id.rlComent3)
        RelativeLayout rlComent3;
        @Bind(R.id.llComent)
        LinearLayout llComent;
        @Bind(R.id.llRoot)
        RelativeLayout llRoot;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
