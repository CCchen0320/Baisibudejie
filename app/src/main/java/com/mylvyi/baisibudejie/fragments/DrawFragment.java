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
import com.mylvyi.baisibudejie.adapter.DrawAdapter;
import com.mylvyi.baisibudejie.bean.DrawBean;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class DrawFragment extends Fragment {
    public static final int MSG_QUERY_JSON_GOT = 21;
    private static final String TAG = "test";
    @Bind(R.id.dr_ptrl)
    PullToRefreshListView drPtrl;

    private View view;
    private int drawdata = 0;
    private DrawAdapter drawAdapter;
    private DrawBean drawBean = null;
    private List<DrawBean.ListBean> data = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what = 21) {
                case MSG_QUERY_JSON_GOT:
                    String drawString = (String) msg.obj;
                    //得到drawBean
                    drawBean = new JsonUntil().paresDrawBean(drawString);
                    if (drawBean != null) {
                        List<DrawBean.ListBean> listBeen = drawBean.getList();
                        data.clear();
                        data.addAll(listBeen);
                        drawAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "shuju: " + drawString);
                        Toast.makeText(getActivity(), "数据异常，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.draw_fragment, container, false);
            ButterKnife.bind(this, view);


            //获得数据
            initdata(20);


            drawAdapter = new DrawAdapter(getContext(), data);
            drPtrl.setAdapter(drawAdapter);
            drPtrl.setMode(PullToRefreshBase.Mode.BOTH);
            drPtrl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initdata(20);
                            drPtrl.onRefreshComplete();
                        }
                    }, 3000);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initdata(drawdata+=20);
                            drPtrl.onRefreshComplete();
                            Log.e(TAG, "run: " + drawdata);
                        }
                    }, 3000);
                }
            });

        }
        return view;
    }


    public void initdata(final int pages) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String drawString = new HttpUntil().getjinghuaDrawString(pages,getContext());
                Message msg = handler.obtainMessage();
                msg.what = MSG_QUERY_JSON_GOT;
                msg.obj = drawString;
                handler.sendMessage(msg);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
