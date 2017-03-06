package com.mylvyi.baisibudejie;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mylvyi.baisibudejie.fragments.GuanZhuFragment;
import com.mylvyi.baisibudejie.fragments.JingHuaFragment;
import com.mylvyi.baisibudejie.fragments.MineFragment;
import com.mylvyi.baisibudejie.fragments.XinTieFragment;
import com.mylvyi.baisibudejie.until.MediaUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "lzy";
    @Bind(R.id.flMainActivity)
    FrameLayout flMainActivity;
    @Bind(R.id.rbMianJinghua)
    RadioButton rbMianJinghua;
    @Bind(R.id.rbMianXintie)
    RadioButton rbMianXintie;
    @Bind(R.id.rbMianGuanzhu)
    RadioButton rbMianGuanzhu;
    @Bind(R.id.rbMianWode)
    RadioButton rbMianWode;
    @Bind(R.id.rgMainActivity)
    RadioGroup rgMainActivity;
    @Bind(R.id.tvMianFabu)
    TextView tvMianFabu;
    @Bind(R.id.rootLl)
    LinearLayout rootLl;
    @Bind(R.id.ivLogo)
    ImageView ivLogo;
    @Bind(R.id.send_video)
    ImageView sendVideo;
    @Bind(R.id.send_picture)
    ImageView sendPicture;
    @Bind(R.id.send_text)
    ImageView sendText;
    @Bind(R.id.send_voice)
    ImageView sendVoice;
    @Bind(R.id.audit)
    ImageView audit;
    @Bind(R.id.send_link)
    ImageView sendLink;
    @Bind(R.id.tubiaoLl)
    LinearLayout tubiaoLl;
    @Bind(R.id.cancel_btn)
    Button cancelBtn;
    @Bind(R.id.centerRl)
    RelativeLayout centerRl;


    private List<RadioButton> rbs = new ArrayList<>();
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();
    private JingHuaFragment jingHuaFragment;
    private GuanZhuFragment guanZhuFragment;
    private XinTieFragment xinTieFragment;
    private MineFragment mineFragment;
    private Handler handler = new Handler();
    private AnimatorSet set;
    private ObjectAnimator open1;
    private ObjectAnimator open2;
    private ObjectAnimator close1;
    private ObjectAnimator close2;
    private ArrayList<Animator> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRadioButtonList();
        setRadioGrounpOnClickListener();
        initFragments();
        showCurrentFragment(jingHuaFragment);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(MediaUtil.mediaPlayer!=null&&MediaUtil.mediaPlayer.isPlaying()){
            MediaUtil.mediaPlayer.pause();
        }
    }

    public void LoadAnimator(){
        set = new AnimatorSet();
        list = new ArrayList<>();
        open1 = ObjectAnimator.ofFloat(tubiaoLl, "translationY", -1500f, 100f);
        open2 = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 100f, 0);
        close1 = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 0, -100f);
        close2 = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 100f, 1500f);
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        jingHuaFragment = new JingHuaFragment();
        guanZhuFragment = new GuanZhuFragment();
        xinTieFragment = new XinTieFragment();
        mineFragment = new MineFragment();
        fragments.add(jingHuaFragment);
        fragments.add(xinTieFragment);
        fragments.add(guanZhuFragment);
        fragments.add(mineFragment);
    }

    //显示当前fragment
    private void showCurrentFragment(Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            ft.add(R.id.flMainActivity, fragment);
        }
        for (Fragment f : fragments) {
            if (fragment == f) {
                ft.show(f);
            } else {
                ft.hide(f);
            }
        }
        ft.commit();
    }

    //初始化radiobutton列表
    private void initRadioButtonList() {
        rbs.add(rbMianGuanzhu);
        rbs.add(rbMianJinghua);
        rbs.add(rbMianWode);
        rbs.add(rbMianXintie);
    }

    //RadioGrounp的点击事件
    private void setRadioGrounpOnClickListener() {
        rgMainActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMianJinghua:
                        showCurrentFragment(jingHuaFragment);
                        setRadioButtonTextColor(rbMianJinghua);
                        break;
                    case R.id.rbMianXintie:
                        showCurrentFragment(xinTieFragment);
                        setRadioButtonTextColor(rbMianXintie);
                        break;
                    case R.id.rbMianGuanzhu:
                        showCurrentFragment(guanZhuFragment);
                        setRadioButtonTextColor(rbMianGuanzhu);
                        break;
                    case R.id.rbMianWode:
                        showCurrentFragment(mineFragment);
                        setRadioButtonTextColor(rbMianWode);
                        break;
                }
            }
        });
    }

    //设置radiobutton点击时的颜色
    private void setRadioButtonTextColor(RadioButton rb) {
        for (int i = 0; i < rbs.size(); i++) {
            RadioButton radioButton = rbs.get(i);
            if (radioButton.getId() == rb.getId()) {
                radioButton.setTextColor(getResources().getColor(R.color.rb_main_pressed));
            } else {
                radioButton.setTextColor(getResources().getColor(R.color.rb_main_normal));
            }

        }
    }


    @OnClick({R.id.tvMianFabu, R.id.cancel_btn, R.id.send_video, R.id.send_picture, R.id.send_text, R.id.send_voice, R.id.audit, R.id.send_link})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMianFabu:
                rootLl.setVisibility(View.GONE);
                centerRl.setVisibility(View.VISIBLE);
//                set = new AnimatorSet();
//                list = new ArrayList<>();
                LoadAnimator();
                list.clear();
                list.add(open1);
                list.add(open2);
                set.playSequentially(list);
                set.start();
                cancelBtn.setVisibility(View.VISIBLE);
//            ObjectAnimator translationY = ObjectAnimator.ofFloat(tubiaoLl, "translationY", -1500f, 100f);
//            translationY.setDuration(500);
//            translationY.start();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ObjectAnimator translationY = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 100f, 0);
//                    translationY.setDuration(300);
//                    translationY.start();
//                }
//            },500);
                break;
            case R.id.cancel_btn:
                LoadAnimator();
                list.clear();
                list.add(close1);
                list.add(close2);
                set.playSequentially(list);
                set.start();
                cancelBtn.setVisibility(View.GONE);
//            ObjectAnimator translationY = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 0, -100f);
//            translationY.setDuration(300);
//            translationY.start();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ObjectAnimator translationY = ObjectAnimator.ofFloat(tubiaoLl, "translationY", 100f, 1500f);
//                    translationY.setDuration(500);
//                    translationY.start();
//                }
//            },300);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        centerRl.setVisibility(View.GONE);
                        rootLl.setVisibility(View.VISIBLE);
                    }
                }, 500);
                break;
            default:
                Toast.makeText(MainActivity.this, "相关功能维护中", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.send_video:
//                break;
//            case R.id.send_picture:
//                break;
//            case R.id.send_text:
//                break;
//            case R.id.send_voice:
//                break;
//            case R.id.audit:
//                break;
//            case R.id.send_link:
//                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            if (centerRl.getVisibility() == View.VISIBLE) {
                LoadAnimator();
                list.clear();
                list.add(close1);
                list.add(close2);
                set.playSequentially(list);
                set.start();
                cancelBtn.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        centerRl.setVisibility(View.GONE);
                        rootLl.setVisibility(View.VISIBLE);
                    }
                }, 500);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

