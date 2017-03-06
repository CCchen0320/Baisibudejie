package com.mylvyi.baisibudejie.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class HttpUntil {

    public static String getVideoJH(String pages, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.8/0-" + pages + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    private static String getStringByOkhttp(String url, Context context) {
        String json = "";
        CacheControl cacheControl ;
        if(isNetworkConnected(context)){
            cacheControl = CacheControl.FORCE_NETWORK;
        }else{
            cacheControl = CacheControl.FORCE_CACHE;
        }
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(new Cache(context.getCacheDir(), 1024 * 1024 * 30))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();//1、创建OkHttpClient

        Request request = new Request.Builder()//2、通过构造器构造Request
                .url(url)//配置URL
                .cacheControl(cacheControl)
                .tag("tag")//为request设置标签，将来可以通过标签找到请求，并取消之
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();//3、执行Request

            //4、解析Response
            if (response.isSuccessful()) {
                json = response.body().string();//拿到字符流，还可以是bytes(),byteStream()
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static String getjinghuaDrawString(int num, Context context) {
        String drjson = getStringByOkhttp("http://s.budejie.com/topic/list/jingxuan/10/budejie-android-6.5.8/0-" + num + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return drjson;
    }
    public static String getJHShiPinJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHDuanZiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/jingxuan/29/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHLengZhiShiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/3176/hot/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHMeiNvJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/117/hot/budejie-android-6.5.8/0-20.json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHPaiHangJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/remen/1/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHSheHuiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/473/hot/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHTuiJianJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHWangHongJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/3096/hot/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getJHYouXiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/164/hot/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTShengYinJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/zuixin/31/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTQuanBuJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/zuixin/1/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTShiPinJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/zuixin/41/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTDuanZiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/zuixin/29/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTTuPianJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/list/zuixin/10/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTWangHongJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/3096/new/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }


    public static String getXTMeiNvJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/117/new/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTLengZhiShiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/3176/new/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getXTYouXiJson(int count, Context context) {
        String json = getStringByOkhttp("http://s.budejie.com/topic/tag-topic/164/new/budejie-android-6.5.8/0-" + count + ".json?market=baidu&ver=6.5.8&visiting=&os=6.0&appname=baisibudejie&client=android&udid=352090060095762&mac=02%3A00%3A00%3A00%3A00%3A00", context);
        return json;
    }

    public static String getPingLunJson(int itemID, Context context) {
        String json = getStringByOkhttp("http://c.api.budejie.com/topic/comment_list/" + itemID + "/0/budejie-android-6.5.8/0-20.json", context);
        return json;
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}
