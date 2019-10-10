package com.tianshang.common.retrofit.result;

public class CommonResult<T> extends BaseResult {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
