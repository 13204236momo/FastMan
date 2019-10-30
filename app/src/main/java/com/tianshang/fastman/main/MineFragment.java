package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tianshang.annotation.behaviour.LoginCheck;
import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.common.utils.Helper;
import com.tianshang.fastman.R;
import com.tianshang.fastman.mine.MineFmContract;
import com.tianshang.fastman.mine.MineFmPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends BaseViewFm<MineFmPresenter, MineFmContract.View> {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
    }

    @OnClick({R.id.iv_profile})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_profile:
//                ARouterManager.getInstance()
//                        .build("/personal/LoginActivity")
//                        .withString("username", "simon")
//                        .navigation(getContext());
                jump();
                break;
        }
    }

    @LoginCheck
    private void jump(){
        Helper.showToast("我登录啦");
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
