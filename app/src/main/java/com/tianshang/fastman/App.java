package com.tianshang.fastman;

import android.app.Application;

import com.tianshang.common.utils.Helper;
import com.tianshang.common.utils.PreferencesUtil;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Helper.mContext = this;
        PreferencesUtil.getInstance().init(this);
    }
}
