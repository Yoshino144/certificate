package com.pc.ks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;
import com.pc.ks.Adapter.MainTabFragmentPagerAdapter;
import com.pc.ks.Fragment.BlankFragment_set;
import com.pc.ks.Fragment.BlankFragment_time;
import com.pc.ks.Fragment.BlankFragment_todo;
import com.pc.ks.Utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.UI_MODE_NIGHT_MASK;

public class MainActivity extends AppCompatActivity{

    private long exitTime = 0;
    private ViewPager mViewPager;
    private MenuItem menuItem;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private BottomNavigationView bottomNavigationView;
    private int page;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int color = getResources().getColor(R.color.pure_white);
        StatusBarUtil.setColor(this, color, 0);
        switch (isDarkTheme(this)) {
            case Configuration.UI_MODE_NIGHT_NO:
                LogUtils.d("当前不是深色模式");
                StatusBarUtil.setLightMode(this);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                LogUtils.d("当前是深色模式");
                StatusBarUtil.setDarkMode(this);
                break;
        }

        initTabView();  //注册底部菜单

        Intent intent =getIntent();
        page=intent.getIntExtra("page",0);
        setViewPager(page);

    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public void setViewPager(int page){
        mViewPager.setCurrentItem(page);
    }

    private void initTabView() {
        // find view
        mViewPager = findViewById(R.id.main_fragment_vp);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        // fragment
        mFragments = new ArrayList<>(2);
        mFragments.add(BlankFragment_todo.newInstance("TODO","1"));
        mFragments.add(BlankFragment_time.newInstance("TIME","2"));
        mFragments.add(BlankFragment_set.newInstance("SET","3"));
        // Adapter
        mAdapter = new MainTabFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        // Listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        bottomNavigationView.setOnNavigationItemSelectedListener(
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_todo:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_time:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_set:
                        mViewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        );
    }

    @Override //双击退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            LogUtils.d("当前主页数"+position);
            if (position!=0){
                Display();
            }
            if (menuItem != null) {
                menuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            menuItem = bottomNavigationView.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void Hide(){
        LogUtils.d("隐藏了导航栏");
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight());
    }

    public void Display(){
        LogUtils.d("显示了导航栏");
        bottomNavigationView.animate().translationY(0);
    }

    private int isDarkTheme(Context context) {
        return context.getResources().getConfiguration().uiMode & UI_MODE_NIGHT_MASK;
    }


}
