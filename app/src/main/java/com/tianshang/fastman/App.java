package com.tianshang.fastman;

import android.app.Application;

import com.tianshang.common.utils.Helper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Helper.mContext = this;
    }
}
