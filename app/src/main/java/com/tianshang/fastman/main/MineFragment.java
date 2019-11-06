package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tianshang.annotation.behaviour.LoginCheck;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.fastman.R;
import com.tianshang.fastman.mine.MineFmContract;
import com.tianshang.fastman.mine.MineFmPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends BaseViewFm<MineFmPresenter, MineFmContract.View> {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_more_pull)
    TextView tvMorePull;
    @BindView(R.id.tv_un_finish)
    TextView tvUnFinish;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_more_push)
    TextView tvMorePush;
    @BindView(R.id.tv_un_pay)
    TextView tvUnPay;
    @BindView(R.id.tv_un_claim)
    TextView tvUnClaim;
    @BindView(R.id.tv_no_receive)
    TextView tvNoReceive;
    @BindView(R.id.tv_no_appraise)
    TextView tvNoAppraise;
    @BindView(R.id.tv_cancel_push)
    TextView tvCancelPush;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.iv_profile, R.id.iv_setting, R.id.tv_more_pull,R.id.tv_more_push,
            R.id.tv_un_finish, R.id.tv_finish, R.id.tv_cancel,
            R.id.tv_un_pay, R.id.tv_un_claim, R.id.tv_no_receive,
            R.id.tv_no_appraise, R.id.tv_cancel_push, R.id.iv_message})
    @LoginCheck
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_profile:
                ARouterManager.getInstance()
                        .build("/personal/PersonalInfoActivity")
                        .navigation(getContext());
                break;
            case R.id.iv_setting:
                ARouterManager.getInstance()
                        .build("/personal/SettingActivity")
                        .navigation(getContext());
                break;
            case R.id.iv_message:
                break;
            case R.id.tv_more_push:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .navigation(getContext());
                break;
            case R.id.tv_more_pull:

                break;
            case R.id.tv_un_finish:
                break;
            case R.id.tv_finish:
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_un_pay:
                break;
            case R.id.tv_un_claim:
                break;
            case R.id.tv_no_receive:
                break;
            case R.id.tv_no_appraise:
                break;
            case R.id.tv_cancel_push:
                break;
        }
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
