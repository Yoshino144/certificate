package com.pc.ks.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pc.ks.Activity.FirstActivity;
import com.pc.ks.Activity.SecondActivity;
import com.pc.ks.Activity.StartActivity;
import com.pc.ks.List.SecondRecyclerViewFragment;
import com.pc.ks.R;
import com.pc.ks.Utils.LogUtils;
import com.pc.ks.View.SmoothCheckBox;

import java.util.List;

public class SecondRecyclerViewAdapter extends RecyclerView.Adapter<SecondRecyclerViewAdapter.ViewHolder>{

    private List<SecondRecyclerViewFragment> mFruitList;
    private SecondActivity secondActivity;
    private SharedPreferences sharedPreferences;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Name;
        View itemView;
        SmoothCheckBox smoothCheckBox;
        CardView cardView;
        boolean state =false;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
//            Image = (ImageView) view.findViewById(R.id.fruit_image);
            cardView = view.findViewById(R.id.card);
            smoothCheckBox = view.findViewById(R.id.scb);
            Name = (TextView) view.findViewById(R.id.card_name);
        }
    }

    public SecondRecyclerViewAdapter(List<SecondRecyclerViewFragment> itemFragment2s,SecondActivity secondActivity) {
        mFruitList = itemFragment2s;
        this.secondActivity = secondActivity;
        sharedPreferences = secondActivity.getSharedPreferences("second_user_choose", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_second, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.cardView.setOnClickListener(v->{
            LogUtils.d("点击了cardView"+holder.state);
            if (holder.state){
                holder.smoothCheckBox.setChecked(false,true);
            }else{
                holder.smoothCheckBox.setChecked(true,true);
            }
        });

        holder.smoothCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                int position = holder.getAdapterPosition();
                //注册sharedPreferences
                SecondRecyclerViewFragment secondRecyclerViewFragment = mFruitList.get(position);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(isChecked){
                    holder.state = true;
                    editor.putString(secondRecyclerViewFragment.getName(), "true");
                    holder.smoothCheckBox.setVisibility(View.VISIBLE);
                }else{
                    holder.state = false;
                    new Thread(() -> {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        secondActivity.runOnUiThread(() -> holder.smoothCheckBox.setVisibility(View.GONE));
                    }).start();
                    editor.putString(secondRecyclerViewFragment.getName(), "false");
                }
                //提交sharedPreferences
                editor.apply();
            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SecondRecyclerViewFragment fruit = mFruitList.get(position);
//        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.Name.setText(fruit.getName());
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void re(){
        notifyDataSetChanged();
    }
}
