package com.mylvyi.baisibudejie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mylvyi.baisibudejie.bean.PingLunBean;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 11/08 0008.
 */
public class DuanZiCommentActivity extends AppCompatActivity {

    List<PingLunBean.NormalBean.ListBean> data = new ArrayList<>();
    @Bind(R.id.NewCommentlv)
    PullToRefreshListView NewCommentlv;
    private String TAG = "duanzilog";
    Handler handler = new Handler() {
        private DuanZiCommentAdapter adapter;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PingLunBean pingLunBean = (PingLunBean) msg.obj;
            Log.e(TAG, "handleMessage: " + pingLunBean);
            List<PingLunBean.NormalBean.ListBean> newList = pingLunBean.getNormal().getList();
            data.clear();
            data.addAll(newList);
            adapter = new DuanZiCommentAdapter(DuanZiCommentActivity.this, data);
            NewCommentlv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };
    private int count = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duanzicomment_layout);
        ButterKnife.bind(this);
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String json = HttpUntil.getPingLunJson(Integer.parseInt(getIntent().getStringExtra("id")), DuanZiCommentActivity.this);
                Log.e(TAG, "run: " + json);
                PingLunBean pingLunBean = JsonUntil.paresPinglunBean(json);
                Log.e(TAG, "run: " + pingLunBean);
                Message message = Message.obtain();
                message.obj = pingLunBean;
                handler.sendMessage(message);
            }
        });
        NewCommentlv.setMode(PullToRefreshBase.Mode.BOTH);
        NewCommentlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        String json = HttpUntil.getPingLunJson(Integer.parseInt(getIntent().getStringExtra("id")), DuanZiCommentActivity.this);
                        Log.e(TAG, "run: " + json);
                        PingLunBean pingLunBean = JsonUntil.paresPinglunBean(json);
                        Log.e(TAG, "run: " + pingLunBean);
                        Message message = Message.obtain();
                        message.obj = pingLunBean;
                        handler.sendMessage(message);
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        String json = HttpUntil.getPingLunJson(Integer.parseInt(getIntent().getStringExtra("id")) + count, DuanZiCommentActivity.this);
                        Log.e(TAG, "run: " + json);
                        PingLunBean pingLunBean = JsonUntil.paresPinglunBean(json);
                        Log.e(TAG, "run: " + pingLunBean);
                        Message message = Message.obtain();
                        message.obj = pingLunBean;
                        handler.sendMessage(message);
                    }
                });
                count += 20;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 1000);
            }
        });


    }
}
