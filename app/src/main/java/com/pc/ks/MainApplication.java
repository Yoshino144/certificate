package com.pc.ks;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

public class MainApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext(), "f896926914", true);

    }

    public static Context getInstance() {
        return mContext;
    }
}