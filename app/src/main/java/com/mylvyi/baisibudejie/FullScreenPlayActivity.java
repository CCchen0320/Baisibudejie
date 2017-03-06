package com.mylvyi.baisibudejie;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mylvyi.baisibudejie.until.MediaUtil;
import com.mylvyi.baisibudejie.until.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullScreenPlayActivity extends AppCompatActivity  {

    @Bind(R.id.sfVideo)
    SurfaceView sfVideo;
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
    private MediaPlayer mediaPlayer;
    private String TAG = "fff";
    private int currentPositionBefor;
    private Handler handler=new Handler();
    private Runnable updataProgressCallback;
    private int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_play);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String duration = bundle.getString("dur");
        currentPositionBefor = bundle.getInt("cup");
        tvSumTime.setText(duration);
        mediaPlayer= MediaUtil.getMediaPlayer();

        addSVHolderCallback();

        videoProgressOnClick();
    }

    private void addSVHolderCallback() {
        sfVideo.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.start();
                mediaPlayer.seekTo(currentPosition);
                mediaPlayer.setDisplay(sfVideo.getHolder());
                unDateSeekBarPregress();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    //进度条 控制播放 显示与否 及点击事件监听 开启全屏播放
    private void videoProgressOnClick() {
        sfVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlVideoProgress.getVisibility() == View.INVISIBLE) {
                    rlVideoProgress.setVisibility(View.VISIBLE);
                    setVideoPlayOrPauseListenner();//播放按钮监听
                    setSeekBarChangeListener();// 进度条拖动监听

                    invisibleProgress();
                } else {
                    rlVideoProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //自动隐藏进度条
    private void invisibleProgress() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rlVideoProgress.setVisibility(View.INVISIBLE);
            }
        },6000);
    }

    //进度条同步更新
    private void unDateSeekBarPregress() {
        currentPosition = mediaPlayer.getCurrentPosition();
        int progress = (int) (currentPosition * 100 / (float) mediaPlayer.getDuration());
        tvCurrentTime.setText(TimeUtil.parseDuration(currentPosition / 1000));
        videoSeekbar.setProgress(progress);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            updataProgressCallback = new Runnable() {
                @Override
                public void run() {
                    unDateSeekBarPregress();
                }
            };
            handler.postDelayed(updataProgressCallback, 1000);
        }
    }
    // 进度条拖动监听
    private void setSeekBarChangeListener() {
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
                mediaPlayer.seekTo((int) (mediaPlayer.getDuration() * (progress / 100f)));
            }
        });
    }
    //设置播放按钮监听
    private void setVideoPlayOrPauseListenner() {
        tvPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    tvPlayOrPause.setBackgroundResource(R.mipmap.video_play);
                } else {
                    mediaPlayer.start();
                    tvPlayOrPause.setBackgroundResource(R.mipmap.video_pause);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}
