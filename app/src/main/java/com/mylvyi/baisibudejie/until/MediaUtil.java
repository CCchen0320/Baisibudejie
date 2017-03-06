package com.mylvyi.baisibudejie.until;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by qf on 2016/11/4.
 */
public class MediaUtil {
    public static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer(){
        if(mediaPlayer==null
                ){
            mediaPlayer=new MediaPlayer();
        }
        return mediaPlayer;
    }

    public static void playMediaFile(String url,Context context){
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(url));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
