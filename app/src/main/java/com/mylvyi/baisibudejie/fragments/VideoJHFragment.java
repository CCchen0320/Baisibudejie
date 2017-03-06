package com.mylvyi.baisibudejie.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.adapter.VideoJHAdapter;
import com.mylvyi.baisibudejie.bean.VideoJingHua;
import com.mylvyi.baisibudejie.config.Config;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class VideoJHFragment extends Fragment {

    private static final String TAG = "www";
    @Bind(R.id.prtLVideoJH)
    PullToRefreshListView prtLVideoJH;

    private static final int FIRST_GOT_MESSAGE = 10;
    private static final int DOWNFRESH_MESSAGE = 20;
    private static final int UPFRESH_MESSAGE = 30;
    private String firstId = null;
    private int newPages=30;
    private View view;
    private VideoJingHua videoJingHua;
    private List<VideoJingHua.ListBean> data = new ArrayList<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FIRST_GOT_MESSAGE:
                    getVideoJinghuaBean(msg);
                    if (videoJingHua != null) {
                        data = videoJingHua.getList();
                        firstId = data.get(0).getId();
                        videoJHAdapter.setData(data);
                    } else {
                        Log.e(TAG, "handleMessage: 数据解析异常");
                    }
                    break;
                case DOWNFRESH_MESSAGE:
                    getVideoJinghuaBean(msg);
                    if (videoJingHua != null) {
                        String downId = videoJingHua.getList().get(0).getId();
                        if (downId.equals(firstId)) {
                            Toast.makeText(getActivity(), "暂无新数据", Toast.LENGTH_SHORT).show();
                            prtLVideoJH.onRefreshComplete();
                        } else {
                            List<VideoJingHua.ListBean> listBeen = videoJingHua.getList();
                            videoJHAdapter.setData(listBeen);
                            prtLVideoJH.onRefreshComplete();
                        }
                    }
                    break;
                case UPFRESH_MESSAGE:
                    getVideoJinghuaBean(msg);
                    Log.e(TAG, "handleMessage: " );
                    if (videoJingHua!=null){
                        List<VideoJingHua.ListBean> listBeen = videoJingHua.getList();
                        videoJHAdapter.setData(listBeen);
                        prtLVideoJH.onRefreshComplete();
                        newPages+=10;
                        Log.e(TAG, "handleMessage: newpage"+newPages );
                    }
                    break;
            }

        }
    };


    private VideoJHAdapter videoJHAdapter;
    private MyReceiver myReceiver;
    private IntentFilter intentFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.videojinghua_fragment, container, false);
            ButterKnife.bind(this, view);
            getJson(20, FIRST_GOT_MESSAGE);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            videoJHAdapter = new VideoJHAdapter(getContext(), data,display);
            prtLVideoJH.setAdapter(videoJHAdapter);

            prtLVideoJH.setMode(PullToRefreshBase.Mode.BOTH);
            ILoadingLayout layoutProxy = prtLVideoJH.getLoadingLayoutProxy();
            layoutProxy.setPullLabel("下拉可以刷新");
            layoutProxy.setReleaseLabel("松开立即刷新");
            layoutProxy.setRefreshingLabel("正在刷新...");
            layoutProxy.setLastUpdatedLabel("最后更新 :"+new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
            layoutProxy.setLoadingDrawable(getResources().getDrawable(R.mipmap.list_view_pull));//配置刷新图标
            prtLVideoJH.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getJson(20, DOWNFRESH_MESSAGE);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    Log.e(TAG, "onPullUpToRefresh: --====" );
                    if (newPages>91){
                        Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
                        prtLVideoJH.onRefreshComplete();
                        return;
                    }
                    getJson(newPages,UPFRESH_MESSAGE);

                }
            });

        }

        return view;
    }

    private void getVideoJinghuaBean(Message msg) {
        String json = (String) msg.obj;
        if (json == null || json.equals("")) {
            Toast.makeText(getActivity(), "网络数据异常，请检查您的网络设置", Toast.LENGTH_SHORT).show();
            prtLVideoJH.onRefreshComplete();
            return;
        }
        videoJingHua = JsonUntil.parseVideoJinghua(json);
    }

    private void getJson(final int pages, final int type) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: type "+type );
                String json = HttpUntil.getVideoJH(pages + "",getContext());
                Message message = handler.obtainMessage();
                message.what = type;
                message.obj = json;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ON_PAGE_CHANGED);
        getContext().registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Config.ON_PAGE_CHANGED.equals(action)) {
                videoJHAdapter.resetCurrentPlayPosition();
            }
        }
    }
}
