package com.tianshang.fastman.order.push;

import android.os.Bundle;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.FragmentManagerActivity;

import java.util.ArrayList;

@ARouter(path = "/order/PushOrderManagerActivity")
public class PushOrderManagerActivity extends FragmentManagerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {


    }


    @Override
    public void setTitles() {
        mTitles = new ArrayList<>();
        mTitles.add("待付款");
        mTitles.add("待认领");
        mTitles.add("待收件");
        mTitles.add("待评价");
        mTitles.add("已取消");
    }

    @Override
    public void setFragments() {
        fragments = new ArrayList<>();
        for (int i = 0; i<5;i++){
            fragments.add(new PushOrderFragment());
        }
    }
}
