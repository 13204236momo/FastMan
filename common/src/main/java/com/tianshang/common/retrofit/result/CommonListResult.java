package com.tianshang.common.retrofit.result;

import java.util.ArrayList;

public class CommonListResult<T> extends BaseResult {
    private ArrayList<T> result;

    public ArrayList<T> getData() {
        return result;
    }

    public void setData(ArrayList<T> data) {
        this.result = data;
    }
}
