package com.mylvyi.baisibudejie.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mylvyi.baisibudejie.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 11/02 0002.
 */
public class MineFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.mine_layout, container, false);
            ButterKnife.bind(this, view);
            toolbar.setNavigationIcon(R.mipmap.my_coins_normal);
            toolbar.setTitle("我的");
            toolbar.setTitleMarginStart(300);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.inflateMenu(R.menu.mine_setting);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(getContext(), "功能暂无", Toast.LENGTH_SHORT).show();
                    return false;
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
