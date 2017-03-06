package com.mylvyi.baisibudejie;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mylvyi.baisibudejie.adapter.CommentAdapter;
import com.mylvyi.baisibudejie.bean.CommentBean;
import com.mylvyi.baisibudejie.bean.XTShengyinBean;
import com.mylvyi.baisibudejie.config.Config;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.ThreadUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class VoiceCommentActivity extends AppCompatActivity {

    public static final String TAG = "testv";
    @Bind(R.id.tvBack)
    TextView tvBack;
    @Bind(R.id.llActionbar)
    LinearLayout llActionbar;
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
    @Bind(R.id.lvComments)
    ListView lvComments;
    @Bind(R.id.tvAddVoice)
    TextView tvAddVoice;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.tvAddPicture)
    TextView tvAddPicture;
    @Bind(R.id.tvCommentNone)
    TextView tvCommentNone;
    private XTShengyinBean.ListBean bean;
    private String thumbUrl;
    private String id;
    private MediaPlayer mediaPlayer = MediaUtil.getMediaPlayer();
    private Runnable updataProgressCallback;
    private CommentAdapter adapter;
    private List<CommentBean.NormalBean.ListBean> data = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.ON_JSON_GOT_SUCCESS:
                    String json = (String) msg.obj;
                    try {
                        CommentBean commentBean = JsonUntil.paresCommentBean(json);
                        List<CommentBean.NormalBean.ListBean> listBeen = commentBean.getNormal().getList();
                        data.addAll(listBeen);
                        adapter.notifyDataSetChanged();

                        Log.e(TAG, "handleMessage: "+ listBeen.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(VoiceCommentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Config.ON_JSON_GOT_FAIL:
                    Toast.makeText(VoiceCommentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_comment);
        ButterKnife.bind(this);
        ShareSDK.initSDK(this);
        Intent intent = getIntent();
        bean = (XTShengyinBean.ListBean) intent.getSerializableExtra("bean");
        thumbUrl = bean.getAudio().getThumbnail().get(0);
        id = bean.getId();
        adapter = new CommentAdapter(data, this);
        lvComments.setAdapter(adapter);
        initBaseData();
        setSeekBarProgressChangeListener();
        setUpOrDown(bean);
        getDataFromNet();

    }

    //从网络拿数据
    private void getDataFromNet() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUntil.getPingLunJson(Integer.parseInt(id), VoiceCommentActivity.this);
                if (json != null && json != "") {
                    Message message = handler.obtainMessage(Config.ON_JSON_GOT_SUCCESS);
                    message.obj = json;
                    handler.sendMessage(message);
                } else {
                    Message message2 = handler.obtainMessage(Config.ON_JSON_GOT_FAIL);
                    handler.sendMessage(message2);
                }
            }
        });
    }

    //初始化基本信息
    private void initBaseData() {
        sdvUserHead.setImageURI(Uri.parse(bean.getU().getHeader().get(0)));
        tvUserName.setText(bean.getU().getName());
        tvPastTime.setText(bean.getPasstime());
        if (bean.getU().isIs_v()) {
            isV.setVisibility(View.VISIBLE);
        } else {
            isV.setVisibility(View.GONE);
        }
        if (bean.getU().isIs_vip()) {
            isVIP.setVisibility(View.VISIBLE);
        } else {
            isVIP.setVisibility(View.INVISIBLE);
        }
        tvVoiceDetil.setText(bean.getText());
        tvPlayCount.setText(bean.getAudio().getPlaycount() + "次播放");
        tvVoiceSumtime.setText(TimeUtil.parseDuration(bean.getAudio().getDuration()));
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(thumbUrl))
                .setAutoPlayAnimations(true)
                .build();//设置图片宽高
        WindowManager systemService = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        float width = 1f * systemService.getDefaultDisplay().getWidth() / bean.getAudio().getWidth();
        ViewGroup.LayoutParams layoutParams = ivBackground.getLayoutParams();//设置图片宽高
        ivBackground.setImageURI(Uri.parse(thumbUrl));//设置图片宽高
        if (width > 0 && width <= 1) {
            layoutParams.height = (int) (bean.getAudio().getHeight() * width);//设置图片宽高
        } else {
            layoutParams.height = bean.getAudio().getHeight() * (int) width;//设置图片宽高
        }
        ivBackground.setController(draweeController);
        cbZan.setText(bean.getUp());
        cbNozan.setTag(bean.getDown() + "");
        tvFenxiang.setText(bean.getForward() + "");
        tvPinglun.setText(bean.getComment());
        if (bean.getComment() == null || "".equals(bean.getComment()) || "0".equals(bean.getComment())) {
            tvCommentNone.setVisibility(View.VISIBLE);
        }else{
            tvCommentNone.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tvFenxiang)
    public void onTvFengXiangClick(){
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
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(bean.getShare_url());
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.cbZan)
    public void onCbZanClick(View v) {
        bean.setIsZan(true);
        cbZan.setEnabled(false);
        cbNozan.setEnabled(false);
    }

    @OnClick(R.id.cbNozan)
    public void onCbNozanClick(View v) {
        bean.setIsCai(true);
        cbZan.setEnabled(false);
        cbNozan.setEnabled(false);
    }

    @OnClick(R.id.tvPlayVoice)
    public void onTvPlayVoiceClick(View v) {
        tvPlayVoice.setVisibility(View.GONE);
        playVoice(bean.getAudio().getAudio().get(0));

    }

    @OnClick(R.id.tvPlayOrPause)
    public void onTvPlayOrPauseClick(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            tvPlayOrPause.setBackgroundResource(R.mipmap.play_voice_pressed);
        } else {
            mediaPlayer.start();
            updateSeekBar();
            tvPlayOrPause.setBackgroundResource(R.mipmap.my_news_play_audio);
        }
    }

    //播放
    private void playVoice(String Url) {
        try {
            mediaPlayer.reset();//重置mediaPlayer
            mediaPlayer.setDataSource(this, Uri.parse(Url));//设置数据源
            mediaPlayer.prepareAsync();//准备播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();//异步回调：准备好之后开始播放视频
                    rlVoiceProgress.setVisibility(View.VISIBLE);
                    updateSeekBar();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //更新进度条
    private void updateSeekBar() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition / 1000));
        tvTotalTime.setText(TimeUtil.parseDuration(mediaPlayer.getDuration()));
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        voiceSeekbar.setProgress(progress);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            updataProgressCallback = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            handler.postDelayed(updataProgressCallback, 1000);
        }
    }

    //设置赞或者踩
    private void setUpOrDown(XTShengyinBean.ListBean bean) {
        if (bean.getIsZan() == true || bean.getIsCai() == true) {
            if (bean.getIsZan() == true) {
                cbZan.setChecked(true);
                cbZan.setEnabled(false);
                cbNozan.setEnabled(false);
            } else {
                cbNozan.setChecked(true);
                cbZan.setEnabled(false);
                cbNozan.setEnabled(false);
            }
        } else {
            cbZan.setChecked(false);
            cbNozan.setChecked(false);
        }
    }

    //进度条与播放器联动
    private void setSeekBarProgressChangeListener() {
        voiceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
