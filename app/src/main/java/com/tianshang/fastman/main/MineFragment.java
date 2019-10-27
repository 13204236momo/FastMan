package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.View;

import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.fastman.R;
import com.tianshang.fastman.mine.MineFmContract;
import com.tianshang.fastman.mine.MineFmPresenter;


import butterknife.ButterKnife;


public class MineFragment extends BaseViewFm<MineFmPresenter, MineFmContract.View> {


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
    }

    @Override
    public MineFmContract.View getContract() {
        return null;
    }

    @Override
    public MineFmPresenter getPresenter() {
        return new MineFmPresenter();
    }
}
