package com.tianshang.common.base.recyclerAdapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public abstract class MyBaseQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T,K> {
    public MyBaseQuickAdapter(int id, List<T> data) {
        super(id, data);
    }
}


