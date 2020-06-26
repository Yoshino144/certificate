package com.pc.ks.Activity;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pc.ks.Fragment.FragmentForget;
import com.pc.ks.Fragment.FragmentLogin;
import com.pc.ks.Fragment.FragmentSign;
import com.pc.ks.R;
import com.pc.ks.Utils.FixedSpeedScroller;
import com.pc.ks.Utils.NoScrollViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private NoScrollViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentForget());
        list.add(new FragmentLogin());
        list.add(new FragmentSign());
        viewPager = findViewById(R.id.login_vp);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setScrollable(false);
        viewPager.setCurrentItem(1);
        controlViewPagerSpeed(this,viewPager);
    }

    public void setViewPager(int page){
        viewPager.setCurrentItem(page);
    }

    private void controlViewPagerSpeed(Context context, ViewPager viewpager) {
        try {
            Field mField;
            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller =
                    new FixedSpeedScroller(context, new AccelerateInterpolator());
            mScroller.setmDuration(400);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ArgbEvaluator evaluator = new ArgbEvaluator(); // ARGB求值器
        int evaluate = 0x00FFFFFF; // 初始默认颜色（透明白）
        if(position == 0){
            evaluate = (Integer) evaluator.evaluate(positionOffset, 0XFF6698cb, 0XFF50c7a2); // 根据positionOffset和第0页~第1页的颜色转换范围取颜色值
        }else if (position == 1) {
            evaluate = (Integer) evaluator.evaluate(positionOffset, 0XFF50c7a2, 0XFF7fccde); // 根据positionOffset和第0页~第1页的颜色转换范围取颜色值
        }else if(position == 2){
            evaluate = (Integer) evaluator.evaluate(positionOffset, 0XFF7fccde, 0XFF50c7a2); // 根据positionOffset和第1页~第2页的颜色转换范围取颜色值
        }
        ((View)viewPager.getParent()).setBackgroundColor(evaluate); // 为ViewPager的父容器设置背景色
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> mfragmentList;

        public MyAdapter(FragmentManager fm, List<Fragment>fragmentList) {
            super(fm);
            this.mfragmentList=fragmentList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mfragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mfragmentList.size();
        }
    }
}