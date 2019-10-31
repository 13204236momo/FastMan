package com.tianshang.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Helper {
    public static Context mContext;
    private static Toast mToast;

    /**
     * 全局Toast
     *
     */
    public static void showToast(String s) {
        try {
            if (mToast != null) {
                mToast.setText(s);
            } else {
                mToast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            }
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
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
