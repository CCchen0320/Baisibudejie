package com.mylvyi.baisibudejie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.FullScreenPlayActivity;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.bean.VideoJingHua;
import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class VideoJHAdapter extends BaseAdapter {

    private static final String TAG = "www";
    private static final int PLAYPB_VISB = 333;
    private static final int PLAYPB_INVISB = 444;
    private LayoutInflater inflater;
    private Context context;
    private List<VideoJingHua.ListBean> data = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaVociePlayer;
    public static int currentPlayingPosition = -1;
    private String text;
    private int up;
    private int down;
    private int forward;
    private String share_url;
    private VideoJingHua.ListBean.UBean uBean;
    private VideoJingHua.ListBean.VideoBean video;
    private VideoJingHua.ListBean listBean;
    private String headerUri;
    private String uName;
    private boolean isV;
    private boolean isVip;
    private String passtime;
    private String playUri;
    private String downUri;
    private int playcount;
    private int videoDuration;
    private String bigMapUri;
    private String smallMapUri;
    private int comment;
    private int myPosition;
    private int vTaPosition;
    private int vosPosition;

    private List<VideoJingHua.ListBean.TopCommentBean> top_comments;
    private boolean isZan;
    private boolean isCai;
    private Runnable updataProgressCallback;
    private Handler handler = new Handler();
    private int currentPosition;
    private Display display;
    private String duration;

    public void setData(List<VideoJingHua.ListBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void resetCurrentPlayPosition() {
        currentPlayingPosition = -1;
        notifyDataSetChanged();
    }

    public VideoJHAdapter(Context context, List<VideoJingHua.ListBean> data, Display display) {
        this.context = context;
        this.data = data;
        this.display = display;
        inflater = LayoutInflater.from(context);
        mediaPlayer = MediaUtil.getMediaPlayer();
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
            convertView = inflater.inflate(R.layout.video_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        //初始化bean拿到的数据
        initDataBean(position);

        //初始化首次页面加载数据
        initItemDate(position, holder);

        //点击赞或踩之后，checkbox设为不可点击  尚存BUG
        setUpOrDownEnable(holder, listBean, position);

        //设置checkbox点击事件监听
        setCheckBoxClickListener(holder, listBean, position);

        //top 评论显示与否 和评论的显示方式 条目
        setTopComments(position, holder);

        //点击封面开始播放
        holder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayingPosition = position;
                notifyDataSetChanged();
            }
        });

        //进度条 控制播放 显示与否 及点击事件监听 开启全屏播放
        videoProgressOnClick(holder, duration);

        //播放谁就显示谁的surfaceView，否则显示封面
        setVisibleSV(position, holder);

        //一旦播放当前播放条目离开屏幕，就停止播放，并将当前播放位置重新置为-1
        setPlayOrPauseNowItem(position, holder);


        return convertView;

    }

    //初始化首次页面加载数据
    private void initItemDate(int position, Holder holder) {
        mediaVociePlayer = new MediaPlayer();
        holder.tvVideoDetil.setText(text);
        Glide.with(context).load(Uri.parse(bigMapUri))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivBlur);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(bigMapUri))
                .setAutoPlayAnimations(true)
                .build();
        holder.ivImg.setController(controller);
        holder.sdvVideoCover.setImageURI(headerUri);
        if (isV) {
            holder.isV.setVisibility(View.VISIBLE);
        } else {
            holder.isV.setVisibility(View.INVISIBLE);
        }
        if (isVip) {
            holder.isVIP.setVisibility(View.VISIBLE);
            vTaPosition = position;
        } else {
            holder.isVIP.setVisibility(View.INVISIBLE);
        }
        holder.rbNozan.setText(down + "");
        holder.rbZan.setText(up + "");
        holder.tvFenxiang.setText(forward + "");
        holder.tvPinglun.setText(comment + "");
        holder.tvVideoName.setText(uName);
        holder.tvVideoTime.setText(passtime);
        holder.tvBofaCount.setText(playcount + "次播放");
        duration = TimeUtil.parseDuration(videoDuration);
        holder.tvVideoSumtime.setText(duration);
        holder.tvSumTime.setText(duration);

        if (isVip && vTaPosition == position) {
            holder.tvVideoName.setTextColor(Color.RED);
        } else {
            holder.tvVideoName.setTextColor(Color.BLACK);
        }
    }

    //一旦播放当前播放条目离开屏幕，就停止播放，并将当前播放位置重新置为-1
    private void setPlayOrPauseNowItem(int position, Holder holder) {
        Integer formerPosition = (Integer) holder.svVideo.getTag();//前世
        if (formerPosition != null && position != currentPlayingPosition && formerPosition == currentPlayingPosition) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            currentPlayingPosition = -1;
        }
        holder.svVideo.setTag(position);
    }

    //播放谁就显示谁的surfaceView，否则显示封面
    private void setVisibleSV(int position, Holder holder) {
        if (position == currentPlayingPosition) {
            holder.ivImg.setVisibility(View.INVISIBLE);
            holder.svVideo.setVisibility(View.VISIBLE);
            holder.tvBofaCount.setVisibility(View.INVISIBLE);
            holder.tvVideoSumtime.setVisibility(View.INVISIBLE);
            holder.ivBofaBtn.setVisibility(View.INVISIBLE);
            holder.pb.setVisibility(View.VISIBLE);
            playVideo(playUri, holder);
            if (!mediaPlayer.isPlaying()) {
                holder.svVideo.setAlpha(0);
            }
        } else {
            holder.ivImg.setVisibility(View.VISIBLE);
            holder.svVideo.setVisibility(View.INVISIBLE);
            holder.tvBofaCount.setVisibility(View.VISIBLE);
            holder.tvVideoSumtime.setVisibility(View.VISIBLE);
            holder.ivBofaBtn.setVisibility(View.VISIBLE);
            holder.pb.setVisibility(View.INVISIBLE);
            holder.rlVideoProgress.setVisibility(View.INVISIBLE);
        }
    }

    //进度条 控制播放 显示与否 及点击事件监听 开启全屏播放
    private void videoProgressOnClick(final Holder holder, final String duration) {
        holder.svVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rlVideoProgress.getVisibility() == View.INVISIBLE) {
                    holder.rlVideoProgress.setVisibility(View.VISIBLE);
                    holder.tvSumTime.setText(duration);
                    setVideoPlayOrPauseListenner(holder);//播放按钮监听
                    setSeekBarChangeListener(holder);// 进度条拖动监听
                    holder.tvFulScreen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, FullScreenPlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("dur", duration);
                            bundle.putInt("cup", currentPosition);
                            intent.putExtras(bundle);
                            mediaPlayer.pause();
                            context.startActivity(intent);
                            currentPlayingPosition = -1;
                            notifyDataSetChanged();
                            Log.e(TAG, "onClick: stoppppppppppppppppp ");
                        }
                    });

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.rlVideoProgress.setVisibility(View.INVISIBLE);
                        }
                    }, 5000);
                } else {
                    holder.rlVideoProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //top 评论显示与否
    private void setTopComments(int position, Holder holder) {
        if (top_comments != null && top_comments.size() != 0) {
            holder.llComent.setVisibility(View.VISIBLE);
            vosPosition = position;
            notifyDataSetChanged();
        } else {
            holder.llComent.setVisibility(View.GONE);
        }

        if (vosPosition == position && top_comments != null) {
            Log.e(TAG, "getView: top_comments" + top_comments.size());
            if (top_comments.size() == 1) {
                final VideoJingHua.ListBean.TopCommentBean topCommentBean = top_comments.get(0);
                holder.rlComent1.setVisibility(View.VISIBLE);
                holder.tvComUser1.setText(topCommentBean.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom1, holder.flmVoice1, holder.pbmVoice1, holder.ivVoiec1, topCommentBean);

                holder.rlComent2.setVisibility(View.GONE);
                holder.rlComent3.setVisibility(View.GONE);
            } else if (top_comments.size() == 2) {
                VideoJingHua.ListBean.TopCommentBean topCommentBean1 = top_comments.get(0);
                VideoJingHua.ListBean.TopCommentBean topCommentBean2 = top_comments.get(1);
                holder.rlComent1.setVisibility(View.VISIBLE);
                holder.rlComent2.setVisibility(View.VISIBLE);

                holder.tvComUser1.setText(topCommentBean1.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom1, holder.flmVoice1, holder.pbmVoice1, holder.ivVoiec1, topCommentBean1);

                holder.tvComUser2.setText(topCommentBean2.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom2, holder.flmVoice2, holder.pbmVoice2, holder.ivVoiec2, topCommentBean2);

                holder.rlComent3.setVisibility(View.GONE);
            } else if (top_comments.size() == 3) {
                VideoJingHua.ListBean.TopCommentBean topCommentBean1 = top_comments.get(0);
                VideoJingHua.ListBean.TopCommentBean topCommentBean2 = top_comments.get(1);
                VideoJingHua.ListBean.TopCommentBean topCommentBean3 = top_comments.get(2);
                holder.rlComent1.setVisibility(View.VISIBLE);
                holder.rlComent2.setVisibility(View.VISIBLE);
                holder.rlComent3.setVisibility(View.VISIBLE);

                holder.tvComUser1.setText(topCommentBean1.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom1, holder.flmVoice1, holder.pbmVoice1, holder.ivVoiec1, topCommentBean1);

                holder.tvComUser2.setText(topCommentBean2.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom2, holder.flmVoice2, holder.pbmVoice2, holder.ivVoiec2, topCommentBean2);

                holder.tvComUser3.setText(topCommentBean3.getU().getName() + ":  ");
                setTextOrPlayVoice(holder.tvCom3, holder.flmVoice3, holder.pbmVoice3, holder.ivVoiec3, topCommentBean3);
            }

        }
    }

    //进度条同步更新
    private void unDateSeekBarPregress(final Holder holder) {
        currentPosition = mediaPlayer.getCurrentPosition();
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        holder.tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition / 1000));
        holder.videoSeekbar.setProgress(progress);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            updataProgressCallback = new Runnable() {
                @Override
                public void run() {
                    unDateSeekBarPregress(holder);
                }
            };
            handler.postDelayed(updataProgressCallback, 1000);
        }
    }

    // 进度条拖动监听
    private void setSeekBarChangeListener(Holder holder) {
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
                mediaPlayer.seekTo((int) (mediaPlayer.getDuration() * (progress / 100f)));
            }
        });
    }

    //设置播放按钮监听
    private void setVideoPlayOrPauseListenner(final Holder holder) {
        holder.tvPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    holder.tvPlayOrPause.setBackgroundResource(R.mipmap.video_play);
                } else {
                    mediaPlayer.start();
                    holder.tvPlayOrPause.setBackgroundResource(R.mipmap.video_pause);
                }
            }
        });
    }

    //评论内容是文字还是声音
    private void setTextOrPlayVoice(TextView tvCom1, RelativeLayout flmVoice1, final ProgressBar pbmVoice1, final ImageView ivVoiec1, final VideoJingHua.ListBean.TopCommentBean topCommentBean) {
        if (topCommentBean.getCmt_type().equals("text")) {
            tvCom1.setText(topCommentBean.getContent());
            flmVoice1.setVisibility(View.GONE);
        } else {
            tvCom1.setText(topCommentBean.getVoicetime() + "″");
            flmVoice1.setVisibility(View.VISIBLE);
//            final AnimationDrawable ad = ((AnimationDrawable) ivVoiec1.getDrawable());
            flmVoice1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mediaVociePlayer.isPlaying()){
                        mediaVociePlayer.stop();
//                        ad.stop();
                    }else {
                        pbmVoice1.setVisibility(View.VISIBLE);
                        ivVoiec1.setVisibility(View.INVISIBLE);
                        try {
                            mediaVociePlayer.reset();
                            mediaVociePlayer.setDataSource(context, Uri.parse(topCommentBean.getVoiceuri()));
                            mediaVociePlayer.prepareAsync();
                            mediaVociePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mediaPlayer.start();
                                    pbmVoice1.setVisibility(View.INVISIBLE);
                                    ivVoiec1.setVisibility(View.VISIBLE);
//                                    ad.start();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    //设置checkbox点击事件监听
    private void setCheckBoxClickListener(Holder holder, final VideoJingHua.ListBean bean, final int position) {
        holder.rbZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPosition = position;
                bean.setZan(true);
                notifyDataSetChanged();
            }
        });

        holder.rbNozan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPosition = position;
                bean.setCai(true);
                notifyDataSetChanged();
            }
        });
    }

    //点击赞或踩之后，checkbox设为不可点击
    private void setUpOrDownEnable(final Holder holder, final VideoJingHua.ListBean bean, int position) {
        if (bean.isZan() == true || bean.isCai() == true) {
            if (bean.isZan() == true) {
                holder.rbZan.setChecked(true);
                holder.rbZan.setEnabled(false);
                holder.rbNozan.setEnabled(false);
            } else {
                holder.rbNozan.setChecked(true);
                holder.rbZan.setEnabled(false);
                holder.rbNozan.setEnabled(false);
            }
        } else {
            holder.rbZan.setChecked(false);
            holder.rbNozan.setChecked(false);
        }
    }

    //播放视频方法
    private void playVideo(String videoUri, final Holder holder) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(videoUri));
            mediaPlayer.setDisplay(holder.svVideo.getHolder());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    holder.pb.setVisibility(View.INVISIBLE);
                    holder.svVideo.setAlpha(1);
                    unDateSeekBarPregress(holder);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //初始化bean拿到的数据
    private void initDataBean(int position) {
        listBean = data.get(position);
        text = listBean.getText();
        comment = Integer.parseInt(listBean.getComment());
        up = Integer.parseInt(listBean.getUp());
        down = listBean.getDown();
        forward = listBean.getForward();
        share_url = listBean.getShare_url();
        passtime = listBean.getPasstime();
        isZan = listBean.isZan();
        isCai = listBean.isCai();

        uBean = listBean.getU();
        video = listBean.getVideo();

        top_comments = listBean.getTop_comments();

        headerUri = uBean.getHeader().get(0);
        uName = uBean.getName();
        isV = uBean.isIs_v();
        isVip = uBean.isIs_vip();

        playUri = video.getVideo().get(0);
        downUri = video.getDownload().get(0);
        playcount = video.getPlaycount();
        videoDuration = video.getDuration();
        bigMapUri = video.getThumbnail().get(0);
        smallMapUri = video.getThumbnail_small().get(0);
    }


    static class Holder {
        @Bind(R.id.sdvVideoCover)
        SimpleDraweeView sdvVideoCover;
        @Bind(R.id.isV)
        ImageView isV;
        @Bind(R.id.tvVideoName)
        TextView tvVideoName;
        @Bind(R.id.isVIP)
        ImageView isVIP;
        @Bind(R.id.tvVideoTime)
        TextView tvVideoTime;
        @Bind(R.id.tvVideoDetil)
        TextView tvVideoDetil;
        @Bind(R.id.ivBlur)
        ImageView ivBlur;
        @Bind(R.id.svVideo)
        SurfaceView svVideo;
        @Bind(R.id.ivImg)
        SimpleDraweeView ivImg;
        @Bind(R.id.pb)
        ProgressBar pb;
        @Bind(R.id.ivBofaBtn)
        ImageView ivBofaBtn;
        @Bind(R.id.tvBofaCount)
        TextView tvBofaCount;
        @Bind(R.id.tvVideoSumtime)
        TextView tvVideoSumtime;
        @Bind(R.id.videoSeekbar)
        SeekBar videoSeekbar;
        @Bind(R.id.tvPlayOrPause)
        TextView tvPlayOrPause;
        @Bind(R.id.tvCurrentTime)
        TextView tvCurrentTime;
        @Bind(R.id.tvSumTime)
        TextView tvSumTime;
        @Bind(R.id.tvVideoDownload)
        TextView tvVideoDownload;
        @Bind(R.id.tvFulScreen)
        TextView tvFulScreen;
        @Bind(R.id.rlVideoProgress)
        RelativeLayout rlVideoProgress;
        @Bind(R.id.rbZan)
        CheckBox rbZan;
        @Bind(R.id.rbNozan)
        CheckBox rbNozan;
        @Bind(R.id.rgZanOrNo)
        LinearLayout rgZanOrNo;
        @Bind(R.id.tvFenxiang)
        TextView tvFenxiang;
        @Bind(R.id.tvPinglun)
        TextView tvPinglun;
        @Bind(R.id.tvComUser1)
        TextView tvComUser1;
        @Bind(R.id.pbmVoice1)
        ProgressBar pbmVoice1;
        @Bind(R.id.ivVoiec1)
        ImageView ivVoiec1;
        @Bind(R.id.flmVoice1)
        RelativeLayout flmVoice1;
        @Bind(R.id.tvCom1)
        TextView tvCom1;
        @Bind(R.id.rlComent1)
        LinearLayout rlComent1;
        @Bind(R.id.tvComUser2)
        TextView tvComUser2;
        @Bind(R.id.pbmVoice2)
        ProgressBar pbmVoice2;
        @Bind(R.id.ivVoiec2)
        ImageView ivVoiec2;
        @Bind(R.id.flmVoice2)
        RelativeLayout flmVoice2;
        @Bind(R.id.tvCom2)
        TextView tvCom2;
        @Bind(R.id.rlComent2)
        LinearLayout rlComent2;
        @Bind(R.id.tvComUser3)
        TextView tvComUser3;
        @Bind(R.id.pbmVoice3)
        ProgressBar pbmVoice3;
        @Bind(R.id.ivVoiec3)
        ImageView ivVoiec3;
        @Bind(R.id.flmVoice3)
        RelativeLayout flmVoice3;
        @Bind(R.id.tvCom3)
        TextView tvCom3;
        @Bind(R.id.rlComent3)
        LinearLayout rlComent3;
        @Bind(R.id.llComent)
        LinearLayout llComent;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
