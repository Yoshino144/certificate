package com.pc.ks.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.pc.ks.Adapter.TimeLineAdapter;
import com.pc.ks.List.OrderStatus;
import com.pc.ks.List.TimeLineModel;
import com.pc.ks.MainActivity;
import com.pc.ks.R;
import com.pc.ks.Utils.LogUtils;
import com.pc.ks.View.CirclereFreshLayout.CircleRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BlankFragment_todo extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<TimeLineModel> mDataList = new ArrayList();
    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    CalendarView mCalendarView;
    private MainActivity ma;
    private CircleRefreshLayout circleRefreshLayout;

    public BlankFragment_todo() {
    }

    public static BlankFragment_todo newInstance(String param1, String param2) {
        return new BlankFragment_todo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_todo, container, false);
        initTabTime(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        circleRefreshLayout = getActivity().findViewById(R.id.refresh_layout);
        circleRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        // do something when refresh starts
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    circleRefreshLayout.finishRefreshing();
                                    //getActivity().runOnUiThread(() -> {
                                        Toasty.success(Objects.requireNonNull(getActivity()), "刷新了个寂寞", Toast.LENGTH_SHORT, true).show();
                                    //});
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void completeRefresh() {
                        // do something when refresh complete
                    }
                });


    }

    private void initTabTime(View view) {
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        mTextMonthDay = view.findViewById(R.id.todo_month_day);
        mTextYear = view.findViewById(R.id.todo_year);
        mTextLunar = view.findViewById(R.id.todo_lunar);
        mTextYear.setText(String.valueOf(year));
        mTextMonthDay.setText(month + "月" + day + "日");
        mTextLunar.setText("今日");
    }


    private void initRecyclerView(View view) {
        initDates();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TimeLineAdapter adapter = new TimeLineAdapter(mDataList, (MainActivity) getActivity());
        recyclerView.setAdapter(adapter);
        LogUtils.d("RecyclerView success");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 5) {
                    ((MainActivity) getActivity()).Hide();
                }
                if (dy < -5) {
                    ((MainActivity) getActivity()).Display();
                }
            }
        });
    }

    private void initDates() {
        mDataList.add(new TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE));
        mDataList.add(new TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED));

    }

}