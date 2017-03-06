package com.mylvyi.baisibudejie.until;

/**
 * Created by qf on 2016/11/2.
 */
public class TimeUtil {
    //传入bean中的duration后获得00：00格式的时间
    public static String parseDuration(int duration){
        int minutes = duration / 60;
        int seconds = duration % 60;
        String secsStr = seconds < 10 ? "0" + seconds : "" + seconds;
        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        return minutesStr + ":" + secsStr;
    }
}
