package com.mylvyi.baisibudejie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mylvyi.baisibudejie.adapter.DrawCommentAdapter;
import com.mylvyi.baisibudejie.bean.PingLunBean;
import com.mylvyi.baisibudejie.until.HttpUntil;
import com.mylvyi.baisibudejie.until.JsonUntil;
import com.mylvyi.baisibudejie.until.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawCommentActivity extends AppCompatActivity {

    @Bind(R.id.comment_ptrl)
    PullToRefreshListView commentPtrl;
    private static final int MSG_PINGLUN =1;
    private PingLunBean pingLunBean;
    private List<PingLunBean.NormalBean.ListBean> data = new ArrayList<>();
    private Handler handler =new Handler(){


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_PINGLUN:
                    String pinglunString = (String) msg.obj;
                    pingLunBean = JsonUntil.paresPinglunBean(pinglunString);
                    Log.e("gggggg", pinglunString.toString() );
                   // Log.e("gggggg", "bean"+pingLunBean.getNormal().getList().get(0).getType() );
                    if(pingLunBean!=null){
                        List<PingLunBean.NormalBean.ListBean> list = pingLunBean.getNormal().getList();
                    //    Log.e("gggggg", "bean"+pingLunBean.toString() );
                        data.clear();
                        data.addAll(list);
                        drawCommentAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(DrawCommentActivity.this, "数据异常，请检查网络", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    private DrawCommentAdapter drawCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String commentId = intent.getStringExtra("commentId");
        Log.e("gggggg", "评论"+commentId );
        initCommentData(commentId);

        //适配器
        drawCommentAdapter = new DrawCommentAdapter(DrawCommentActivity.this,data);
        commentPtrl.setAdapter(drawCommentAdapter);

    }

    private void initCommentData(final String commentId) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String pingLunJson = HttpUntil.getPingLunJson(Integer.parseInt(commentId), DrawCommentActivity.this);
                Log.e("gggggg", "json"+pingLunJson );
                Message msg = handler.obtainMessage();
                msg.what=MSG_PINGLUN;
                msg.obj=pingLunJson;
                handler.sendMessage(msg);
            }
        });

    }




}
