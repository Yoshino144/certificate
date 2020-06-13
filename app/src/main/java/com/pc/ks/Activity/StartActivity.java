package com.pc.ks.Activity;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.pc.ks.MainActivity;
import com.pc.ks.R;

import java.io.File;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //判断是否已经开启
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == 0) {
            //启动应用
            ShimmerFrameLayout container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
            Shimmer.AlphaHighlightBuilder shimmerBuilder = new Shimmer.AlphaHighlightBuilder();
            shimmerBuilder.setDuration(3000L).setRepeatMode(ValueAnimator.REVERSE);
            container.setShimmer(shimmerBuilder.build());
            container.startShimmer();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2300);
                        //判断是否首次运行
                        SharedPreferences sp = getSharedPreferences("first_open", 0);
                        int count = sp.getInt("start_count", 0);
                        if(count == 0){
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("start_count", ++count);
                            //提交修改
                            editor.apply();
                            //新用户页面
                            Intent intent = new Intent(StartActivity.this, FirstActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            //主页
                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else{
            finish();
        }
    }
}
