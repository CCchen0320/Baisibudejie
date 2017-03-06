package com.mylvyi.baisibudejie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DrawActivity extends AppCompatActivity {


    @Bind(R.id.draw)
    SimpleDraweeView draw;
    @Bind(R.id.vb_draw)
    WebView vbDraw;
    @Bind(R.id.bt_down)
    Button btDown;
    @Bind(R.id.tv_conmment)
    TextView tvConmment;
    @Bind(R.id.bt_comment)
    LinearLayout btComment;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.bt_share)
    LinearLayout btShare;
    private Bitmap downBitmap;
    private String bigDraw = null;
    private String text = null;
    private String image;
    private String uri;
    private String commentId;
    private int height;
    private int width;
    private boolean b;
    private byte[] bytes2;
    private String path;
    private Handler handler = new Handler();
    private boolean loadSuccess = false;
    private FileOutputStream flo = null;
    private boolean loa = false;
    private WebSettings webSettings = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        ButterKnife.bind(this);
        //ShareSdk配置
        ShareSDK.initSDK(this);

        String downDraw = null;
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            downDraw = bundle.getString("DownDraw");
            bigDraw = bundle.getString("BigDraw");
            text = bundle.getString("Text");
            image = bundle.getString("Image");
            uri = bundle.getString("Uri");
            commentId = bundle.getString("CommentId");
            height = bundle.getInt("Height");
            width = bundle.getInt("Width");
            Log.e("tet", "宽=" + width);
            Log.e("tet", "高=" + height);
            loadpath();



            vbDraw.getSettings().setUseWideViewPort(true);
            vbDraw.getSettings().setLoadWithOverviewMode(true);

            vbDraw.getSettings().setJavaScriptEnabled(true);
            // 获取WebSettings对象
            webSettings = vbDraw.getSettings();
            // 设置WebView支持运行普通的Javascript
            webSettings.setJavaScriptEnabled(true);

            vbDraw.setWebChromeClient(new WebChromeClient());
            // 点击超链接后不弹出浏览器窗口，而在WebView控件中加载URL
            vbDraw.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            vbDraw.loadUrl(bigDraw);






            WindowManager systemService = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            ViewGroup.LayoutParams layoutParams = draw.getLayoutParams();//得到控件大小
            //   ViewGroup.LayoutParams imagel = imageView.getLayoutParams();
            int wi = 0;
            if (height > width && height > 700 * 3) {
                int bili = height / width;
                wi = systemService.getDefaultDisplay().getWidth() * bili;
                layoutParams.height = wi;//设置高度
                // imagel.height = wi;
            } else {
                layoutParams.height = systemService.getDefaultDisplay().getHeight();
                //imagel.height = systemService.getDefaultDisplay().getHeight();
            }
            Log.e("llll", "onCreate: " + height);
            //  draw.setLayoutParams(layoutParams);
            //   imageView.setLayoutParams(imagel);


            if (height > 8000 * 3) {
                draw.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
            }
            loadDraw(bigDraw, height);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("test", "knull");
            Toast.makeText(DrawActivity.this, "图片不知道丢失了", Toast.LENGTH_SHORT).show();
        }


        downDraw(btDown);
        //  loadBitmapAlways();

    }

//    private void loadBitmapAlways() {
//        downDraw();
//        // loadBitmap();
//        if (!loadSuccess) {
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loadBitmapAlways();
//                    Log.e("vvv", "loadBitmapAlways: ");
//                }
//            }, 500);
//        }

//    }

    private void loadDraw(String bigDraw, final int height) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(bigDraw))//url
                // .setResizeOptions(new ResizeOptions(width * 64, height * 64))//图像质量，可以缩小大图片体积，提升UI的流畅程度
                .setProgressiveRenderingEnabled(true)
                .setPostprocessor(new BasePostprocessor() {
                    @Override
                    public void process(Bitmap bitmap) {
                        super.process(bitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();

//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        if (height > 7000 * 3) {
//                            options.inSampleSize = 16;
//                        } else {
//                            options.inSampleSize = 4;
//                        }
                        //                   bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                        downBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        SoftReference<Bitmap> srBitmap = new SoftReference<>(bitmap);

                    }
                })
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        draw.setController(controller);
//

        Log.e("tttt", "loadDraw: " + bigDraw);
    }

    @OnClick(R.id.bt_down)
    public void downDraw(View v) {
        try {
            flo = new FileOutputStream(path);
            b = downBitmap.compress(Bitmap.CompressFormat.JPEG, 100, flo);
            if (b) {
                Toast.makeText(DrawActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                Bitmap bm = BitmapFactory.decodeFile(path);//
                SoftReference<Bitmap> srBitmap = new SoftReference<>(bm);//
                bm = null;//释放强引用
                if (srBitmap.get() != null) {
                    //   imageView.setImageBitmap(srBitmap.get());
                }
                //如已被回收，则需要重新获取图片
                else {
                    Bitmap bmp = BitmapFactory.decodeFile(path);
                    srBitmap = new SoftReference<Bitmap>(bmp);
                    bmp = null;//释放强引用
                    System.gc();
                    //  imageView.setImageBitmap(srBitmap.get());//给控件设置值
                }//
                loadSuccess = true;//////
                Log.e("test", "bytes2.length : " + bytes2.length);

            } else {
                Toast.makeText(DrawActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(DrawActivity.this, "exception", Toast.LENGTH_SHORT).show();
        } finally {
            if (flo != null) {
                try {
                    flo.flush();
                    flo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @OnClick(R.id.bt_share)
    public void onshareClick() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(text);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(uri);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(image);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(uri);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(text);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(uri);

        // 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.bt_comment)
    public void dowmBitmap(View v) {

    }

    public void oncommentClick() {
        try {
            Intent intent = new Intent(DrawActivity.this, DrawCommentActivity.class);
            intent.putExtra("commentId", commentId);
            Log.e("gggggg", "id: " + commentId);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadpath() {
        String concat = bigDraw.substring(29, 50).replaceAll("/", "").concat(".jpg");
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + concat;
    }


//    @OnClick(R.id.bt_down)
//    public void load(View v) {
//        loa = true;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vbDraw.clearCache(true);
        vbDraw.destroy();
//        if (loa) {
//            File file = new File(path);
//            if (file.exists()) {
//                if (file.isFile()) {
//                    file.delete();
//                }
//            }
//        }
    }

    public void loadBitmap() {
//        if (b) {
//        try {
//          //  flo = new FileOutputStream(path);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        Bitmap bm = BitmapFactory.decodeFile(path);//
        SoftReference<Bitmap> srBitmap = new SoftReference<>(bm);//
        bm = null;//释放强引用
        if (srBitmap.get() != null) {
            //   imageView.setImageBitmap(srBitmap.get());
        }
        //如已被回收，则需要重新获取图片
        else {
            Bitmap bmp = BitmapFactory.decodeFile(path);
            srBitmap = new SoftReference<Bitmap>(bmp);
            bmp = null;//释放强引用
            System.gc();
            //    imageView.setImageBitmap(srBitmap.get());//给控件设置值
        }//
//        }
    }




}
