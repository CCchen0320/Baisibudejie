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
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.mylvyi.baisibudejie.adapter.CommentAdapter;
import com.mylvyi.baisibudejie.bean.CommentBean;
import com.mylvyi.baisibudejie.bean.JHTuiJianBean;
import com.mylvyi.baisibudejie.config.Config;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.ThreadUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;
import com.mylvyi.baisibudejie.widget.MyListview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ComprehensiveCommentActivity extends AppCompatActivity {

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
    @Bind(R.id.levelB)
    LinearLayout levelB;
    @Bind(R.id.cbZan)
    CheckBox cbZan;
    @Bind(R.id.cbNozan)
    CheckBox cbNozan;
    @Bind(R.id.tvShare)
    TextView tvShare;
    @Bind(R.id.tvPinglun)
    TextView tvPinglun;
    @Bind(R.id.levelD)
    LinearLayout levelD;
    @Bind(R.id.lvComments)
    MyListview lvComments;
    @Bind(R.id.tvCommentNone)
    TextView tvCommentNone;
    @Bind(R.id.tvAddVoice)
    TextView tvAddVoice;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.tvAddPicture)
    TextView tvAddPicture;
    private String type;
    private JHTuiJianBean.ListBean bean;
    private MediaPlayer mediaPlayer;
    private Runnable updataProgressCallback;
    private String id;
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ComprehensiveCommentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Config.ON_JSON_GOT_FAIL:
                    Toast.makeText(ComprehensiveCommentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private JHTuiJianBean.ListBean.UBean uBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehensive_comment);
        ButterKnife.bind(this);
        initBaseData();
        setingShowType(type);
        setingItemThumb(bean,type);//加载内容布局的界面
        setUserMessage();  //设置用户信息和详细内容
        setClickToShare(bean);//设置分享按钮监听
        setImageShowAllClickListener( bean);//设置查看大图按钮监听
        setVideoPlayClickListener();//点击播放按钮事件
        setUpOrDown(bean);
        adapter = new CommentAdapter(data, this);
        lvComments.setAdapter(adapter);
        getDataFromNet();
        cbNozan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

//    //设置checkbox点击事件监听
    @OnClick(R.id.cbNozan)
    public void onCbNozanClick(View v) {
        bean.setIsCai(true);
        setUpOrDown(bean);
        Log.e(TAG, "onCbNozanClick: "+ bean.getIsCai());

    }

    @OnClick(R.id.tvBack)
    public void onTvBackClick(View v) {
       startActivity(new Intent(this,MainActivity.class));
    }

    @OnClick(R.id.cbZan)
    public void onCbZanClick(View v) {
        bean.setIsZan(true);
        Log.e(TAG, "onCbZanClick: "+ bean.getIsZan());
        setUpOrDown(bean);
    }
    //从网络拿数据
    private void getDataFromNet() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUntil.getPingLunJson(Integer.parseInt(id), ComprehensiveCommentActivity.this);
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
    //设置赞或者踩
    private void setUpOrDown(JHTuiJianBean.ListBean bean) {
        if (bean.getIsZan() == true || bean.getIsCai() == true) {
            if (bean.getIsZan() == true) {
                cbZan.setChecked(true);
                cbZan.setEnabled(false);
                cbNozan.setEnabled(false);
                Log.e(TAG, "setUpOrDown: "+cbZan.isEnabled()+"|||"+ cbNozan.isEnabled());
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
    //点击播放按钮事件
    private void setVideoPlayClickListener() {
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPlay.setVisibility(View.GONE);
                ivThumb.setVisibility(View.GONE);
                playMidea(bean.getVideo().getVideo().get(0),type);
            }
        });
    }
    //获得基础数据
    private void initBaseData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        bean = (JHTuiJianBean.ListBean) intent.getSerializableExtra("bean");
        ShareSDK.initSDK(this);
        id = bean.getId();
        mediaPlayer= MediaUtil.getMediaPlayer();
    }
    //根据类型显示对应的布局
    private void setingShowType( String type) {
        if("text".equals(type)){
            levelB.setVisibility(View.GONE);
        }else if("video".equals(type)){
            levelB.setVisibility(View.VISIBLE);
            videoLevelC.setVisibility(View.VISIBLE);
            rlVideoProgress.setVisibility(View.GONE);
            audioLevelC.setVisibility(View.GONE);
            imageLevelC.setVisibility(View.GONE);
        }else if("image".equals(type)||"gif".equals(type)){
            levelB.setVisibility(View.VISIBLE);
            imageLevelC.setVisibility(View.VISIBLE);
            audioLevelC.setVisibility(View.GONE);
            videoLevelC.setVisibility(View.GONE);
        }else if("audio".equals(type)){
            levelB.setVisibility(View.VISIBLE);
            rlVoiceProgress.setVisibility(View.GONE);
            audioLevelC.setVisibility(View.VISIBLE);
            videoLevelC.setVisibility(View.GONE);
            imageLevelC.setVisibility(View.GONE);
        }
    }
    //根据类型加载内容布局的界面和对应设置
    private void setingItemThumb( JHTuiJianBean.ListBean bean, String type) {
        String thumbUrl;
        if("image".equals(type)){
            thumbUrl=bean.getImage().getBig().get(0);
            WindowManager systemService = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            ViewGroup.LayoutParams layoutParams = ivImageShow.getLayoutParams();
            int width = systemService.getDefaultDisplay().getWidth()/bean.getImage().getWidth();
            ivImageShowAll.setVisibility(View.VISIBLE);
            layoutParams.height=400*3;
            ivImageShow.setScaleType(ImageView.ScaleType.MATRIX);
            ivImageShow.setLayoutParams(layoutParams);
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(thumbUrl))
                    .setResizeOptions(new ResizeOptions(1000, 1000))
                    .build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(bean.getImage().getThumbnail_small().get(0)))
                    .setImageRequest(request)
                    .setOldController(ivImageShow.getController())
                    .build();
            ivImageShow.setController(draweeController);
        }
        else if("gif".equals(type)){
            thumbUrl=bean.getGif().getImages().get(0);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(thumbUrl))
                    .setAutoPlayAnimations(true)
                    .build();
            ivImageShowAll.setVisibility(View.INVISIBLE);
            ivImageShow.setController(draweeController);
            WindowManager systemService = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
            int width = systemService.getDefaultDisplay().getWidth()/bean.getGif().getWidth();
            ViewGroup.LayoutParams layoutParams = ivImageShow.getLayoutParams();
            layoutParams.height=bean.getGif().getHeight()*width;
            ivImageShow.setLayoutParams(layoutParams);
        }
        else if("video".equals(type)){
            thumbUrl=bean.getVideo().getThumbnail().get(0);
            tvVideoPlayCount.setText(bean.getVideo().getPlaycount()+"次播放");
            tvVideoSumtime.setText(TimeUtil.parseDuration(bean.getVideo().getDuration()));
            ivThumb.setImageURI(Uri.parse(thumbUrl));
            setSurfaceViewClickListener();
            setVideoPlayOrPauseClickListener( type);
            setSeekBarProgress(type);
        }
    }
    //播放时更新seekbar进度
    private void updateSeekBar( final String type) {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        //播放的为音频时更新音频进度
        if("audio".equals(type)){
            tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition/1000));
            voiceSeekbar.setProgress(progress);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                updataProgressCallback = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar(type);
                    }
                };
                handler.postDelayed(updataProgressCallback, 1000);
            }
        }
        //播放的为视频时更新视频进度
        if("video".equals(type)){
            tvVideoCurrentTime.setText(TimeUtil.parseDuration(currentPosition/1000));
            tvVideoTotalTime.setText(TimeUtil.parseDuration(duration/1000));
           videoSeekbar.setProgress(progress);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                updataProgressCallback = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar(type);
                    }
                };
                handler.postDelayed(updataProgressCallback, 1000);
            }
        }

    }
    //播放视频或音频文件
    private void playMidea(String mideaUri, final String type) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(mideaUri));
            if("video".equals(type)){
                mediaPlayer.setDisplay(svVideo.getHolder());
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    updateSeekBar(type);//更新进度
                    if("video".equals(type)){
                        svVideo.setAlpha(1);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //视频音频进度条拖动监听
    private void setSeekBarProgress(String type) {
        if("video".equals(type)){
            videoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    mediaPlayer.seekTo((int) (mediaPlayer.getDuration()*(progress/100f)));
                }
            });
        }

    }
    //设置查看大图按钮监听
    private void setImageShowAllClickListener( final JHTuiJianBean.ListBean bean) {
        ivImageShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComprehensiveCommentActivity.this, DrawActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("BigDraw", bean.getImage().getBig().get(0));
                bundle.putString("DownDraw", bean.getImage().getDownload_url().get(0));
                bundle.putString("Text",bean.getText());
                bundle.putString("Image",bean.getImage().getThumbnail_small().get(0));
                bundle.putString("Uri",bean.getShare_url());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    //设置分享按钮监听
    private void setClickToShare( final JHTuiJianBean.ListBean bean) {
        tvShare.setOnClickListener(new View.OnClickListener() {
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
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(bean.getShare_url());
                // 启动分享GUI
                oks.show(ComprehensiveCommentActivity.this);
            }
        });
    }
    //视频类型进度条区域控件点击事件监听
    private void setVideoPlayOrPauseClickListener( final String type) {
        tvVideoPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    tvVideoPlayOrPause.setBackgroundResource(R.mipmap.video_play);
                } else {
                    mediaPlayer.start();
                    updateSeekBar(type);
                    tvVideoPlayOrPause.setBackgroundResource(R.mipmap.ic_pause);
                }
            }
        });
    }
    //点击surfaceview事件
    private void setSurfaceViewClickListener() {
        svVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    if(rlVideoProgress.getVisibility()==View.VISIBLE){
                        rlVideoProgress.setVisibility(View.GONE);
                    }else{
                        rlVideoProgress.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    //设置用户信息和详细内容
    private void setUserMessage(){
        uBean = bean.getU();
        String headUri = uBean.getHeader().get(0);
        sdvUserHead.setImageURI(Uri.parse(headUri));
        tvUserName.setText(uBean.getName());
        //是否显示VIP
        if(uBean.isIs_v()){
            isV.setVisibility(View.VISIBLE);
        }else{
            isV.setVisibility(View.INVISIBLE);
        }
        if(uBean.isIs_vip()){
            isVIP.setVisibility(View.VISIBLE);
        }else{
            isVIP.setVisibility(View.INVISIBLE);
        }
        tvDetil.setText(bean.getText());//设置详细内容
        cbZan.setText(bean.getUp());//赞
        cbNozan.setText(bean.getDown()+"");//踩
        tvShare.setText(bean.getForward()+"");//分享
        tvPinglun.setText(bean.getComment());//评论
        if("".equals(bean.getComment())||"0".equals(bean.getComment())){
            tvCommentNone.setVisibility(View.VISIBLE);
        }else{
            tvCommentNone.setVisibility(View.INVISIBLE);
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
}
