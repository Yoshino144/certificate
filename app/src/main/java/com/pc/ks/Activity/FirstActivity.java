package com.pc.ks.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pc.ks.MainActivity;
import com.pc.ks.R;
import com.pc.ks.Utils.LogUtils;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button new_friend = findViewById(R.id.first_button_new_friend);
        Button old_friend = findViewById(R.id.first_button_old_friend);
        new_friend.setOnClickListener(v->{
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
            finish();
        });
        old_friend.setOnClickListener(v->{
            LogUtils.d("old_friend");
        });

    }
}