package com.mylvyi.baisibudejie.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.adapter.ComprehensiveAdapter;
import com.mylvyi.baisibudejie.bean.JHTuiJianBean;
import com.mylvyi.baisibudejie.config.Config;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/3.
 */
public class JHWangHongFragment extends Fragment {


    View view;
    private int count=20;
    private int newCount=0;
    private  List<JHTuiJianBean.ListBean> data = new ArrayList<>();
    private  static final String TAG = "testv";
    private ComprehensiveAdapter comprehensiveAdapter;
    private IntentFilter intentFilter;
    private MyReceiver myReceiver;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.ON_JSON_GOT_SUCCESS:
                    onJsonGotSuccess(msg);
                    break;
                case Config.ON_JSON_GOT_FAIL:
                    ptrlComprehensive.onRefreshComplete();
                    getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
                    Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
                    break;
                case Config.DOWN_FRESH_MESSAGE:
                    onPullDownRefreshSuccess(msg);
                    getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
                    break;
                case Config.UP_FRESH_MESSAGE:
                    onPullUpRefreshSuccess(msg);
                    getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
                    break;
                case Config.NO_MORE_MESSAGE:
                    ptrlComprehensive.onRefreshComplete();
                    getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
                    Toast.makeText(getActivity(), "没有更多数据啦！~", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private PullToRefreshListView ptrlComprehensive;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.comprehensive_layout, container, false);
            ButterKnife.bind(this, view);
            ptrlComprehensive = ((PullToRefreshListView) view.findViewById(R.id.ptrlComprehensive));
            setFrescoGetImagePipelineOnPtrStopScroll();
            comprehensiveAdapter = new ComprehensiveAdapter(handler, getContext(), data);
            ptrlComprehensive.setAdapter(comprehensiveAdapter);
            getDataFromNet(Config.ON_JSON_GOT_SUCCESS,20);
            setPtrListViewRefreshEvent();
        }
        return view;
    }

    //设置ptr刷新事件
    private void setPtrListViewRefreshEvent() {
        ptrlComprehensive.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout layoutProxy = ptrlComprehensive.getLoadingLayoutProxy();
        layoutProxy.setPullLabel("下拉可以刷新");
        layoutProxy.setReleaseLabel("松开立即刷新");
        layoutProxy.setRefreshingLabel("正在刷新...");
        layoutProxy.setLastUpdatedLabel("最后更新 :"+new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        layoutProxy.setLoadingDrawable(getResources().getDrawable(R.mipmap.list_view_pull));//配置刷新图标
        ptrlComprehensive.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataFromNet(Config.DOWN_FRESH_MESSAGE,19);
                getContext().sendBroadcast(new Intent(Config.ON_REFRESH_START));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                count+=10;
                getDataFromNet(Config.UP_FRESH_MESSAGE,count);
                getContext().sendBroadcast(new Intent(Config.ON_REFRESH_START));
            }
        });
    }
    //设置fresco在滚动时停止加载数据
    private void setFrescoGetImagePipelineOnPtrStopScroll() {
        ptrlComprehensive.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        if (!Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().pause();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //ImageLoader.resumeLoader();
                        if (Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().resume();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    //设置上拉刷新事件
    private void onPullUpRefreshSuccess(Message msg) {
        String jsonNew2 = (String) msg.obj;
        JHTuiJianBean beanNew2 = JsonUntil.paresJHTuiJianBean(jsonNew2);
        if (beanNew2 != null) {
            List<JHTuiJianBean.ListBean> bean2 = beanNew2.getList();
            boolean add=true;
            newCount=0;
            for (int i = 0; i < bean2.size(); i++) {
                add=true;
                JHTuiJianBean.ListBean beans = bean2.get(i);
                for(int j = 0; j < data.size(); j++){
                    if(beans.getId().equals(data.get(j).getId())){
                        add=false;
                    }
                }
                if(add){
                    data.add(beans);
                    newCount++;
                }
            }
            ptrlComprehensive.onRefreshComplete();
            if(newCount>0){
                comprehensiveAdapter.updata(data);
            }else{
                Toast.makeText(getActivity(), "暂时没有新数据哦~", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //设置下拉刷新事件
    private void onPullDownRefreshSuccess(Message msg) {
        String jsonNew = (String) msg.obj;
        JHTuiJianBean beanNew = JsonUntil.paresJHTuiJianBean(jsonNew);
        if (beanNew != null) {
            boolean add=true;
            newCount=0;
            List<JHTuiJianBean.ListBean> bean1 = beanNew.getList();
            for (int i = 0; i < bean1.size(); i++) {
                add=true;
                JHTuiJianBean.ListBean beans = bean1.get(i);
                for(int j = 0; j < data.size(); j++){
                    if(beans.getId().equals(data.get(j).getId())){
                        add=false;
                    }
                }
                if(add){
                    data.add(0,beans);
                    newCount++;
                }
            }
            ptrlComprehensive.onRefreshComplete();
            if(newCount>0){
                comprehensiveAdapter.updata(data);
                Toast.makeText(getActivity(), "更新了"+newCount+"条数据", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "暂时没有新数据哦~", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //设置首次成功获取数据事件
    private void onJsonGotSuccess(Message msg) {
        String json = (String) msg.obj;
        JHTuiJianBean bean = JsonUntil.paresJHTuiJianBean(json);
        Log.e(TAG, "JHTuiJianBean: " + bean.getList().size());
        if (bean != null) {
            List<JHTuiJianBean.ListBean> listBeen = bean.getList();
            data.clear();
            data.addAll(listBeen);
            Log.e(TAG, "handleMessage: "+data.size() );
            comprehensiveAdapter.notifyDataSetChanged();
        }
    }
    //从网络加载数据
    public void getDataFromNet(final int what, final int count) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = HttpUntil.getJHWangHongJson(count,getContext());
                    if(json==null||"".equals(json)){
                        Message message = handler.obtainMessage(Config.ON_JSON_GOT_FAIL);
                        handler.sendMessage(message);
                        return;
                    }
                    if(count>=100){
                        Message message = handler.obtainMessage(Config.NO_MORE_MESSAGE);
                        handler.sendMessage(message);
                    }else{
                        Log.e(TAG, "json: "+json );
                        Message message = handler.obtainMessage(what);
                        message.obj = json;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = handler.obtainMessage(Config.ON_JSON_GOT_FAIL);
                    handler.sendMessage(message);
                }
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
        getContext().unregisterReceiver(myReceiver);
    }
    //广播控制viewpage翻页时停止播放
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Config.ON_PAGE_CHANGED.equals(action)) {
                comprehensiveAdapter.resetCurrentPlayPosition();
            }
        }
    }

}
