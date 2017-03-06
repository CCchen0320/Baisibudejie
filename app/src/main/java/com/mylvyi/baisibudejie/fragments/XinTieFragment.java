package com.mylvyi.baisibudejie.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.mylvyi.baisibudejie.R;
import com.mylvyi.baisibudejie.config.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/11/1.
 */
public class XinTieFragment extends Fragment {

    public static final String TAG = "testv";
    @Bind(R.id.tvMainCup)
    TextView tvMainCup;
    @Bind(R.id.tvMainTitle)
    TextView tvMainTitle;
    @Bind(R.id.tvMainRotate)
    TextView tvMainRotate;
    @Bind(R.id.tvMainRandom)
    TextView tvMainRandom;
    @Bind(R.id.tlMainActivity)
    TabLayout tlMainActivity;
    @Bind(R.id.vpMainActivity)
    ViewPager vpMainActivity;
    private View view;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"全部", "视频", "图片", "段子", "网红", "美女", "冷知识", "游戏", "声音"};
//    private String[] titles = new String[]{"声音"};

    private XTVoiceFragment voiceFragment;
    private XTQuanBuFragment xtQuanBuFragment;
    private XTShiPinFragment xtShiPinFragment;
    private XTTuPianFragment xtTuPianFragment;
    private XTDuanZiFragment xtDuanZiFragment;
    private XTWangHongFragment xtWangHongFragment;
    private XTMeiNvFragment xtMeiNvFragment;
    private XTLengZhiShiFragment xtLengZhiShiFragment;
    private XTYouXiFragment xtYouXiFragment;
    private MyReceiver myReceiver;
    private IntentFilter intentFilter;
    private RotateAnimation rotateAnimation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.jinghua_fragment, container, false);
            ButterKnife.bind(this, view);
            initFragments();
            initViewPagerAndTabLayout();
            initRotateAnimation();
        }
        return view;
    }


    private void initViewPagerAndTabLayout() {
        tlMainActivity.setupWithViewPager(vpMainActivity);

        vpMainActivity.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        vpMainActivity.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getContext().sendBroadcast(new Intent(Config.ON_PAGE_CHANGED));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initFragments() {
        xtQuanBuFragment = new XTQuanBuFragment();
        xtShiPinFragment = new XTShiPinFragment();
        xtTuPianFragment = new XTTuPianFragment();
        xtDuanZiFragment = new XTDuanZiFragment();
        xtWangHongFragment = new XTWangHongFragment();
        xtMeiNvFragment = new XTMeiNvFragment();
        xtLengZhiShiFragment = new XTLengZhiShiFragment();
        xtYouXiFragment = new XTYouXiFragment();
        voiceFragment = new XTVoiceFragment();
        fragments.add(xtQuanBuFragment);
        fragments.add(xtShiPinFragment);
        fragments.add(xtTuPianFragment);
        fragments.add(xtDuanZiFragment);
        fragments.add(xtWangHongFragment);
        fragments.add(xtMeiNvFragment);
        fragments.add(xtLengZhiShiFragment);
        fragments.add(xtYouXiFragment);
        fragments.add(voiceFragment);
    }

    private void initRotateAnimation() {
        rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onStart() {
        super.onStart();
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ON_REFRESH_START);
        intentFilter.addAction(Config.ON_REFRESH_COMPLETED);
        getContext().registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getContext().unregisterReceiver(myReceiver);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Config.ON_REFRESH_START.equals(action)) {
                tvMainRotate.startAnimation(rotateAnimation);
            }
            if (Config.ON_REFRESH_COMPLETED.equals(action)) {
                tvMainRotate.clearAnimation();
            }
        }
    }
}
