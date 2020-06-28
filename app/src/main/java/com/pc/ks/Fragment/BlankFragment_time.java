package com.pc.ks.Fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.pc.ks.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BlankFragment_time extends Fragment implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    public BlankFragment_time() {}

    public static BlankFragment_time newInstance(String param1, String param2) {
        BlankFragment_time fragment = new BlankFragment_time();
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
        return inflater.inflate(R.layout.fragment_blank_time, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @SuppressLint("SetTextI18n")
    protected void initView() {
        mTextMonthDay = getActivity().findViewById(R.id.tv_month_day);
        mTextYear = getActivity().findViewById(R.id.tv_year);
        mTextLunar = getActivity().findViewById(R.id.tv_lunar);
        mRelativeTool = getActivity().findViewById(R.id.rl_tool);
        mCalendarView = getActivity().findViewById(R.id.calendarView);
        mTextCurrentDay = getActivity().findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mYear);
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
            mTextMonthDay.setText(String.valueOf(mYear));
        });
        getActivity().findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());
        mCalendarLayout = getActivity().findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    protected void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();


        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF7abd9a, "20").toString(),
                getSchemeCalendar(year, month, 3, 0xFF7abd9a, "20"));
        map.put(getSchemeCalendar(year, month, 6, 0xFF7abd9a, "33").toString(),
                getSchemeCalendar(year, month, 6, 0xFF7abd9a, "33"));
        map.put(getSchemeCalendar(year, month, 9, 0xFF7abd9a, "25").toString(),
                getSchemeCalendar(year, month, 9, 0xFF7abd9a, "25"));
        map.put(getSchemeCalendar(year, month, 13, 0xFF7abd9a, "50").toString(),
                getSchemeCalendar(year, month, 13, 0xFF7abd9a, "50"));
        map.put(getSchemeCalendar(year, month, 14, 0xFF7abd9a, "80").toString(),
                getSchemeCalendar(year, month, 14, 0xFF7abd9a, "80"));
        map.put(getSchemeCalendar(year, month, 15, 0xFF7abd9a, "20").toString(),
                getSchemeCalendar(year, month, 15, 0xFF7abd9a, "20"));
        map.put(getSchemeCalendar(year, month, 18, 0xFF7abd9a, "70").toString(),
                getSchemeCalendar(year, month, 18, 0xFF7abd9a, "70"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF7abd9a, "36").toString(),
                getSchemeCalendar(year, month, 25, 0xFF7abd9a, "36"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF7abd9a, "95").toString(),
                getSchemeCalendar(year, month, 27, 0xFF7abd9a, "95"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
}