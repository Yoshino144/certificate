package com.pc.ks.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pc.ks.Activity.LoginActivity;
import com.pc.ks.R;
import com.pc.ks.Utils.LogUtils;
import com.pc.ks.View.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment_set#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment_set extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CircleImageView circleImageView;
    private String mParam1;
    private String mParam2;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private UriAdapter mAdapter;

    public BlankFragment_set() {}
    public static BlankFragment_set newInstance(String param1, String param2) {
        BlankFragment_set fragment = new BlankFragment_set();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_set, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RelativeLayout login = getActivity().findViewById(R.id.login);
        circleImageView = getActivity().findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(v->{
            Matisse.from(getActivity())
                    .choose(MimeType.ofAll(), false) //参数1 显示资源类型 参数2 是否可以同时选择不同的资源类型 true表示不可以 false表示可以
//            .theme(R.style.Matisse_Dracula) //选择主题 默认是蓝色主题，Matisse_Dracula为黑色主题
                    .countable(true) //是否显示数字
                    .capture(true)  //是否可以拍照
                    .captureStrategy(//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                            new CaptureStrategy(true, "com.pc.ks.provider"))
                    .maxSelectable(9)  //最大选择资源数量
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K)) //添加自定义过滤器
                    .gridExpectedSize( getResources().getDimensionPixelSize(R.dimen.grid_expected_size)) //设置列宽
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) //设置屏幕方向
                    .thumbnailScale(0.85f)  //图片缩放比例
                    .imageEngine(new GlideEngine())  //选择图片加载引擎
                    .forResult(REQUEST_CODE_CHOOSE);  //设置requestcode,开启Matisse主页面
        });
        login.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            Log.d("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
        }
    }

    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private List<Uri> mUris;
        private List<String> mPaths;

        void setData(List<Uri> uris, List<String> paths) {
            mUris = uris;
            mPaths = paths;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            holder.mUri.setText(mUris.get(position).toString());
            holder.mPath.setText(mPaths.get(position));

            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
            holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {

            private TextView mUri;
            private TextView mPath;

            UriViewHolder(View contentView) {
                super(contentView);
                mUri = (TextView) contentView.findViewById(R.id.uri);
                mPath = (TextView) contentView.findViewById(R.id.path);
            }
        }
    }
}