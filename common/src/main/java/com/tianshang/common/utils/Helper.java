package com.tianshang.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class Helper {
    public static Context mContext;
    private static Toast mToast;

    /**
     * 全局Toast
     *
     * @param str
     */
    public static void showToast(String str) {
        if (!isMainThread(Thread.currentThread().getName())) {
            Log.e("error","不能在异步线程调用showToast");
            return;
        }
        if (mContext == null) return;
        if (TextUtils.isEmpty(str)) return;
        if (mToast == null) {
            mToast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast.setText(str);
        }

    }

    /**
     * 是否在主线程
     * @param aThreadName
     * @return
     */
    public static boolean isMainThread(String aThreadName) {
        return aThreadName.equals("main");
    }

}
