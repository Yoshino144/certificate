package com.pc.ks.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.pc.ks.Activity.LoginActivity;
import com.pc.ks.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSign#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSign extends Fragment {
    private ImageView back;

    public FragmentSign() {}

    public static FragmentSign newInstance(String param1, String param2) {
        return new FragmentSign();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        back = getActivity().findViewById(R.id.back_login);
        back.setOnClickListener(v->{
            ((LoginActivity)getActivity()).setViewPager(1);
        });
    }
}