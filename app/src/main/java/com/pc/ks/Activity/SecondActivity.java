package com.pc.ks.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pc.ks.Adapter.SecondRecyclerViewAdapter;
import com.pc.ks.List.SecondRecyclerViewFragment;
import com.pc.ks.MainActivity;
import com.pc.ks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    List<String> string_list = Arrays.asList("公务员","教师","计算机","心理学","法学");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initRecyclerView();
        Button submit = findViewById(R.id.second_submit);
        Button skip = findViewById(R.id.second_skip);
        submit.setOnClickListener(v->{
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });
        skip.setOnClickListener(v->{
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initRecyclerView(){
        List<SecondRecyclerViewFragment> list = new ArrayList<>();
        initDates(list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scecond_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        SecondRecyclerViewAdapter adapter = new SecondRecyclerViewAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }

    private void initDates(List<SecondRecyclerViewFragment> list) {
        for(String s: string_list){
            SecondRecyclerViewFragment secondRecyclerViewFragment = new SecondRecyclerViewFragment(
                    s, R.mipmap.ic_launcher,"0");
            list.add(secondRecyclerViewFragment);
        }
    }
}