package com.mylvyi.baisibudejie.fragments;

import android.content.Intent;
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
import com.mylvyi.baisibudejie.adapter.VoiceAdapter;
import com.mylvyi.baisibudejie.bean.XTShengyinBean;
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
 * Created by qf on 2016/11/2.
 */
public class XTVoiceFragment extends Fragment {

    View view;
    private int count=20;
    private int newCount=0;
    public static final String TAG="testv";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Config.ON_JSON_GOT_SUCCESS:
                    onJsonGotSuccess(msg);
                    break;
                case Config.ON_JSON_GOT_FAIL:
                    ptlVoice.onRefreshComplete();
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
                    ptlVoice.onRefreshComplete();
                    getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
                    Toast.makeText(getActivity(), "没有更多数据啦！~", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    public List<XTShengyinBean.ListBean> data=new ArrayList<>();
    private VoiceAdapter voiceAdapter;
    private PullToRefreshListView ptlVoice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.voice_fragment, container, false);
            ButterKnife.bind(this, view);
            ptlVoice = ((PullToRefreshListView) view.findViewById(R.id.ptlVoice));
            setFrescoGetImagePipelineOnPtrStopScroll();
            setPtrListViewRefreshEvent();
            getDataFromNet(Config.ON_JSON_GOT_SUCCESS,20);
            voiceAdapter = new VoiceAdapter(data,getContext(),handler);
            ptlVoice.setAdapter(voiceAdapter);
        }
        return view;
    }
    //从网络获取数据
    private void getDataFromNet(final int what, final int count) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = HttpUntil.getXTShengYinJson(count,getContext());
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
    //首次成功获取数据时
    private void onJsonGotSuccess(Message msg) {
        String json = (String) msg.obj;
        XTShengyinBean bean = JsonUntil.paresXTShengyinBean(json);
        Log.e(TAG, "XTShengyinBean: "+bean.getList().size() );
        if(bean!=null){
            List<XTShengyinBean.ListBean> listBeen = bean.getList();
            data.clear();
            data.addAll(listBeen);
            voiceAdapter.updata(data);
        }
    }
    //设置ptr刷新事件
    private void setPtrListViewRefreshEvent() {
        ptlVoice.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout layoutProxy = ptlVoice.getLoadingLayoutProxy();
        layoutProxy.setPullLabel("下拉可以刷新");
        layoutProxy.setReleaseLabel("松开立即刷新");
        layoutProxy.setRefreshingLabel("正在刷新...");
        layoutProxy.setLastUpdatedLabel("最后更新 :"+new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        layoutProxy.setLoadingDrawable(getResources().getDrawable(R.mipmap.list_view_pull));//配置刷新图标
        ptlVoice.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    //设置上拉刷新事件
    private void onPullUpRefreshSuccess(Message msg) {
        String jsonNew2 = (String) msg.obj;
        XTShengyinBean beanNew2 = JsonUntil.paresXTShengyinBean(jsonNew2);
        if (beanNew2 != null) {
            List<XTShengyinBean.ListBean> bean2 = beanNew2.getList();
            boolean add=true;
            newCount=0;
            for (int i = 0; i < bean2.size(); i++) {
                add=true;
                XTShengyinBean.ListBean beans = bean2.get(i);
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
            getContext().sendBroadcast(new Intent(Config.ON_REFRESH_COMPLETED));
            if(newCount>0){
                voiceAdapter.updata(data);
            }else{
                Toast.makeText(getActivity(), "暂时没有新数据哦~", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //设置下拉刷新事件
    private void onPullDownRefreshSuccess(Message msg) {
        String jsonNew = (String) msg.obj;
        XTShengyinBean beanNew = JsonUntil.paresXTShengyinBean(jsonNew);
        if (beanNew != null) {
            boolean add=true;
            newCount=0;
            List<XTShengyinBean.ListBean> bean1 = beanNew.getList();
            for (int i = 0; i < bean1.size(); i++) {
                add=true;
                XTShengyinBean.ListBean beans = bean1.get(i);
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
            ptlVoice.onRefreshComplete();
            if(newCount>0){
                voiceAdapter.updata(data);
                Toast.makeText(getActivity(), "更新了"+newCount+"条数据", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "暂时没有新数据哦~", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //设置fresco在滚动时停止加载数据
    private void setFrescoGetImagePipelineOnPtrStopScroll() {
        ptlVoice.setOnScrollListener(new AbsListView.OnScrollListener() {
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

}
