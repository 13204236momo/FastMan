package com.tianshang.common.Retrofit.result;

import com.tianshang.common.Retrofit.result.BaseResult;

public class CommonResult<T> extends BaseResult {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
