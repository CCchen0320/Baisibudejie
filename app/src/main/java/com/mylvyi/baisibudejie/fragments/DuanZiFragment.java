package com.mylvyi.baisibudejie.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.adapter.DuanZiAdapter;
import com.mylvyi.baisibudejie.bean.DuanZi;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Liang on 10-31 0031.
 */
public class DuanZiFragment extends Fragment {

    private static final String TAG = "lzy";
    private int count=0;
    @Bind(R.id.ptrlv)
    PullToRefreshListView ptrlv;
    private View view;
    private List<DuanZi.ListBean> data = new ArrayList<>();
    private DuanZiAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = (String) msg.obj;
            DuanZi duanZi = JsonUntil.paresDuanZiBean(json);
            if (duanZi != null) {
                data.clear();
                data.addAll(duanZi.getList());
                Log.e(TAG, "handleMessage: " + data);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.duanzi_layout, container, false);
            ButterKnife.bind(this, view);
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    String json = HttpUntil.getJHDuanZiJson(20,getContext());
                    Message message = Message.obtain();
                    message.obj = json;
                    handler.sendMessage(message);
                }
            });
            adapter = new DuanZiAdapter(getContext(), data);
            ptrlv.setAdapter(adapter);
            ptrlv.setMode(PullToRefreshBase.Mode.BOTH);
            ptrlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            String json = HttpUntil.getJHDuanZiJson(20,getContext());
                            Message message = Message.obtain();
                            message.obj = json;
                            handler.sendMessage(message);
                        }
                    });
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshView.onRefreshComplete();
                        }
                    }, 2000);
                }

                @Override
                public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            count+=20;
                            String json = HttpUntil.getJHDuanZiJson(20+count,getContext());
                            Message message = Message.obtain();
                            message.obj = json;
                            handler.sendMessage(message);
                        }
                    });
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshView.onRefreshComplete();
                        }
                    }, 2000);
                }
            });
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
