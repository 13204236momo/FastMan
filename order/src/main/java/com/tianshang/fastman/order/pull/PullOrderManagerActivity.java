package com.tianshang.fastman.order.pull;

import android.os.Bundle;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.FragmentManagerActivity;
import com.tianshang.fastman.order.push.PushOrderFragment;

import java.util.ArrayList;

@ARouter(path = "/order/PullOrderManagerActivity")
public class PullOrderManagerActivity extends FragmentManagerActivity {
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setTitle("任务管理");
    }

    private void initView() {
        position = getIntent().getIntExtra("position", 0);
        setPosition(position);
    }


    @Override
    public void setTitles() {
        mTitles = new ArrayList<>();
        mTitles.add("未派送");
        mTitles.add("已完成");
        mTitles.add("已取消");
    }

    @Override
    public void setFragments() {
        fragments = new ArrayList<>();
        for (int i = 1; i < mTitles.size()+1; i++) {
            fragments.add(new PullOrderFragment(i));
        }
    }
}
