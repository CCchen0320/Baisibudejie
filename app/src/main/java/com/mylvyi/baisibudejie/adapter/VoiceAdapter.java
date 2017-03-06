package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.VoiceCommentActivity;
import com.mylvyi.baisibudejie.bean.XTShengyinBean;
import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by qf on 2016/11/2.
 */
public class VoiceAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<XTShengyinBean.ListBean> data;
    Context context;
    private MediaPlayer mediaPlayer;
    private int currentPlayPosition = -1;
    public static final String TAG = "testVoice";
    Handler handler;
    private Runnable updataProgressCallback;

    public VoiceAdapter(List<XTShengyinBean.ListBean> data, Context context, Handler handler) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        mediaPlayer = MediaUtil.getMediaPlayer();
        this.handler=handler;
    }

    public void resetCurrentPlayPosition(){
        currentPlayPosition=-1;
        notifyDataSetChanged();
    }

    public void updata(List<XTShengyinBean.ListBean> data) {
        this.data = data;
        notifyDataSetChanged();
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
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.voice_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final XTShengyinBean.ListBean bean = data.get(position);
        initBaseData(holder, bean);
        setPlayConctrl(position, holder, bean);
        setSeekBarProgress(holder);
        setUpOrDown(holder, bean); //点击赞或踩之后，radiogrounp设为不可点击
        setCheckBoxClickListener(holder, bean);//设置点赞checkbox点击事件监听
        setClickToShare(holder, bean);//设置分享按钮事件监听
        setClickToCommentpage(holder, bean);//设置评论按钮事件监听
        setVoiveDetilClickListener(holder, bean);//设置详情点击事件监听
        return convertView;
    }

    //设置详情点击事件监听
    private void setVoiveDetilClickListener(Holder holder, final XTShengyinBean.ListBean bean) {
        holder.tvVoiceDetil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnToCommentActivity(bean);
            }
        });
    }
    //设置评论按钮事件监听
    private void setClickToCommentpage(Holder holder, final XTShengyinBean.ListBean bean) {
        holder.tvPinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnToCommentActivity(bean);
            }
        });
    }
    //跳到评论页面
    private void turnToCommentActivity(XTShengyinBean.ListBean bean) {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        currentPlayPosition=-1;
        notifyDataSetChanged();
        Intent intent = new Intent(context, VoiceCommentActivity.class);
        intent.putExtra("bean",bean);
        context.startActivity(intent);
    }
    //设置分享按钮事件监听
    private void setClickToShare(Holder holder, final XTShengyinBean.ListBean bean) {
        holder.tvFenxiang.setOnClickListener(new View.OnClickListener() {
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
                oks.setImageUrl(bean.getAudio().getThumbnail().get(0));
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
    private void setCheckBoxClickListener(Holder holder, final XTShengyinBean.ListBean bean) {
        holder.cbZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setIsZan(true);
                notifyDataSetChanged();
            }
        });

        holder.cbNozan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setIsCai(true);
                notifyDataSetChanged();
            }
        });
    }
    //点击赞或踩之后，radiogrounp设为不可点击
    private void setUpOrDown(final Holder holder, final XTShengyinBean.ListBean bean) {
        if(bean.getIsZan()==true||bean.getIsCai()==true){
            if(bean.getIsZan()==true){
                holder.cbZan.setChecked(true);
                Log.e(TAG, "setUpOrDown_cbZan.setChecked: "+holder.cbZan.isChecked()+bean.getU().getName() );
                holder.cbZan.setEnabled(false);
                holder.cbNozan.setEnabled(false);
            }else{
                holder.cbNozan.setChecked(true);
                Log.e(TAG, "setUpOrDown_cbNozan.setChecked: "+holder.cbNozan.isChecked()+bean.getU().getName() );
                holder.cbZan.setEnabled(false);
                holder.cbNozan.setEnabled(false);
            }
        }else{
            holder.cbZan.setChecked(false);
            holder.cbNozan.setChecked(false);
        }
    }
    //进度条鱼播放器联动
    private void setSeekBarProgress(Holder holder) {

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
    //更新进度条
    private void updateSeekBar(final Holder holder) {
        int currentPosition = mediaPlayer.getCurrentPosition();
        holder.tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition/1000));
        holder.tvTotalTime.setText(TimeUtil.parseDuration(mediaPlayer.getDuration()));
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        Log.e(TAG, "updateSeekBar: "+progress );
        holder.voiceSeekbar.setProgress(progress);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            updataProgressCallback = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar(holder);
                }
            };
            handler.postDelayed(updataProgressCallback, 1000);
        }
    }
    //播放控制
    private void setPlayConctrl(final int position, final Holder holder, XTShengyinBean.ListBean listBean) {
        holder.tvPlayVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayPosition = position;
                notifyDataSetChanged();
            }
        });
        //播放或暂停
        holder.tvPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    holder.tvPlayOrPause.setBackgroundResource(R.mipmap.play_voice_pressed);
                } else {
                    mediaPlayer.start();
                    updateSeekBar(holder);
                    holder.tvPlayOrPause.setBackgroundResource(R.mipmap.my_news_play_audio);
                }
            }
        });

        if (position == currentPlayPosition) {
            holder.rlVoiceProgress.setVisibility(View.VISIBLE);
            holder.tvPlayVoice.setVisibility(View.INVISIBLE);
            holder.tvVoiceSumtime.setVisibility(View.INVISIBLE);
            playVoice(listBean.getAudio().getAudio().get(0),holder);
        } else {
            holder.rlVoiceProgress.setVisibility(View.INVISIBLE);
            holder.tvPlayVoice.setVisibility(View.VISIBLE);
            holder.tvVoiceSumtime.setVisibility(View.VISIBLE);
            handler.removeCallbacks(updataProgressCallback);
        }

        //处理串行播放
        Integer formerPosition = (Integer) holder.tvPlayVoice.getTag();
        if (formerPosition != null && position != currentPlayPosition && formerPosition == currentPlayPosition) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            currentPlayPosition = -1;
        }
        holder.tvPlayVoice.setTag(position);
    }
    //播放
    private void playVoice(String videoUrl, final Holder holder) {
        try {
            mediaPlayer.reset();//重置mediaPlayer
            mediaPlayer.setDataSource(context, Uri.parse(videoUrl));//设置数据源
            mediaPlayer.prepareAsync();//准备播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();//异步回调：准备好之后开始播放视频
                    updateSeekBar(holder);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //初始化基本信息
    private void initBaseData(Holder holder, XTShengyinBean.ListBean listBean) {
        //用户类信息
        String userName = listBean.getU().getName();
        String userHeadIcon = listBean.getU().getHeader().get(0);
        boolean userIsV = listBean.getU().isIs_v();
        boolean is_vip = listBean.getU().isIs_vip();
        String passtime = listBean.getPasstime();
        //声音信息
        String itemId = listBean.getId();
        String detail = listBean.getText();
        String audioUrl = listBean.getAudio().getAudio().get(0);
        int duration = listBean.getAudio().getDuration();
        int height = listBean.getAudio().getHeight();
        int playcount = listBean.getAudio().getPlaycount();
        String thumbnailUrl = listBean.getAudio().getThumbnail().get(0);
        String thumbnailSmallUrl = listBean.getAudio().getThumbnail_small().get(0);
        //评论信息
        String zan = listBean.getUp();
        int cai = listBean.getDown();
        String comment = listBean.getComment();
        int forward = listBean.getForward();
        //设置基本信息
        if (!userIsV) {
            holder.isV.setVisibility(View.GONE);
        }
        if (!is_vip) {
            holder.isVIP.setVisibility(View.GONE);
        }
        holder.sdvUserHead.setImageURI(Uri.parse(userHeadIcon));
        holder.tvUserName.setText(userName);
        holder.tvPastTime.setText(passtime);
        holder.tvVoiceDetil.setText(detail);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(thumbnailUrl))
                .setAutoPlayAnimations(true)
                .build();//设置图片宽高
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        float width =1f* systemService.getDefaultDisplay().getWidth()/listBean.getAudio().getWidth();
        ViewGroup.LayoutParams layoutParams = holder.ivBackground.getLayoutParams();//设置图片宽高
        holder.ivBackground.setImageURI(Uri.parse(thumbnailUrl));//设置图片宽高
        if(width>0&&width<=1){
            layoutParams.height= (int) (listBean.getAudio().getHeight()*width);//设置图片宽高
        }else{
            layoutParams.height= listBean.getAudio().getHeight()*(int)width;//设置图片宽高
        }
        holder.ivBackground.setController(draweeController);
        holder.tvPlayCount.setText(playcount + "次播放");
        holder.tvVoiceSumtime.setText(TimeUtil.parseDuration(duration));
        holder.cbZan.setText(zan);
        holder.cbNozan.setTag(cai + "");
        holder.tvFenxiang.setText(forward + "");
        holder.tvPinglun.setText(comment);
    }

    class Holder {
        @Bind(R.id.sdvUserHead)
        SimpleDraweeView sdvUserHead;
        @Bind(R.id.isV)
        ImageView isV;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.isVIP)
        ImageView isVIP;
        @Bind(R.id.tvPastTime)
        TextView tvPastTime;
        @Bind(R.id.levelA)
        RelativeLayout levelA;
        @Bind(R.id.tvVoiceDetil)
        TextView tvVoiceDetil;
        @Bind(R.id.ivBackground)
        SimpleDraweeView ivBackground;
        @Bind(R.id.tvPlayCount)
        TextView tvPlayCount;
        @Bind(R.id.tvVoiceSumtime)
        TextView tvVoiceSumtime;
        @Bind(R.id.levelC)
        RelativeLayout levelC;
        @Bind(R.id.levelE)
        View levelE;
        @Bind(R.id.cbZan)
        CheckBox cbZan;
        @Bind(R.id.cbNozan)
        CheckBox cbNozan;
        @Bind(R.id.tvFenxiang)
        TextView tvFenxiang;
        @Bind(R.id.tvPinglun)
        TextView tvPinglun;
        @Bind(R.id.levelD)
        LinearLayout levelD;
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

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
