package com.mylvyi.baisibudejie.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mylvyi.baisibudejie.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 11/01 0001.
 */
public class GuanZhuFragment extends Fragment {

    @Bind(R.id.btn1)
    Button btn1;
    @Bind(R.id.btn2)
    Button btn2;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.guanzhu_layout, container, false);
            ButterKnife.bind(this, view);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.post_right_follow);
            toolbar.setTitle("关注");
            toolbar.setTitleMarginStart(300);
            toolbar.setTitleTextColor(Color.WHITE);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View v) {
        Toast.makeText(getContext(), "功能暂无", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
