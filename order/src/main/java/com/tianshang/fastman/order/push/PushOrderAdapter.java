package com.tianshang.fastman.order.push;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianshang.fastman.order.R;

import java.util.List;

public class PushOrderAdapter extends BaseQuickAdapter<String ,BaseViewHolder>{

    public PushOrderAdapter(@Nullable List<String> data) {
        super(R.layout.item_order,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}