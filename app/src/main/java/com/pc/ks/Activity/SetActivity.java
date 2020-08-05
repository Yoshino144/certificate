package com.pc.ks.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pc.ks.R;
import com.tencent.bugly.beta.Beta;

import org.json.JSONObject;

public class SetActivity extends AppCompatActivity {

    private MaterialRippleLayout outLogin;
    private MaterialRippleLayout check_updata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
    }

    private void initView(){
        outLogin = findViewById(R.id.out_login);
        check_updata = findViewById(R.id.check_updata);
        outLogin.setOnClickListener(v->{
            SharedPreferences sp = getSharedPreferences("user", 0);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isLogin",false);
            editor.apply();
        });
        check_updata.setOnClickListener(v->{
            Beta.checkUpgrade(true,false);
        });
    }


}