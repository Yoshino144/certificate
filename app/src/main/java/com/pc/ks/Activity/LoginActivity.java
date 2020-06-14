package com.pc.ks.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.pc.ks.MainActivity;
import com.pc.ks.R;
import com.pc.ks.Utils.CompatUtils;
import com.pc.ks.View.TickView;

public class LoginActivity extends AppCompatActivity {
    private TextView login_text;
    private SpinKitView spinKitView;
    private LinearLayout login;
    private TickView tickView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        login_text = findViewById(R.id.login_text);
        tickView = findViewById(R.id.tickView);
        login = findViewById(R.id.login);
        spinKitView = findViewById(R.id.spin_kit);
        login.setOnClickListener(v->{
            showBottomLayout();
            login_text.setVisibility(View.GONE);
            spinKitView.setVisibility(View.VISIBLE);

            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    spinKitView.setVisibility(View.GONE);
                    tickView.setVisibility(View.VISIBLE);
                    tickView.start();
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    tickView.stop();
                });
            }).start();

        });
    }

    private void showBottomLayout() {
        TypeEvaluator<ViewGroup.LayoutParams> evaluator = new HeightEvaluator();

        ViewGroup.LayoutParams start = new ViewGroup.LayoutParams(CompatUtils.dp2px(this,200), CompatUtils.dp2px(this,50));
        ViewGroup.LayoutParams end = new ViewGroup.LayoutParams(CompatUtils.dp2px(this,50), CompatUtils.dp2px(this,50));
        ValueAnimator animator = ObjectAnimator.ofObject(login, "layoutParams", evaluator, start, end);

        AnimatorSet set = new AnimatorSet();
        set.play(animator);
        set.setDuration(400);
        set.start();
    }

    class HeightEvaluator implements TypeEvaluator<ViewGroup.LayoutParams> {

        @Override
        public ViewGroup.LayoutParams evaluate(float fraction, ViewGroup.LayoutParams startValue, ViewGroup.LayoutParams endValue) {
            ViewGroup.LayoutParams params = login.getLayoutParams();
            params.width = (int) (startValue.width + fraction * (endValue.width - startValue.width));
            return params;
        }
    }
}