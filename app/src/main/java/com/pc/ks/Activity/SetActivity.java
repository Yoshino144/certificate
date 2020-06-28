package com.pc.ks.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pc.ks.R;
import com.pc.ks.Utils.LogUtils;

import org.json.JSONObject;

public class SetActivity extends AppCompatActivity {

    private MaterialRippleLayout outLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
    }

    private void initView(){
        outLogin = findViewById(R.id.out_login);
        outLogin.setOnClickListener(v->{
            SharedPreferences sp = getSharedPreferences("user", 0);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isLogin",false);
            editor.apply();
        });
    }
}