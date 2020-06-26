package com.pc.ks.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.pc.ks.Activity.LoginActivity;
import com.pc.ks.R;
import com.pc.ks.Utils.CompatUtils;
import com.pc.ks.View.TickView;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView login_text;
    private SpinKitView spinKitView;
    private LinearLayout login;
    private TickView tickView;
    private TextView forget;
    private TextView sign_up;
    private ImageView back_main;

    public FragmentLogin() {}

    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView() {
        login_text = getActivity().findViewById(R.id.login_text);
        tickView = getActivity().findViewById(R.id.tickView);
        sign_up = getActivity().findViewById(R.id.sign_up);
        login = getActivity().findViewById(R.id.login);
        spinKitView = getActivity().findViewById(R.id.spin_kit);
        forget = getActivity().findViewById(R.id.forget);
        back_main = getActivity().findViewById(R.id.back_main);
        back_main.setOnClickListener(v->{
            getActivity().finish();
        });
        forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forget.getPaint().setAntiAlias(true);//抗锯齿
        forget.setOnClickListener(v->{
            ((LoginActivity)getActivity()).setViewPager(0);
        });
        sign_up.setOnClickListener(v->{
            ((LoginActivity)getActivity()).setViewPager(2);
        });
        login.setOnClickListener(v->{
            showBottomLayout();
            login_text.setVisibility(View.GONE);
            spinKitView.setVisibility(View.VISIBLE);

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(() -> {
                    spinKitView.setVisibility(View.GONE);
                    tickView.setVisibility(View.VISIBLE);
                    tickView.start();
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(() -> {
                    tickView.stop();
                    Toasty.success(getActivity(), "Success!", Toast.LENGTH_SHORT, true).show();
                });
            }).start();

        });
    }

    private void showBottomLayout() {
        TypeEvaluator<ViewGroup.LayoutParams> evaluator = new HeightEvaluator();

        ViewGroup.LayoutParams start = new ViewGroup.LayoutParams(CompatUtils.dp2px(Objects.requireNonNull(getActivity()),200), CompatUtils.dp2px(getActivity(),50));
        ViewGroup.LayoutParams end = new ViewGroup.LayoutParams(CompatUtils.dp2px(getActivity(),50), CompatUtils.dp2px(getActivity(),50));
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