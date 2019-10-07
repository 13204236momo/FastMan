package com.tianshang.arouter_api;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 参数的管理
 */
public class BundleManger {
    private Bundle bundle = new Bundle();
    //是否回调setResult（）；
    private boolean isResult;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    //对外提供传参方法
    //@notNUll不允许传空，@Nullable：允许传空
    public BundleManger withString(@NonNull String key, @Nullable String value) {
        bundle.putString(key, value);
        return this;
    }

    //示例代码，架构师拓展
    public BundleManger withResultString(@NonNull String key, @Nullable String value) {
        bundle.putString(key, value);
        isResult = true;
        return this;
    }

    public BundleManger withBollean(@NonNull String key, @Nullable boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleManger withInt(@NonNull String key, @Nullable int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleManger withBundle(@NonNull String key, @Nullable Bundle value) {
        this.bundle = value;
        return this;
    }

    //直接跳转startActivity
    public Object navigation(Context context) {
        return navigation(context, -1);
    }

    //forResult
    //这里的code可以使resultCode，也可以是requestCode ，取决于isResult
    public Object navigation(Context context, int code) {
        return ARouterManager.getInstance().navigation(context, this,code);
    }


}
